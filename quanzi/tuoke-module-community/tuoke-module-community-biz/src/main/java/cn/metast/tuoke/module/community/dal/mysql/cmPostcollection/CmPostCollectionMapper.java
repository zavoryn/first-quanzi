package cn.metast.tuoke.module.community.dal.mysql.cmPostcollection;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostcollection.CmPostCollectionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmPostcollection.vo.*;

/**
 * 用户帖子中间 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmPostCollectionMapper extends BaseMapperX<CmPostCollectionDO> {

    default PageResult<CmPostCollectionDO> selectPage(CmPostCollectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmPostCollectionDO>()
                .eqIfPresent(CmPostCollectionDO::getUserId, reqVO.getUserId())
                .eqIfPresent(CmPostCollectionDO::getPostId, reqVO.getPostId())
                .orderByDesc(CmPostCollectionDO::getId));
    }

}