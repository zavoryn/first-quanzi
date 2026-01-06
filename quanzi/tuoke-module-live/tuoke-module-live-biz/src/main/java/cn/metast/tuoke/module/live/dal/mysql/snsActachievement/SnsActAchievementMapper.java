package cn.metast.tuoke.module.live.dal.mysql.snsActachievement;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActachievement.SnsActAchievementDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActachievement.vo.*;

/**
 * 活动-成绩 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActAchievementMapper extends BaseMapperX<SnsActAchievementDO> {

    default PageResult<SnsActAchievementDO> selectPage(SnsActAchievementPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActAchievementDO>()
                .likeIfPresent(SnsActAchievementDO::getName, reqVO.getName())
                .eqIfPresent(SnsActAchievementDO::getLogo, reqVO.getLogo())
                .eqIfPresent(SnsActAchievementDO::getFraction, reqVO.getFraction())
                .likeIfPresent(SnsActAchievementDO::getToname, reqVO.getToname())
                .eqIfPresent(SnsActAchievementDO::getTologo, reqVO.getTologo())
                .eqIfPresent(SnsActAchievementDO::getTofraction, reqVO.getTofraction())
                .eqIfPresent(SnsActAchievementDO::getCreateUserId, reqVO.getCreateUserId())
                .betweenIfPresent(SnsActAchievementDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActAchievementDO::getId));
    }

}