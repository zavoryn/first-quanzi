package cn.metast.tuoke.module.system.service.auth;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.util.monitor.TracerUtils;
import cn.metast.tuoke.framework.common.util.servlet.ServletUtils;
import cn.metast.tuoke.framework.common.util.validation.ValidationUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.framework.tenant.core.context.TenantContextHolder;
import cn.metast.tuoke.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.metast.tuoke.module.system.api.sms.SmsCodeApi;
import cn.metast.tuoke.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.metast.tuoke.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.metast.tuoke.module.system.api.social.dto.SocialUserRespDTO;
import cn.metast.tuoke.module.system.controller.admin.auth.sync.constans.AuthConstans;
import cn.metast.tuoke.module.system.controller.admin.auth.vo.*;
import cn.metast.tuoke.module.system.convert.auth.AuthConvert;
import cn.metast.tuoke.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.metast.tuoke.module.system.dal.dataobject.tenant.TenantDO;
import cn.metast.tuoke.module.system.dal.dataobject.user.AdminUserDO;
import cn.metast.tuoke.module.system.enums.logger.LoginLogTypeEnum;
import cn.metast.tuoke.module.system.enums.logger.LoginResultEnum;
import cn.metast.tuoke.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.metast.tuoke.module.system.enums.sms.SmsSceneEnum;
import cn.metast.tuoke.module.system.service.logger.LoginLogService;
import cn.metast.tuoke.module.system.service.member.MemberService;
import cn.metast.tuoke.module.system.service.oauth2.OAuth2TokenService;
import cn.metast.tuoke.module.system.service.social.SocialUserService;
import cn.metast.tuoke.module.system.service.tenant.TenantService;
import cn.metast.tuoke.module.system.service.user.AdminUserService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.annotations.VisibleForTesting;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.metast.tuoke.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author metast.cn
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminUserService userService;
    @Resource
    @Lazy
    private LoginLogService loginLogService;
    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private SocialUserService socialUserService;
    @Resource
    private MemberService memberService;
    @Resource
    private Validator validator;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private TenantService tenantService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${baseurl}")
    private String baseurl;
    /**
     * 验证码的开关，默认为 true
     */
    @Value("${tuoke.captcha.enable:true}")
    @Setter // 为了单测：开启或者关闭验证码
    private Boolean captchaEnable;

    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_NOUSER);
        }
        Long tenantId = TenantContextHolder.getRequiredTenantId();

        // 优化后
        String authCodeByUsername = stringRedisTemplate.opsForValue()
                .get(AuthConstans.AUTHCOCE + tenantId + "_" + username);
        String authCodeByMobile = stringRedisTemplate.opsForValue()
                .get(AuthConstans.AUTHCOCE + tenantId + "_" + user.getMobile());
        String authCode = StringUtils.isNotEmpty(authCodeByUsername) ? authCodeByUsername : authCodeByMobile;

        Boolean isAuthCode = false;
        if(StringUtils.isNotEmpty(authCode) && authCode.equals(password)){
            isAuthCode = true;
        }
        if (!isAuthCode && !userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 校验验证码
        validateCaptcha(reqVO);

        // 使用账号密码，进行登录
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 如果 socialType 非空，说明需要绑定社交用户
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @Override
    public AuthLoginRespVO aiLogin(AuthLoginReqVO reqVO) {
        // 使用账号密码，进行登录
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());
        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @Override
    public void sendSmsCode(AuthSmsSendReqVO reqVO) {
        // 如果是重置密码场景，需要校验图形验证码是否正确
        if (Objects.equals(SmsSceneEnum.ADMIN_MEMBER_RESET_PASSWORD.getScene(), reqVO.getScene())) {
            ResponseModel response = doValidateCaptcha(reqVO);
            if (!response.isSuccess()) {
                throw exception(AUTH_REGISTER_CAPTCHA_CODE_ERROR, response.getRepMsg());
            }
        }

        // 登录场景，验证是否存在
        if (userService.getUserByMobile(reqVO.getMobile()) == null) {
            throw exception(AUTH_MOBILE_NOT_EXISTS);
        }
        // 发送验证码
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    @Override
    public AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) {
        // 校验验证码
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_MEMBER_LOGIN.getScene(), getClientIP()));

        // 获得用户信息
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE);
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    @Override
    public AuthLoginRespVO socialLogin(AuthSocialLoginReqVO reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        SocialUserRespDTO socialUser = socialUserService.getSocialUserByCode(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (socialUser == null || socialUser.getUserId() == null) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }

        // 获得用户
        AdminUserDO user = userService.getUser(socialUser.getUserId());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL);
    }

    @VisibleForTesting
    void validateCaptcha(AuthLoginReqVO reqVO) {
        ResponseModel response = doValidateCaptcha(reqVO);
        // 校验验证码
        if (!response.isSuccess()) {
            // 创建登录失败日志（验证码不正确)
            createLoginLog(null, reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, response.getRepMsg());
        }
    }

    private ResponseModel doValidateCaptcha(CaptchaVerificationReqVO reqVO) {
        // 如果验证码关闭，则不进行校验
        if (!captchaEnable) {
            return ResponseModel.success();
        }
        ValidationUtils.validate(validator, reqVO, CaptchaVerificationReqVO.CodeEnableGroup.class);
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(reqVO.getCaptchaVerification());
        return captchaService.verification(captchaVO);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 插入登陆日志
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logType);
    }

    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        if (ObjectUtil.equal(getUserType().getValue(), userType)) {
            reqDTO.setUsername(getUsername(userId));
        } else {
            reqDTO.setUsername(memberService.getMemberUserMobile(userId));
        }
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

    @Override
    public AuthLoginRespVO register(AuthRegisterReqVO registerReqVO) {
        // 1. 校验验证码
        validateCaptcha(registerReqVO);

        // 2. 校验用户名是否已存在
        Long userId = userService.registerUser(registerReqVO);

        // 3. 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(userId, registerReqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @VisibleForTesting
    void validateCaptcha(AuthRegisterReqVO reqVO) {
        ResponseModel response = doValidateCaptcha(reqVO);
        // 验证不通过
        if (!response.isSuccess()) {
            throw exception(AUTH_REGISTER_CAPTCHA_CODE_ERROR, response.getRepMsg());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(AuthResetPasswordReqVO reqVO) {
        AdminUserDO userByMobile = userService.getUserByMobile(reqVO.getMobile());
        if (userByMobile == null) {
            throw exception(USER_MOBILE_NOT_EXISTS);
        }

        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO()
                .setCode(reqVO.getCode())
                .setMobile(reqVO.getMobile())
                .setScene(SmsSceneEnum.ADMIN_MEMBER_RESET_PASSWORD.getScene())
                .setUsedIp(getClientIP())
        );

        userService.updateUserPassword(userByMobile.getId(), reqVO.getPassword());
    }





    public JSONObject verifyToken(String token) {
        Request request = new Request.Builder()
                .url(baseurl + "/system/user/profile")
                .method("GET",null)
                .addHeader("Content-Type", "application/json")
                .addHeader("tenant-id", String.valueOf(TenantContextHolder.getTenantId()))
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return executeHttpRequest(request);
    }
    public JSONObject getAuthCode(Long kefuId){
        JSONObject paramObject = new JSONObject();
        paramObject.put("enterpriseName", TenantContextHolder.getTenantId());
        paramObject.put("userName", ObjectUtil.isNotEmpty(kefuId) ? kefuId : SecurityFrameworkUtils.getLoginUserId());

        RequestBody body = RequestBody.create(paramObject.toJSONString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(baseurl + "/auth/getAuthCode")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("tenant-id", String.valueOf(TenantContextHolder.getTenantId()))
                .build();

        return executeHttpRequest(request);
    }
    public JSONObject createToken(Long kefuId) {
        JSONObject authInfo = getAuthCode(kefuId);

        if(ObjectUtil.isEmpty(authInfo) || authInfo.getInteger("code") != 200){
            return authInfo;
        }
        String authCode = authInfo.getString("msg");
        JSONObject paramObject = new JSONObject();
        TenantDO tenant = tenantService.getTenant(TenantContextHolder.getTenantId());
        paramObject.put("enterpriseName", TenantContextHolder.getTenantId() == 1L ? "administrator" : tenant.getName());
        paramObject.put("userName", getUsername(ObjectUtil.isNotEmpty(kefuId) ? kefuId : SecurityFrameworkUtils.getLoginUserId()));
        paramObject.put("code", authCode);
        RequestBody body = RequestBody.create(paramObject.toJSONString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(baseurl + "/auth/accountLogin")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("tenant-id", String.valueOf(TenantContextHolder.getTenantId()))
                .build();

        return executeHttpRequest(request);
    }


    @Override
    public JSONObject lxLogin(Long kefuId) {
        Long tenantId = TenantContextHolder.getTenantId();
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        String redisKey = "LINGXI-TOKEN:" + tenantId + ":" + userId;
        String tokenInfo = stringRedisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isNotEmpty(tokenInfo)) {

            JSONObject tokenObj = JSONObject.parseObject(tokenInfo);
            String accessToken = tokenObj.getString("access_token");

            JSONObject accessTokenResult = verifyToken(accessToken);
            if (accessTokenResult != null && accessTokenResult.getInteger("code") == 200) {
                return tokenObj;
            }
            log.warn("Access token invalid, attempting to refresh...");
            // TODO: 调用 refreshToken 并更新缓存
        }
        JSONObject tokenData = createToken(kefuId);
        JSONObject token = null;
        if(ObjectUtil.isNotEmpty(tokenData) && tokenData.getInteger("code") == 200){
            token = tokenData.getJSONObject("data");
            stringRedisTemplate.opsForValue().set(redisKey,token.toJSONString());
        }else{
//            if(ObjectUtil.isNotEmpty(tokenData) && ("租户不存在".equals(tokenData.getString("msg")) || "登录失败，登录用户不存在".equals(tokenData.getString("msg")))){
//                //创建用户
//                syncUserById(sysUserDto);
//                for (int i = 0; i < 5; i++) {
//                    tokenData = createToken();
//                    if(ObjectUtil.isNotEmpty(tokenData) && tokenData.getInteger("code") == 0) {
//                        token = tokenData.getJSONObject("data");
//                        stringRedisTemplate.opsForValue().set(redisKey, token.toJSONString());
//                        break;
//                    }
//                    ThreadUtil.sleep(5000);
//                }
//            }
        }
        return token;
    }


    @Override
    public JSONObject syncUserById(Long userId, String optCode) {
        JSONObject tokenInfo = lxLogin(null);
        if(ObjectUtil.isNotEmpty(tokenInfo)){
            String accessToken = tokenInfo.getString("access_token");
            AdminUserDO user = userService.getUser(userId);
            if(ObjectUtil.isEmpty(user)){
                return null;
            }
            JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(user));
            params.put("optCode", optCode);
            RequestBody body = RequestBody.create(params.toJSONString(), MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url(baseurl + "/system/user/modifyUserByYdId")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();

            return executeHttpRequest(request);
        }
        return null;
    }
    @Override
    public JSONObject syncUserByIdNoToken(Long userId, String optCode) {
        AdminUserDO user = userService.getUser(userId);
        if(ObjectUtil.isEmpty(user)){
            return null;
        }
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(user));
        params.put("optCode", optCode);
        params.put("tenantId", TenantContextHolder.getTenantId());
        RequestBody body = RequestBody.create(params.toJSONString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(baseurl + "/system/user/modifyUserByYdIdNoToken")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        return executeHttpRequest(request);
    }

    @Override
    public JSONObject sendMessage(String message, Long kefuId) {
        JSONObject params = JSONObject.parseObject(message);
        JSONObject tokenInfo = lxLogin(kefuId);
        if(ObjectUtil.isNotEmpty(tokenInfo)){
            String accessToken = tokenInfo.getString("access_token");
            RequestBody body = RequestBody.create(params.toJSONString(), MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url(baseurl + "/system/kf/callback")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();

            return executeHttpRequest(request);
        }
        return null;
    }
    @Override
    public void lxLogout() {
        Long tenantId = TenantContextHolder.getTenantId();
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        String redisKey = "LINGXI-TOKEN:" + tenantId + ":" + userId;
        String tokenInfo = stringRedisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isNotEmpty(tokenInfo)) {
            JSONObject tokenObj = JSONObject.parseObject(tokenInfo);
            String accessToken = tokenObj.getString("access_token");
            JSONObject accessTokenResult = verifyToken(accessToken);
            if (accessTokenResult != null && accessTokenResult.getInteger("code") == 200) {
                RequestBody body = RequestBody.create("", MediaType.parse("application/json"));
                Request request = new Request.Builder()
                        .url(baseurl + "/auth/lxlogout")
                        .delete(body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .build();
                executeHttpRequest(request);
            }
        }
    }

    private JSONObject executeHttpRequest(Request request) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.code() == HttpStatus.HTTP_OK) {
                return JSONObject.parseObject(response.body().string());
            }
        } catch (Exception e) {
            // 使用日志框架记录异常，而非直接打印堆栈
            log.error("HTTP request failed: {}", e.getMessage(), e);
        } finally {
            // 释放资源
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
        }
        return null;
    }


    @Override
    public String zbLogin() {
        //根据租户Id获取请求URl
        Long tenantId = TenantContextHolder.getTenantId();
        TenantDO tenant = tenantService.getTenant(tenantId);
        String reqUrl = tenant.getWebsite();
        log.info("reqUrl:"+reqUrl);
        if(reqUrl.equals("https://fitnesslive.metast.cn") || reqUrl.equals("http://localhost:1801") || reqUrl.matches("https://live.*\\.metast\\.cn")){
            log.info("reqUrl:开始执行");
            RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
            Request request = new Request.Builder()
                    .url(reqUrl + "/login?username=dev-yudao&password=swh888888&verCode=1&btnLogin=%E7%99%BB%E5%BD%95")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response != null && response.code() == HttpStatus.HTTP_OK) {
                    String resultHtml = response.body().string();
                    // 正则表达式匹配 jsessionid= 后面的值
                    Pattern pattern = Pattern.compile("jsessionid=([A-F0-9]{32})", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(resultHtml);

                    if (matcher.find()) {
                        return "JSESSIONID="+matcher.group(1);
                    }
                    return null;
                }
            } catch (Exception e) {
                // 使用日志框架记录异常，而非直接打印堆栈
                log.error("HTTP request failed: {}", e.getMessage(), e);
            } finally {
                // 释放资源
                client.connectionPool().evictAll();
                client.dispatcher().executorService().shutdown();
            }
        }
        return null;
    }
    @Override
    public boolean verifyCookie(String cookie){
        //根据租户Id获取请求URl
        Long tenantId = TenantContextHolder.getTenantId();
        TenantDO tenant = tenantService.getTenant(tenantId);
        String reqUrl = tenant.getWebsite();
        log.info("reqUrl:"+reqUrl);
        if(reqUrl.equals("https://fitnesslive.metast.cn") || reqUrl.equals("http://localhost:1801")){
            log.info("reqUrl:开始执行");
            RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
            Request request = new Request.Builder()
                    .url(reqUrl + "/admin/crud/listpage?pageName=AppUser")
                    .post(body)
                    .addHeader("cookie", cookie)
                    .build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response != null && response.code() == HttpStatus.HTTP_OK) {
                    String resultHtml = response.body().string();
                    if(!resultHtml.contains("<title>用户登录</title>")){
                        return true;
                    }
                    return false;
                }
            } catch (Exception e) {
                // 使用日志框架记录异常，而非直接打印堆栈
                log.error("HTTP request failed: {}", e.getMessage(), e);
            } finally {
                // 释放资源
                client.connectionPool().evictAll();
                client.dispatcher().executorService().shutdown();
            }
        }
        return false;
    }
}
