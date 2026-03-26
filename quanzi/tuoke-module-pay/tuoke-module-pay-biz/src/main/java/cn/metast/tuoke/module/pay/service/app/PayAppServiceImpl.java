package cn.metast.tuoke.module.pay.service.app;

import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.pay.controller.admin.app.vo.PayAppCreateReqVO;
import cn.metast.tuoke.module.pay.controller.admin.app.vo.PayAppPageReqVO;
import cn.metast.tuoke.module.pay.controller.admin.app.vo.PayAppUpdateReqVO;
import cn.metast.tuoke.module.pay.convert.app.PayAppConvert;
import cn.metast.tuoke.module.pay.dal.dataobject.app.PayAppDO;
import cn.metast.tuoke.module.pay.dal.mysql.app.PayAppMapper;
import cn.metast.tuoke.module.pay.enums.ErrorCodeConstants;
import cn.metast.tuoke.module.pay.service.order.PayOrderService;
import cn.metast.tuoke.module.pay.service.refund.PayRefundService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.pay.enums.ErrorCodeConstants.*;

/**
 * 支付应用 Service 实现类
 *
 * @author aquan
 */
@Service
@Validated
public class PayAppServiceImpl implements PayAppService {

    @Resource
    private PayAppMapper appMapper;

    @Resource
    @Lazy // 延迟加载，避免循环依赖报错
    private PayOrderService orderService;
    @Resource
    @Lazy // 延迟加载，避免循环依赖报错
    private PayRefundService refundService;

    @Override
    public Long createApp(PayAppCreateReqVO createReqVO) {
        // 验证 appKey 是否重复
        validateAppKeyUnique(null, createReqVO.getAppKey());

        // 插入
        PayAppDO app = PayAppConvert.INSTANCE.convert(createReqVO);
        
        // 部门数据隔离：自动设置部门ID
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
            // 超级管理员：默认分配给部门117（新建应用默认分配）
            app.setDeptId(117L);
        } else {
            // 普通用户：强制使用当前登录用户的部门
            app.setDeptId(loginUserDeptId);
        }
        
        appMapper.insert(app);
        // 返回
        return app.getId();
    }

    @Override
    public void updateApp(PayAppUpdateReqVO updateReqVO) {
        // 校验存在
        PayAppDO app = validateAppExistsAndReturn(updateReqVO.getId());
        // 部门数据隔离：校验是否有权限编辑
        validateAppDeptPermission(app);
        // 验证 appKey 是否重复
        validateAppKeyUnique(updateReqVO.getId(), updateReqVO.getAppKey());

        // 更新（不允许修改部门）
        PayAppDO updateObj = PayAppConvert.INSTANCE.convert(updateReqVO);
        updateObj.setDeptId(null); // 不更新部门字段
        appMapper.updateById(updateObj);
    }

    void validateAppKeyUnique(Long id, String appKey) {
        PayAppDO app = appMapper.selectByAppKey(appKey);
        if (app == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 appKey 的应用
        if (id == null) {
            throw exception(APP_KEY_EXISTS);
        }
        if (!app.getId().equals(id)) {
            throw exception(APP_KEY_EXISTS);
        }
    }

    @Override
    public void updateAppStatus(Long id, Integer status) {
        // 校验商户存在
        validateAppExists(id);
        // 更新状态
        appMapper.updateById(new PayAppDO().setId(id).setStatus(status));
    }

    @Override
    public void deleteApp(Long id) {
        // 校验存在
        PayAppDO app = validateAppExistsAndReturn(id);
        // 部门数据隔离：校验是否有权限删除
        validateAppDeptPermission(app);
        // 校验关联数据是否存在
        if (orderService.getOrderCountByAppId(id) > 0) {
            throw exception(APP_EXIST_ORDER_CANT_DELETE);
        }
        if (refundService.getRefundCountByAppId(id) > 0) {
            throw exception(APP_EXIST_REFUND_CANT_DELETE);
        }

        // 删除
        appMapper.deleteById(id);
    }

    private void validateAppExists(Long id) {
        if (appMapper.selectById(id) == null) {
            throw exception(APP_NOT_FOUND);
        }
    }

    /**
     * 校验应用存在并返回
     */
    private PayAppDO validateAppExistsAndReturn(Long id) {
        PayAppDO app = appMapper.selectById(id);
        if (app == null) {
            throw exception(APP_NOT_FOUND);
        }
        return app;
    }

    /**
     * 校验当前用户是否有权限操作该应用
     */
    private void validateAppDeptPermission(PayAppDO app) {
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        
        // 超级管理员(deptId=115)，有所有权限
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
            return;
        }
        
        // 非超级管理员：只能操作本部门的应用
        if (app.getDeptId() == null || !app.getDeptId().equals(loginUserDeptId)) {
            throw exception(APP_NOT_FOUND); // 无权限时返回未找到
        }
    }

    @Override
    public PayAppDO getApp(Long id) {
        return appMapper.selectById(id);
    }

    @Override
    public List<PayAppDO> getAppList(Collection<Long> ids) {
        return appMapper.selectBatchIds(ids);
    }

    @Override
    public List<PayAppDO> getAppList() {
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        
        // 超级管理员(deptId=115)，返回所有应用
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
        return appMapper.selectList();
    }
        
        // 非超级管理员：只返回本部门的应用
        return appMapper.selectListByDeptId(loginUserDeptId);
    }

    @Override
    public PageResult<PayAppDO> getAppPage(PayAppPageReqVO pageReqVO) {
        Long loginUserDeptId = SecurityFrameworkUtils.getLoginUserDeptId();
        
        // 超级管理员(deptId=115)，返回所有应用
        if (loginUserDeptId != null && loginUserDeptId == 115L) {
        return appMapper.selectPage(pageReqVO);
    }
        
        // 非超级管理员：只返回本部门的应用
        return appMapper.selectPageByDeptId(pageReqVO, loginUserDeptId);
    }

    @Override
    public PayAppDO validPayApp(Long appId) {
        PayAppDO app = appMapper.selectById(appId);
        return validatePayApp(app);
    }

    @Override
    public PayAppDO validPayApp(String appKey) {
        PayAppDO app = appMapper.selectByAppKey(appKey);
        return validatePayApp(app);
    }

    /**
     * 校验支付应用实体的有效性：存在 + 开启
     *
     * @param app 待校验的支付应用实体
     * @return 校验通过的支付应用实体
     */
    private PayAppDO validatePayApp(PayAppDO app) {
        // 校验是否存在
        if (app == null) {
            throw exception(ErrorCodeConstants.APP_NOT_FOUND);
        }
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(app.getStatus())) {
            throw exception(ErrorCodeConstants.APP_IS_DISABLE);
        }
        return app;
    }

}
