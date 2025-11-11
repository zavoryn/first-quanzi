package cn.metast.tuoke.module.community.dal.mysql.cmCommentthumbs;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentthumbs.CmCommentThumbsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmCommentthumbs.vo.*;

/**
 * 评论用户关联 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmCommentThumbsMapper extends BaseMapperX<CmCommentThumbsDO> {

    default PageResult<CmCommentThumbsDO> selectPage(CmCommentThumbsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmCommentThumbsDO>()
                .eqIfPresent(CmCommentThumbsDO::getCommentId, reqVO.getCommentId())
                .eqIfPresent(CmCommentThumbsDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmCommentThumbsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CmCommentThumbsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmCommentThumbsDO::getId));
    }

}