package cn.metast.tuoke.module.live.service.snsAppconfig;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsAppconfig.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAppconfig.SnsAppConfigDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsAppconfig.SnsAppConfigMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 配置 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsAppConfigServiceImpl implements SnsAppConfigService {

    @Resource
    private SnsAppConfigMapper snsAppConfigMapper;

    @Override
    public Long createSnsAppConfig(SnsAppConfigSaveReqVO createReqVO) {
        // 插入
        SnsAppConfigDO snsAppConfig = BeanUtils.toBean(createReqVO, SnsAppConfigDO.class);
        snsAppConfigMapper.insert(snsAppConfig);
        // 返回
        return snsAppConfig.getId();
    }

    @Override
    public void updateSnsAppConfig(SnsAppConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsAppConfigExists(updateReqVO.getId());
        // 更新
        SnsAppConfigDO updateObj = BeanUtils.toBean(updateReqVO, SnsAppConfigDO.class);
        snsAppConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsAppConfig(Long id) {
        // 校验存在
        validateSnsAppConfigExists(id);
        // 删除
        snsAppConfigMapper.deleteById(id);
    }

    private void validateSnsAppConfigExists(Long id) {
        if (snsAppConfigMapper.selectById(id) == null) {
            throw exception(SNS_APP_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public SnsAppConfigDO getSnsAppConfig(Long id) {
        return snsAppConfigMapper.selectById(id);
    }

    @Override
    public PageResult<SnsAppConfigDO> getSnsAppConfigPage(SnsAppConfigPageReqVO pageReqVO) {
        return snsAppConfigMapper.selectPage(pageReqVO);
    }

}