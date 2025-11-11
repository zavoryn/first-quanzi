package cn.metast.tuoke.module.community.dal.mysql.cmMessage;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmMessage.CmMessageDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmMessage.vo.*;

/**
 * 圈子消息 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmMessageMapper extends BaseMapperX<CmMessageDO> {

    default PageResult<CmMessageDO> selectPage(CmMessagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmMessageDO>()
                .eqIfPresent(CmMessageDO::getFromUserId, reqVO.getFromUserId())
                .eqIfPresent(CmMessageDO::getToUserId, reqVO.getToUserId())
                .eqIfPresent(CmMessageDO::getType, reqVO.getType())
                .eqIfPresent(CmMessageDO::getContent, reqVO.getContent())
                .eqIfPresent(CmMessageDO::getPostId, reqVO.getPostId())
                .eqIfPresent(CmMessageDO::getCommentId, reqVO.getCommentId())
                .eqIfPresent(CmMessageDO::getIsRead, reqVO.getIsRead())
                .betweenIfPresent(CmMessageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmMessageDO::getId));
    }

}