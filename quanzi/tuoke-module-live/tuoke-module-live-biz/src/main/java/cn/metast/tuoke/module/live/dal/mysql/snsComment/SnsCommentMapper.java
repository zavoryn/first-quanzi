package cn.metast.tuoke.module.live.dal.mysql.snsComment;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsComment.SnsCommentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsComment.vo.*;

/**
 * 评论 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsCommentMapper extends BaseMapperX<SnsCommentDO> {

    public int deleteSnsCommentByPostId(Long postId);

    public List<SnsCommentDO> selectSnsCommentList(SnsCommentDO snsComment);

    default PageResult<SnsCommentDO> selectPage(SnsCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsCommentDO>()
                .eqIfPresent(SnsCommentDO::getCommentId, reqVO.getCommentId())
                .eqIfPresent(SnsCommentDO::getPostId, reqVO.getPostId())
                .eqIfPresent(SnsCommentDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SnsCommentDO::getContent, reqVO.getContent())
                .eqIfPresent(SnsCommentDO::getLikeNum, reqVO.getLikeNum())
                .eqIfPresent(SnsCommentDO::getTail, reqVO.getTail())
                .eqIfPresent(SnsCommentDO::getType, reqVO.getType())
                .betweenIfPresent(SnsCommentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsCommentDO::getId));
    }

}
