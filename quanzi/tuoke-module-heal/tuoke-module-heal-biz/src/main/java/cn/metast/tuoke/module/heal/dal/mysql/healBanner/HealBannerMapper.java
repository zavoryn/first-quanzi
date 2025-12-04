package cn.metast.tuoke.module.heal.dal.mysql.healBanner;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.healBanner.HealBannerDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.healBanner.vo.*;

/**
 * 首页banner Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface HealBannerMapper extends BaseMapperX<HealBannerDO> {

    default PageResult<HealBannerDO> selectPage(HealBannerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HealBannerDO>()
                .eqIfPresent(HealBannerDO::getTitle, reqVO.getTitle())
                .eqIfPresent(HealBannerDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(HealBannerDO::getLinkUrl, reqVO.getLinkUrl())
                .eqIfPresent(HealBannerDO::getStatus, reqVO.getStatus())
                .eqIfPresent(HealBannerDO::getSort, reqVO.getSort())
                .eqIfPresent(HealBannerDO::getType, reqVO.getType())
                .eqIfPresent(HealBannerDO::getRemark, reqVO.getRemark())
                .eqIfPresent(HealBannerDO::getSubject, reqVO.getSubject())
                .eqIfPresent(HealBannerDO::getParam, reqVO.getParam())
                .betweenIfPresent(HealBannerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HealBannerDO::getSort));
    }

}
