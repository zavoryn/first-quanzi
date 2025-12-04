package cn.metast.tuoke.module.heal.service.healBomeconfig;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.metast.tuoke.module.heal.controller.admin.healHomeconfig.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healBomeconfig.HealHomeConfigDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.healBomeconfig.HealHomeConfigMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.HOME_CONFIG_NOT_EXISTS;

/**
 * 首页模块配置 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class HealHomeConfigServiceImpl implements HealHomeConfigService {

    @Resource
    private HealHomeConfigMapper homeConfigMapper;

    @Override
    public Long createHomeConfig(HealHomeConfigSaveReqVO createReqVO) {
        // 插入
        HealHomeConfigDO homeConfig = BeanUtils.toBean(createReqVO, HealHomeConfigDO.class);
        homeConfigMapper.insert(homeConfig);
        // 返回
        return homeConfig.getId();
    }

    @Override
    public void updateHomeConfig(HealHomeConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateHomeConfigExists(updateReqVO.getId());
        // 更新
        HealHomeConfigDO updateObj = BeanUtils.toBean(updateReqVO, HealHomeConfigDO.class);
        homeConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteHomeConfig(Long id) {
        // 校验存在
        validateHomeConfigExists(id);
        // 删除
        homeConfigMapper.deleteById(id);
    }

    private void validateHomeConfigExists(Long id) {
        if (homeConfigMapper.selectById(id) == null) {
            throw exception(HOME_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public HealHomeConfigDO getHomeConfig(Long id) {
        return homeConfigMapper.selectById(id);
    }

    @Override
    public PageResult<HealHomeConfigDO> getHomeConfigPage(HealHomeConfigPageReqVO pageReqVO) {
        return homeConfigMapper.selectPage(pageReqVO);
    }

}
