package cn.metast.tuoke.module.system.service.logger;

import cn.hutool.core.collection.CollUtil;
import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.api.cmConfig.CmConfigApi;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.metast.tuoke.module.system.api.user.AdminUserApi;
import cn.metast.tuoke.module.system.api.user.dto.AdminUserRespDTO;
import cn.metast.tuoke.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.metast.tuoke.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.metast.tuoke.module.system.dal.dataobject.logger.LoginLogDO;
import cn.metast.tuoke.module.system.dal.dataobject.user.AdminUserDO;
import cn.metast.tuoke.module.system.dal.mysql.logger.LoginLogMapper;
import cn.metast.tuoke.module.system.service.user.AdminUserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录日志 Service 实现
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;
    @Resource
    @Lazy
    private MemberUserApi memberUserApi;
    
    @Resource
    @Lazy
    private AdminUserApi adminUserApi;
    
    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private AdminUserService adminUserService;

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private CmConfigApi cmConfigApi;

    @Override
    public PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO pageReqVO) {
        // 部门数据隔离：根据当前登录用户的部门过滤
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        
        // 超级管理员(deptId=115)，返回所有数据，不过滤
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
        return loginLogMapper.selectPage(pageReqVO);
    }
        
        // 获取同部门的所有用户ID列表（管理员+会员）
        List<Long> userIds = getAllUserIdsByDeptId(loginUserDeptId);
        
        if (userIds.isEmpty()) {
            return PageResult.empty();
        }
        
        return loginLogMapper.selectPageByUserIds(pageReqVO, userIds);
    }

    @Override
    public PageResult<LoginLogRespVO> getLoginLogPageWithNickname(LoginLogPageReqVO pageReqVO) {
        // 部门数据隔离：根据当前登录用户的部门过滤
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        
        // 超级管理员(deptId=115)，返回所有数据，不过滤
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
        PageResult<LoginLogDO> pageResult = loginLogMapper.selectPage(pageReqVO);
        PageResult<LoginLogRespVO> respPage = BeanUtils.toBean(pageResult, LoginLogRespVO.class);
        fillUserNickname(respPage.getList());
        return respPage;
    }
        
        // 获取同部门的所有用户ID列表（管理员+会员）
        List<Long> userIds = getAllUserIdsByDeptId(loginUserDeptId);
        
        if (userIds.isEmpty()) {
            return PageResult.empty();
        }

        PageResult<LoginLogDO> pageResult = loginLogMapper.selectPageByUserIds(pageReqVO, userIds);
        PageResult<LoginLogRespVO> respPage = BeanUtils.toBean(pageResult, LoginLogRespVO.class);
        fillUserNickname(respPage.getList());
        return respPage;
    }

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogDO loginLog = BeanUtils.toBean(reqDTO, LoginLogDO.class);
        loginLogMapper.insert(loginLog);
    }

    /**
     * 批量查询用户昵称并覆盖 username 字段
     */
    private void fillUserNickname(List<LoginLogRespVO> logList) {
        if (logList == null || logList.isEmpty()) {
            return;
        }

        // 1. 收集用户ID (区分管理员和会员)
        Set<Long> memberUserIds = new HashSet<>();
        Set<Long> adminUserIds = new HashSet<>();

        for (LoginLogRespVO item : logList) {
            if (item.getUserType() == null) continue;
            if (item.getUserType() == UserTypeEnum.MEMBER.getValue()) {
                memberUserIds.add(item.getUserId());
            } else if (item.getUserType() == UserTypeEnum.ADMIN.getValue()) {
                adminUserIds.add(item.getUserId());
            }
        }

        // 2. 批量查询用户昵称
        Map<Long, MemberUserRespDTO> memberMap = Collections.emptyMap();
        Map<Long, AdminUserRespDTO> adminMap = Collections.emptyMap();

        if (!memberUserIds.isEmpty()) {
            memberMap = memberUserApi.getUserMap(memberUserIds);
        }
        if (!adminUserIds.isEmpty()) {
            adminMap = adminUserApi.getUserMap(adminUserIds);
        }

        // 3. 将显示的 username 替换为用户昵称
        for (LoginLogRespVO item : logList) {
            String nickname = null;
            if (item.getUserType() == null) continue;

            if (item.getUserType() == UserTypeEnum.MEMBER.getValue()) {
                MemberUserRespDTO user = memberMap.get(item.getUserId());
                if (user != null) {
                    nickname = user.getNickname();
                }
            } else if (item.getUserType() == UserTypeEnum.ADMIN.getValue()) {
                AdminUserRespDTO user = adminMap.get(item.getUserId());
                if (user != null) {
                    nickname = user.getNickname();
                }
            }

            if (nickname != null) {
                item.setUsername(nickname);
            }
        }
    }

    /**
     * 根据部门ID获取所有用户ID列表（管理员+会员）
     * 登录日志包含两种用户类型，需要同时过滤
     */
    private List<Long> getAllUserIdsByDeptId(Long deptId) {
        if (deptId == null) {
            return Collections.emptyList();
        }
        
        Set<Long> allUserIds = new HashSet<>();
        
        // 1. 获取同部门的后台管理员用户ID
        List<AdminUserDO> adminUsers = adminUserService.getUserListByDeptIds(Collections.singletonList(deptId));
        if (CollUtil.isNotEmpty(adminUsers)) {
            allUserIds.addAll(adminUsers.stream().map(AdminUserDO::getId).collect(Collectors.toList()));
        }
        
        // 2. 获取同部门对应圈子的会员用户ID
        List<Long> memberUserIds = cmConfigApi.getUserIdsByDeptId(deptId);
        if (CollUtil.isNotEmpty(memberUserIds)) {
            allUserIds.addAll(memberUserIds);
        }
        
        return new ArrayList<>(allUserIds);
    }

}
