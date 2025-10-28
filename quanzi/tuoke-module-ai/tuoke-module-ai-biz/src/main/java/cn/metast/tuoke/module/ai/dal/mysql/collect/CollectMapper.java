package cn.metast.tuoke.module.ai.dal.mysql.collect;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.ai.dal.dataobject.collect.CollectDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.ai.controller.admin.collect.vo.*;

/**
 * AI 功能 收藏 Mapper
 *
 * @author 精卫拓客
 */
@Mapper
public interface CollectMapper extends BaseMapperX<CollectDO> {

    default PageResult<CollectDO> selectPage(CollectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CollectDO>()
                .likeIfPresent(CollectDO::getName, reqVO.getName())
                .eqIfPresent(CollectDO::getCode, reqVO.getCode())
                .eqIfPresent(CollectDO::getUid, reqVO.getUid())
                .eqIfPresent(CollectDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CollectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CollectDO::getId));
    }

    default List<CollectDO> selectList(CollectPageReqVO reqVO) {
        return selectList(
                new LambdaQueryWrapperX<CollectDO>()
                .likeIfPresent(CollectDO::getName, reqVO.getName())
                .eqIfPresent(CollectDO::getCode, reqVO.getCode())
                .eqIfPresent(CollectDO::getUid, reqVO.getUid())
                .eqIfPresent(CollectDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CollectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CollectDO::getId)
        );
    }

}
