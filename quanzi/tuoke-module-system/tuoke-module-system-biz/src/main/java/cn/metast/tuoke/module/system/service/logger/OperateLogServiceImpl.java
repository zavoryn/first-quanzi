package cn.metast.tuoke.module.system.service.logger;

import cn.hutool.core.collection.CollUtil;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.system.api.logger.dto.OperateLogCreateReqDTO;
import cn.metast.tuoke.module.system.api.logger.dto.OperateLogPageReqDTO;
import cn.metast.tuoke.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import cn.metast.tuoke.module.system.dal.dataobject.logger.OperateLogDO;
import cn.metast.tuoke.module.system.dal.dataobject.user.AdminUserDO;
import cn.metast.tuoke.module.system.dal.mysql.logger.OperateLogMapper;
import cn.metast.tuoke.module.system.service.user.AdminUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志 Service 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
@Slf4j
public class OperateLogServiceImpl implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private AdminUserService adminUserService;


    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        OperateLogDO log = BeanUtils.toBean(createReqDTO, OperateLogDO.class);
        operateLogMapper.insert(log);
    }

    @Override
    public PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO pageReqVO) {
        // 部门数据隔离：根据当前登录用户的部门过滤
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        
        // 超级管理员(deptId=115)，返回所有数据，不过滤
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
        return operateLogMapper.selectPage(pageReqVO);
    }
        
        // 获取同部门的后台管理员用户ID列表
        List<Long> userIds = getAdminUserIdsByDeptId(loginUserDeptId);
        
        if (userIds.isEmpty()) {
            return PageResult.empty();
        }
        
        return operateLogMapper.selectPageByUserIds(pageReqVO, userIds);
    }

    @Override
    public PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqDTO pageReqDTO) {
        // 部门数据隔离：根据当前登录用户的部门过滤
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        
        // 超级管理员(deptId=115)，返回所有数据，不过滤
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
        return operateLogMapper.selectPage(pageReqDTO);
    }
        
        // 获取同部门的后台管理员用户ID列表
        List<Long> userIds = getAdminUserIdsByDeptId(loginUserDeptId);
        
        if (userIds.isEmpty()) {
            return PageResult.empty();
        }
        
        return operateLogMapper.selectPageByUserIds(pageReqDTO, userIds);
    }

    /**
     * 根据部门ID获取后台管理员用户ID列表
     */
    private List<Long> getAdminUserIdsByDeptId(Long deptId) {
        if (deptId == null) {
            return Collections.emptyList();
        }
        List<AdminUserDO> users = adminUserService.getUserListByDeptIds(Collections.singletonList(deptId));
        if (CollUtil.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream().map(AdminUserDO::getId).collect(Collectors.toList());
    }

}
