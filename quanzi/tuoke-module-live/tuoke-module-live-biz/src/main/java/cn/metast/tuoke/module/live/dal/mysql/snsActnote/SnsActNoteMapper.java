package cn.metast.tuoke.module.live.dal.mysql.snsActnote;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsActnote.SnsActNoteDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsActnote.vo.*;

/**
 * 活动记录 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsActNoteMapper extends BaseMapperX<SnsActNoteDO> {

    default PageResult<SnsActNoteDO> selectPage(SnsActNotePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsActNoteDO>()
                .eqIfPresent(SnsActNoteDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SnsActNoteDO::getAddr, reqVO.getAddr())
                .betweenIfPresent(SnsActNoteDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsActNoteDO::getId));
    }

}