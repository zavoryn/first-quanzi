package cn.metast.tuoke.module.system.controller.admin.auth;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.cache.CacheUtils;
import cn.metast.tuoke.framework.common.util.collection.CollectionUtils;
import cn.metast.tuoke.framework.security.config.SecurityProperties;
import cn.metast.tuoke.framework.security.core.LoginUser;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.framework.tenant.core.context.TenantContextHolder;
import cn.metast.tuoke.framework.tenant.core.service.TenantFrameworkService;
import cn.metast.tuoke.framework.tenant.core.service.TenantFrameworkServiceImpl;
import cn.metast.tuoke.framework.tenant.core.util.TenantUtils;
import cn.metast.tuoke.framework.web.core.util.WebFrameworkUtils;
import cn.metast.tuoke.module.system.controller.admin.auth.sync.*;
import cn.metast.tuoke.module.system.controller.admin.auth.sync.constans.AuthConstans;
import cn.metast.tuoke.module.system.controller.admin.auth.vo.*;
import cn.metast.tuoke.module.system.controller.admin.tenant.vo.tenant.TenantSaveReqVO;
import cn.metast.tuoke.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import cn.metast.tuoke.module.system.convert.auth.AuthConvert;
import cn.metast.tuoke.module.system.dal.dataobject.permission.MenuDO;
import cn.metast.tuoke.module.system.dal.dataobject.permission.RoleDO;
import cn.metast.tuoke.module.system.dal.dataobject.permission.UserRoleDO;
import cn.metast.tuoke.module.system.dal.dataobject.tenant.TenantDO;
import cn.metast.tuoke.module.system.dal.dataobject.user.AdminUserDO;
import cn.metast.tuoke.module.system.dal.mysql.permission.UserRoleMapper;
import cn.metast.tuoke.module.system.enums.logger.LoginLogTypeEnum;
import cn.metast.tuoke.module.system.enums.logger.LoginResultEnum;
import cn.metast.tuoke.module.system.service.auth.AdminAuthService;
import cn.metast.tuoke.module.system.service.permission.MenuService;
import cn.metast.tuoke.module.system.service.permission.PermissionService;
import cn.metast.tuoke.module.system.service.permission.RoleService;
import cn.metast.tuoke.module.system.service.social.SocialClientService;
import cn.metast.tuoke.module.system.service.tenant.TenantService;
import cn.metast.tuoke.module.system.service.user.AdminUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mzt.logapi.context.LogRecordContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.setLoginUser;
import static cn.metast.tuoke.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_NOUSER;
import static java.util.Collections.singleton;

@Tag(name = "管理后台 - 认证")
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;
    @Resource
    private AdminUserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private SocialClientService socialClientService;
    @Resource
    private TenantService tenantService;
    @Resource
    private TenantFrameworkService tenantFrameworkService;
    @Resource
    private SecurityProperties securityProperties;

    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "使用账号密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotBlank(token)) {
            authService.lxLogout();
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @PermitAll
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    @GetMapping("/get-permission-info")
    @Operation(summary = "获取登录用户的权限信息")
    public CommonResult<AuthPermissionInfoRespVO> getPermissionInfo() {
        // 1.1 获得用户信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        if (user == null) {
            return success(null);
        }

        // 1.2 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(getLoginUserId());
        if (CollUtil.isEmpty(roleIds)) {
            return success(AuthConvert.INSTANCE.convert(user, Collections.emptyList(), Collections.emptyList()));
        }
        List<RoleDO> roles = roleService.getRoleList(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())); // 移除禁用的角色

        // 1.3 获得菜单列表
        Set<Long> menuIds = permissionService.getRoleMenuListByRoleId(convertSet(roles, RoleDO::getId));
        List<MenuDO> menuList = menuService.getMenuList(menuIds);
        menuList = menuService.filterDisableMenus(menuList);

        // 2. 拼接结果返回
        return success(AuthConvert.INSTANCE.convert(user, roles, menuList));
    }

    @PostMapping("/register")
    @PermitAll
    @Operation(summary = "注册用户")
    public CommonResult<AuthLoginRespVO> register(@RequestBody @Valid AuthRegisterReqVO registerReqVO) {
        return success(authService.register(registerReqVO));
    }

    // ========== 短信登录相关 ==========

    @PostMapping("/sms-login")
    @PermitAll
    @Operation(summary = "使用短信验证码登录")
    public CommonResult<AuthLoginRespVO> smsLogin(@RequestBody @Valid AuthSmsLoginReqVO reqVO) {
        return success(authService.smsLogin(reqVO));
    }

    @PostMapping("/send-sms-code")
    @PermitAll
    @Operation(summary = "发送手机验证码")
    public CommonResult<Boolean> sendLoginSmsCode(@RequestBody @Valid AuthSmsSendReqVO reqVO) {
        authService.sendSmsCode(reqVO);
        return success(true);
    }

    @PostMapping("/reset-password")
    @PermitAll
    @Operation(summary = "重置密码")
    public CommonResult<Boolean> resetPassword(@RequestBody @Valid AuthResetPasswordReqVO reqVO) {
        authService.resetPassword(reqVO);
        return success(true);
    }

    // ========== 社交登录相关 ==========

    @GetMapping("/social-auth-redirect")
    @PermitAll
    @Operation(summary = "社交授权的跳转")
    @Parameters({
            @Parameter(name = "type", description = "社交类型", required = true),
            @Parameter(name = "redirectUri", description = "回调路径")
    })
    public CommonResult<String> socialLogin(@RequestParam("type") Integer type,
                                            @RequestParam("redirectUri") String redirectUri) {
        return success(socialClientService.getAuthorizeUrl(
                type, UserTypeEnum.ADMIN.getValue(), redirectUri));
    }

    @PostMapping("/social-login")
    @PermitAll
    @Operation(summary = "社交快捷登录，使用 code 授权码", description = "适合未登录的用户，但是社交账号已绑定用户")
    public CommonResult<AuthLoginRespVO> socialQuickLogin(@RequestBody @Valid AuthSocialLoginReqVO reqVO) {
        return success(authService.socialLogin(reqVO));
    }

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/ai-sync-user")
    @PermitAll
    @Operation(summary = "AI客服SCRM用户", description = "AI客服SCRM用户-接收来自灵犀的用户同步")
    public CommonResult<String> aiSyncUser(@RequestBody AuthSyncUserVO reqVO) {
        // 1.根据租户Id 获取租户信息
        AuthSysTenant sysTenant = reqVO.getSysTenant();
        AuthSysUser sysUser = reqVO.getSysUser();

        Long id = sysTenant.getId();

        TenantDO tenant = tenantService.getTenant(id);
        if(ObjectUtil.isEmpty(tenant)){
            TenantSaveReqVO vo = new TenantSaveReqVO();
            vo.setId(id);
            vo.setName(sysTenant.getName());
            vo.setPackageId(111L);
            vo.setWebsite("metast.cn"+System.currentTimeMillis());
            Date expireTime = sysTenant.getExpirationTime();
//            if(ObjectUtil.isNotEmpty(expireTime)){
//                vo.setExpireTime(LocalDateTime.ofInstant(expireTime.toInstant(), ZoneId.systemDefault()));
//            }else{
                vo.setExpireTime(LocalDateTime.now().plusYears(10));
//            }
            vo.setAccountCount(sysTenant.getZhNum());

            vo.setUsername(sysUser.getUserName());
            vo.setPassword("123456");
            vo.setContactId(sysUser.getId());
            vo.setContactName(sysUser.getNickName());
            vo.setContactMobile(sysTenant.getPhone());
            vo.setStatus(0);
            TenantUtils.execute(1L, () -> {
                LoginUser loginUser = new LoginUser();
                loginUser.setId(1L);
                loginUser.setUserType(2);
                SecurityFrameworkUtils.setLoginUser(loginUser, WebFrameworkUtils.getRequest());
                tenantService.createTenant(vo);
                tenantFrameworkService.refreshValidTenantCache(id);
            });
        }else{
            TenantUtils.execute(id, () -> {
                TenantContextHolder.setTenantId(id);
                // 2.根据租户Id 以及用户Id 验证是否存在用户
                AdminUserDO user = userService.getUser(sysUser.getId());
                if(ObjectUtil.isEmpty(user)){
                    LoginUser loginUser = new LoginUser();
                    loginUser.setId(sysUser.getId());
                    loginUser.setUserType(2);
                    SecurityFrameworkUtils.setLoginUser(loginUser, WebFrameworkUtils.getRequest());

                    UserSaveReqVO vo = new UserSaveReqVO();
                    vo.setId(sysUser.getId());
                    vo.setUsername(sysUser.getUserName());
                    vo.setPassword("123456");
                    vo.setNickname(sysUser.getNickName());
                    vo.setMobile(sysUser.getPhone());
                    vo.setEmail(sysUser.getEmail());
                    userService.createUser(vo);
                    List<RoleDO> roleDOS = roleService.getRoleList();
                    if(CollectionUtil.isNotEmpty(roleDOS)){
                        // 分配角色
                        permissionService.assignUserRole(sysUser.getId(), singleton(roleDOS.get(0).getId()));
                    }
                }else{
                    if(StringUtils.isNotEmpty(sysUser.getOptCode()) && "deleteUsers".equals(sysUser.getOptCode())){
                        List<Long> idList = sysUser.getIdList();
                        for (Long delId : idList) {
                            userService.deleteUser(delId);
                        }
                    }else {
                        UserSaveReqVO vo = new UserSaveReqVO();
                        vo.setId(user.getId());
                        vo.setUsername(sysUser.getUserName());
                        vo.setNickname(sysUser.getNickName());
                        vo.setMobile(sysUser.getPhone());
                        vo.setEmail(sysUser.getEmail());
                        vo.setAvatar(sysUser.getAvatar());
                        vo.setSex("0".equals(sysUser.getSex()) ? 1 : 2);
                        vo.setRemark(sysUser.getRemark());
                        userService.updateUser(vo);
                        if ("editUserStatus".equals(sysUser.getOptCode())) {
                            userService.updateUserStatus(user.getId(), user.getStatus());
                        }
                        if ("editUserPassword".equals(sysUser.getOptCode())) {
                            userService.updateUserPassword(user.getId(), user.getPassword());
                        }
                    }
                }
            });
        }
        return success("同步成功！");
    }


    @PostMapping("/get-auth-code")
    @PermitAll
    @Operation(summary = "获取授权码", description = "获取授权码-接收来自灵犀的授权码登录")
    public CommonResult<String> aiLogin(@RequestBody AuthCodeVO reqVO) {
        String code = RandomUtil.randomString(6);
        stringRedisTemplate.opsForValue().set(AuthConstans.AUTHCOCE +reqVO.getTenantId()+"_"+reqVO.getUsername(), code, 1, TimeUnit.MINUTES);
        return success(code);
    }


    @PostMapping("/ai-login")
    @PermitAll
    @Operation(summary = "灵犀账号密码登录", description = "账号密码登录-接收来自灵犀的授权码登录")
    public CommonResult<AuthLoginRespVO> aiLogin(@RequestBody @Valid AuthLoginReqVO reqVO) {
        //要根据之前存储的数据，验证是否过期，机器通过过期的token 能付刷新token
        AuthLoginRespVO authLoginRespVO = authService.aiLogin(reqVO);

        return success(authLoginRespVO);
    }

    @PostMapping("/zb-login")
    @PermitAll
    @Operation(summary = "直播账号密码登录", description = "直播账号密码免登录")
    public CommonResult<String> zbLogin() {
        //直播后台登录
        String token = "";
        try {
            token = authService.zbLogin();
        }catch (Exception e){
            e.printStackTrace();
        }
        return success(token);
    }
    @PostMapping("/verifyCookie")
    @PermitAll
    @Operation(summary = "验证Cookies", description = "直播账号密码免登录")
    public CommonResult<Boolean> verifyCookie(String cookie) {
        //直播后台登录
        boolean verify = false;
        try {
            verify = authService.verifyCookie(cookie);
        }catch (Exception e){
            e.printStackTrace();
        }
        return success(verify);
    }

    @PostMapping("/aiUserLogout")
    @PermitAll
    @Operation(summary = "登出系统", description = "登出系统-接收来自灵犀的登出系统")
    public CommonResult<Boolean> aiLogout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }
//    /**
//     * 根据userId 同步用户信息
//     * @return
//     */
//    @PostMapping("/syncUserById")
//    @Operation(summary = "根据userId 同步用户信息")
//    public JSONObject syncUserById(Long userId) {
//        return authService.syncUserById(userId,AuthConstans.UPDATE);
//    }
//
//    /**
//     * 灵犀账号密码登录
//     * @return
//     */
//    @PostMapping("/lxUserLogin")
//    @Operation(summary = "灵犀账号密码登录")
//    public JSONObject lxUserLogin() {
//        return authService.lxLogin();
//    }
//
//    /**
//     * 灵犀账号密码登录
//     * @return
//     */
//    @PostMapping("/lxUserLogout")
//    @Operation(summary = "灵犀账号退出登录")
//    public void lxUserLogout() {
//        authService.lxLogout();
//    }

}
