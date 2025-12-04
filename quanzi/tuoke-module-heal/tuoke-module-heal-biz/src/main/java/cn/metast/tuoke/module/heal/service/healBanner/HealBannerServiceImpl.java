package cn.metast.tuoke.module.heal.service.healBanner;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.heal.controller.admin.healBanner.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healBanner.HealBannerDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.healBanner.HealBannerMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.*;

/**
 * 首页banner Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class HealBannerServiceImpl implements HealBannerService {

    @Resource
    private HealBannerMapper healBannerMapper;

    @Override
    public Long createBanner(HealBannerSaveReqVO createReqVO) {
        // 插入
        HealBannerDO banner = BeanUtils.toBean(createReqVO, HealBannerDO.class);
        healBannerMapper.insert(banner);
        // 返回
        return banner.getId();
    }

    @Override
    public void updateBanner(HealBannerSaveReqVO updateReqVO) {
        // 校验存在
        validateBannerExists(updateReqVO.getId());
        // 更新
        HealBannerDO updateObj = BeanUtils.toBean(updateReqVO, HealBannerDO.class);
        healBannerMapper.updateById(updateObj);
    }

    @Override
    public void deleteBanner(Long id) {
        // 校验存在
        validateBannerExists(id);
        // 删除
        healBannerMapper.deleteById(id);
    }

    private void validateBannerExists(Long id) {
        if (healBannerMapper.selectById(id) == null) {
            throw exception(BANNER_NOT_EXISTS);
        }
    }

    @Override
    public HealBannerDO getBanner(Long id) {
        return healBannerMapper.selectById(id);
    }

    @Override
    public PageResult<HealBannerDO> getBannerPage(HealBannerPageReqVO pageReqVO) {
        return healBannerMapper.selectPage(pageReqVO);
    }

}
