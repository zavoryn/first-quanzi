package cn.metast.tuoke.module.community.dal.mysql.cmPostlike;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostlike.CmPostLikeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmPostlike.vo.*;

/**
 * 帖子点赞 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmPostLikeMapper extends BaseMapperX<CmPostLikeDO> {

    default PageResult<CmPostLikeDO> selectPage(CmPostLikePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmPostLikeDO>()
                .eqIfPresent(CmPostLikeDO::getPostId, reqVO.getPostId())
                .eqIfPresent(CmPostLikeDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmPostLikeDO::getState, reqVO.getState())
                .betweenIfPresent(CmPostLikeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmPostLikeDO::getId));
    }

}