package cn.metast.tuoke.module.community.dal.mysql.cmCommentlike;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentlike.CmCommentLikeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmCommentlike.vo.*;

/**
 * 评论点赞 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmCommentLikeMapper extends BaseMapperX<CmCommentLikeDO> {

    default PageResult<CmCommentLikeDO> selectPage(CmCommentLikePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmCommentLikeDO>()
                .eqIfPresent(CmCommentLikeDO::getCommentId, reqVO.getCommentId())
                .eqIfPresent(CmCommentLikeDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmCommentLikeDO::getState, reqVO.getState())
                .betweenIfPresent(CmCommentLikeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmCommentLikeDO::getId));
    }

}