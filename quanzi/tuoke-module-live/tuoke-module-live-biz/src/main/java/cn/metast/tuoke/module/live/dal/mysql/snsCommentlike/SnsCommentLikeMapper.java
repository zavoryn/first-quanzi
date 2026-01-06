package cn.metast.tuoke.module.live.dal.mysql.snsCommentlike;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentlike.SnsCommentLikeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.live.controller.admin.snsCommentlike.vo.*;

/**
 * 评论点赞人 Mapper
 *
 * @author 夏兆金
 */
@Mapper
public interface SnsCommentLikeMapper extends BaseMapperX<SnsCommentLikeDO> {

    public int deleteSnsCommentLikeByCommentid(Long commentId);

    default PageResult<SnsCommentLikeDO> selectPage(SnsCommentLikePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SnsCommentLikeDO>()
                .eqIfPresent(SnsCommentLikeDO::getPostId, reqVO.getPostId())
                .eqIfPresent(SnsCommentLikeDO::getCommentId, reqVO.getCommentId())
                .eqIfPresent(SnsCommentLikeDO::getUserId, reqVO.getUserId())
                .betweenIfPresent(SnsCommentLikeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SnsCommentLikeDO::getId));
    }

}
