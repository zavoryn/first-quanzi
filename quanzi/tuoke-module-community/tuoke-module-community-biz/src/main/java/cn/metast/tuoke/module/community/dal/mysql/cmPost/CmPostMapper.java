package cn.metast.tuoke.module.community.dal.mysql.cmPost;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.CmTopicMemberPageReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmPost.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户发帖详情 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmPostMapper extends BaseMapperX<CmPostDO> {

    default PageResult<CmPostDO> selectPage(CmPostPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmPostDO>()
                .eqIfPresent(CmPostDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmPostDO::getTopicId, reqVO.getTopicId())
                .eqIfPresent(CmPostDO::getDiscussId, reqVO.getDiscussId())
                .eqIfPresent(CmPostDO::getVoteId, reqVO.getVoteId())
                .eqIfPresent(CmPostDO::getTitle, reqVO.getTitle())
                .eqIfPresent(CmPostDO::getContent, reqVO.getContent())
                .eqIfPresent(CmPostDO::getMedia, reqVO.getMedia())
                .eqIfPresent(CmPostDO::getReadCount, reqVO.getReadCount())
                .eqIfPresent(CmPostDO::getPostTop, reqVO.getPostTop())
                .eqIfPresent(CmPostDO::getType, reqVO.getType())
                .eqIfPresent(CmPostDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CmPostDO::getLongitude, reqVO.getLongitude())
                .eqIfPresent(CmPostDO::getLatitude, reqVO.getLatitude())
                .eqIfPresent(CmPostDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CmPostDO::getSendTime, reqVO.getSendTime())
                .eqIfPresent(CmPostDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CmPostDO::getCateId, reqVO.getCateId())
                .betweenIfPresent(CmPostDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmPostDO::getId));
    }

    IPage<CmPostDO> getCmPostMediaPage(IPage<CmPostDO> page, @Param("reqvo") CmPostPageReqVO reqvo);

    IPage<CmPostDO> getCmPostPageNew(IPage<CmPostDO> page, @Param("reqvo") CmPostPageReqVO reqvo);

    IPage<CmPostDO> getCmPostPageTop(IPage<CmPostDO> page, @Param("reqvo") CmPostPageReqVO reqvo);

    IPage<CmPostDO> getCmPostCollectPage(IPage<CmPostDO> page, @Param("reqvo") CmPostPageReqVO reqvo);

    IPage<CmPostDO> getCmPostLikePage(IPage<CmPostDO> page, @Param("reqvo") CmPostPageReqVO reqvo);
}
