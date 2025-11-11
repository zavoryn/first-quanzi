package cn.metast.tuoke.module.community.dal.mysql.cmBuylog;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmBuylog.CmBuyLogDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo.*;

/**
 * 会员购买记录 Mapper
 *
 * @author adminXq
 */
@Mapper
public interface CmBuyLogMapper extends BaseMapperX<CmBuyLogDO> {

    default PageResult<CmBuyLogDO> selectPage(CmBuyLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmBuyLogDO>()
                .eqIfPresent(CmBuyLogDO::getLevelId, reqVO.getLevelId())
                .eqIfPresent(CmBuyLogDO::getCreator, reqVO.getCreator())
                .likeIfPresent(CmBuyLogDO::getName, reqVO.getName())
                .eqIfPresent(CmBuyLogDO::getLevel, reqVO.getLevel())
                .eqIfPresent(CmBuyLogDO::getExperience, reqVO.getExperience())
                .eqIfPresent(CmBuyLogDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CmBuyLogDO::getPayOrderId, reqVO.getPayOrderId())
                .eqIfPresent(CmBuyLogDO::getPayStatus, reqVO.getPayStatus())
                .betweenIfPresent(CmBuyLogDO::getPayTime, reqVO.getPayTime())
                .eqIfPresent(CmBuyLogDO::getPayPrice, reqVO.getPayPrice())
                .eqIfPresent(CmBuyLogDO::getTotalPrice, reqVO.getTotalPrice())
                .eqIfPresent(CmBuyLogDO::getYzfPrice, reqVO.getYzfPrice())
                .eqIfPresent(CmBuyLogDO::getBrokerageFirstPercent, reqVO.getBrokerageFirstPercent())
                .eqIfPresent(CmBuyLogDO::getBrokerageSecondPercent, reqVO.getBrokerageSecondPercent())
                .betweenIfPresent(CmBuyLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmBuyLogDO::getId));
    }

}
