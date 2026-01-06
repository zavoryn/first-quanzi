package cn.metast.tuoke.module.live.dal.mysql.snsCommentreplylike;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentreplylike.SnsCommentReplyLikeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsCommentreplylike.vo.*;

/**
 * 评论回复点赞人数 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsCommentReplyLikeMapper extends BaseMapperX<SnsCommentReplyLikeDO> {

    default PageResult<SnsCommentReplyLikeDO> selectPage(SnsCommentReplyLikePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsCommentReplyLikeDO>()
                .eqIfPresent(SnsCommentReplyLikeDO::getReplyId, reqVO.getReplyId())
                .eqIfPresent(SnsCommentReplyLikeDO::getUserId, reqVO.getUserId())
                .betweenIfPresent(SnsCommentReplyLikeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsCommentReplyLikeDO::getId));
    }

}