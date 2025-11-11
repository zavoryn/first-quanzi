package cn.metast.tuoke.module.community.dal.mysql.cmCollect;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmCollect.CmCollectDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmCollect.vo.*;

/**
 * 收藏记录 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmCollectMapper extends BaseMapperX<CmCollectDO> {

    default PageResult<CmCollectDO> selectPage(CmCollectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmCollectDO>()
                .eqIfPresent(CmCollectDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmCollectDO::getPostId, reqVO.getPostId())
                .eqIfPresent(CmCollectDO::getState, reqVO.getState())
                .betweenIfPresent(CmCollectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmCollectDO::getId));
    }

}