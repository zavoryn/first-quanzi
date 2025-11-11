package cn.metast.tuoke.module.community.dal.mysql.cmFollow;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmFollow.CmFollowDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmFollow.vo.*;

/**
 * 用户关注中间 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmFollowMapper extends BaseMapperX<CmFollowDO> {

    default PageResult<CmFollowDO> selectPage(CmFollowPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmFollowDO>()
                .eqIfPresent(CmFollowDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmFollowDO::getFollowUserId, reqVO.getFollowUserId())
                .eqIfPresent(CmFollowDO::getState, reqVO.getState())
                .betweenIfPresent(CmFollowDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmFollowDO::getId));
    }

}