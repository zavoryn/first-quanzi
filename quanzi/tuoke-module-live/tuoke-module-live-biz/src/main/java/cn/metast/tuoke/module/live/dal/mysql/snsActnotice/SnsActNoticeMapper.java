package cn.metast.tuoke.module.live.dal.mysql.snsActnotice;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActnotice.SnsActNoticeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActnotice.vo.*;

/**
 * 活动公告 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActNoticeMapper extends BaseMapperX<SnsActNoticeDO> {

    default PageResult<SnsActNoticeDO> selectPage(SnsActNoticePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActNoticeDO>()
                .eqIfPresent(SnsActNoticeDO::getActId, reqVO.getActId())
                .eqIfPresent(SnsActNoticeDO::getTitle, reqVO.getTitle())
                .betweenIfPresent(SnsActNoticeDO::getTime, reqVO.getTime())
                .eqIfPresent(SnsActNoticeDO::getContext, reqVO.getContext())
                .eqIfPresent(SnsActNoticeDO::getNoticeType, reqVO.getNoticeType())
                .eqIfPresent(SnsActNoticeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SnsActNoticeDO::getRe, reqVO.getRe())
                .eqIfPresent(SnsActNoticeDO::getFromId, reqVO.getFromId())
                .eqIfPresent(SnsActNoticeDO::getToId, reqVO.getToId())
                .eqIfPresent(SnsActNoticeDO::getScope, reqVO.getScope())
                .betweenIfPresent(SnsActNoticeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActNoticeDO::getId));
    }

}