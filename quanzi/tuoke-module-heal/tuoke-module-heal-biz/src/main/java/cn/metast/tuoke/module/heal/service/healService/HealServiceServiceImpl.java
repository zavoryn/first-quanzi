package cn.metast.tuoke.module.heal.service.healService;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.heal.controller.admin.healService.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healService.HealServiceDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.healService.HealServiceMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.*;

/**
 * 服务列 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class HealServiceServiceImpl implements HealServiceService {

    @Resource
    private HealServiceMapper serviceMapper;

    @Override
    public Long createService(HealServiceSaveReqVO createReqVO) {
        // 插入
        HealServiceDO service = BeanUtils.toBean(createReqVO, HealServiceDO.class);
        serviceMapper.insert(service);
        // 返回
        return service.getId();
    }

    @Override
    public void updateService(HealServiceSaveReqVO updateReqVO) {
        // 校验存在
        validateServiceExists(updateReqVO.getId());
        // 更新
        HealServiceDO updateObj = BeanUtils.toBean(updateReqVO, HealServiceDO.class);
        serviceMapper.updateById(updateObj);
    }

    @Override
    public void deleteService(Long id) {
        // 校验存在
        validateServiceExists(id);
        // 删除
        serviceMapper.deleteById(id);
    }

    private void validateServiceExists(Long id) {
        if (serviceMapper.selectById(id) == null) {
            throw exception(SERVICE_NOT_EXISTS);
        }
    }

    @Override
    public HealServiceDO getService(Long id) {
        return serviceMapper.selectById(id);
    }

    @Override
    public PageResult<HealServiceDO> getServicePage(HealServicePageReqVO pageReqVO) {
        return serviceMapper.selectPage(pageReqVO);
    }

}
