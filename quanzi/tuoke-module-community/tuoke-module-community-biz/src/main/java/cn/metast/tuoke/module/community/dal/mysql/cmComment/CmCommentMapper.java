package cn.metast.tuoke.module.community.dal.mysql.cmComment;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.QueryWrapperX;
import cn.metast.tuoke.module.community.controller.admin.cmPost.vo.CmPostPageReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmComment.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 圈子评论 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmCommentMapper extends BaseMapperX<CmCommentDO> {

    default PageResult<CmCommentDO> selectPage(CmCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmCommentDO>()
                .eqIfPresent(CmCommentDO::getParentId, reqVO.getParentId())
                .eqIfPresent(CmCommentDO::getType, reqVO.getType())
                .eqIfPresent(CmCommentDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmCommentDO::getToUserId, reqVO.getToUserId())
                .eqIfPresent(CmCommentDO::getPostId, reqVO.getPostId())
                .eqIfPresent(CmCommentDO::getContent, reqVO.getContent())
                .eqIfPresent(CmCommentDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CmCommentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmCommentDO::getId));
    }

    default List<CmCommentDO> getCmCommentPageShow(CmCommentPageReqVO reqVO) {
       /* return selectList(new QueryWrapperX<CmCommentDO>()
                .eqIfPresent("post_id", reqVO.getPostId())
                .eqIfPresent("parent_id", reqVO.getParentId())
                .and(wrapper -> wrapper
                        .eq("user_id", reqVO.getUserId())  // 自己评论
                        .or()
                        .eq("status", 0)  // 已审核评论
                )
                .orderByDesc("id"));*/
        return selectList(new LambdaQueryWrapperX<CmCommentDO>()
                .eqIfPresent(CmCommentDO::getPostId, reqVO.getPostId())
                .eqIfPresent(CmCommentDO::getParentId, reqVO.getParentId())
                .notIn(reqVO.getBlockUserIds() != null && !reqVO.getBlockUserIds().isEmpty(),
                        CmCommentDO::getUserId, reqVO.getBlockUserIds())
                .and(wrapper -> wrapper
                        .eq(CmCommentDO::getUserId, reqVO.getUserId())  // 自己的消息
                        .or()
                        .eq(CmCommentDO::getStatus, 0)  // 或者其他人已审核的消息
                )
                .orderByDesc(CmCommentDO::getId));
    }


    default Long selectCmCommentCount(Long postId,Long userId){
        return selectCount (new QueryWrapperX<CmCommentDO>()
                .eqIfPresent("post_id", postId)
                .eqIfPresent("parent_id", 0L)
                .and(wrapper -> wrapper
                        .eq("user_id", userId)  // 自己评论
                        .or()
                        .eq("status", 0)  // 已审核评论
                ));
      }
    IPage<CmCommentDO> getCmCommentPageNew(IPage<CmCommentDO> page, @Param("reqvo") CmCommentPageReqVO reqvo);

}
