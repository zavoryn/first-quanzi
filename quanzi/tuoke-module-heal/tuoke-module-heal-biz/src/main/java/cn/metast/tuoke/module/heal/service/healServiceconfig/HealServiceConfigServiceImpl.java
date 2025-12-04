package cn.metast.tuoke.module.heal.service.healServiceconfig;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.heal.controller.admin.healServiceconfig.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healServiceconfig.HealServiceConfigDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.healServiceconfig.HealServiceConfigMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.*;

/**
 * 服务配置 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class HealServiceConfigServiceImpl implements HealServiceConfigService {

    @Resource
    private HealServiceConfigMapper serviceConfigMapper;

    @Override
    public Long createServiceConfig(HealServiceConfigSaveReqVO createReqVO) {
        // 插入
        HealServiceConfigDO serviceConfig = BeanUtils.toBean(createReqVO, HealServiceConfigDO.class);
        serviceConfigMapper.insert(serviceConfig);
        // 返回
        return serviceConfig.getId();
    }

    @Override
    public void updateServiceConfig(HealServiceConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateServiceConfigExists(updateReqVO.getId());
        // 更新
        HealServiceConfigDO updateObj = BeanUtils.toBean(updateReqVO, HealServiceConfigDO.class);
        serviceConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteServiceConfig(Long id) {
        // 校验存在
        validateServiceConfigExists(id);
        // 删除
        serviceConfigMapper.deleteById(id);
    }

    private void validateServiceConfigExists(Long id) {
        if (serviceConfigMapper.selectById(id) == null) {
            throw exception(SERVICE_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public HealServiceConfigDO getServiceConfig(Long id) {
        return serviceConfigMapper.selectById(id);
    }

    @Override
    public PageResult<HealServiceConfigDO> getServiceConfigPage(HealServiceConfigPageReqVO pageReqVO) {
        return serviceConfigMapper.selectPage(pageReqVO);
    }

}
