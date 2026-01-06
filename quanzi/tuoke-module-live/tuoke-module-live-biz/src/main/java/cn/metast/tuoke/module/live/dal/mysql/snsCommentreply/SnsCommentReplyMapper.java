package cn.metast.tuoke.module.live.dal.mysql.snsCommentreply;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentreply.SnsCommentReplyDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsCommentreply.vo.*;

/**
 * 评论回复 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsCommentReplyMapper extends BaseMapperX<SnsCommentReplyDO> {

    public int deleteSnsCommentReplyByCommentid(Long commentId);
    default PageResult<SnsCommentReplyDO> selectPage(SnsCommentReplyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsCommentReplyDO>()
                .eqIfPresent(SnsCommentReplyDO::getReplyId, reqVO.getReplyId())
                .eqIfPresent(SnsCommentReplyDO::getCommentId, reqVO.getCommentId())
                .eqIfPresent(SnsCommentReplyDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SnsCommentReplyDO::getContent, reqVO.getContent())
                .eqIfPresent(SnsCommentReplyDO::getLikeNum, reqVO.getLikeNum())
                .betweenIfPresent(SnsCommentReplyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsCommentReplyDO::getId));
    }

}
