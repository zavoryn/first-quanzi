package cn.metast.tuoke.module.live.dal.mysql.snsActschedule;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActschedule.SnsActScheduleDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActschedule.vo.*;

/**
 * 活动日程 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActScheduleMapper extends BaseMapperX<SnsActScheduleDO> {

    default PageResult<SnsActScheduleDO> selectPage(SnsActSchedulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActScheduleDO>()
                .eqIfPresent(SnsActScheduleDO::getActId, reqVO.getActId())
                .likeIfPresent(SnsActScheduleDO::getItemName, reqVO.getItemName())
                .likeIfPresent(SnsActScheduleDO::getSubName, reqVO.getSubName())
                .betweenIfPresent(SnsActScheduleDO::getSubStime, reqVO.getSubStime())
                .betweenIfPresent(SnsActScheduleDO::getSubEtime, reqVO.getSubEtime())
                .eqIfPresent(SnsActScheduleDO::getSubContext, reqVO.getSubContext())
                .eqIfPresent(SnsActScheduleDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(SnsActScheduleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActScheduleDO::getId));
    }

}