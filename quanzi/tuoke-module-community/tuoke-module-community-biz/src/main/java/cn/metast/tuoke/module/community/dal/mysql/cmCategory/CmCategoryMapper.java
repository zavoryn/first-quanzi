package cn.metast.tuoke.module.community.dal.mysql.cmCategory;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmCategory.CmCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmCategory.vo.*;

/**
 * 圈子分类 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmCategoryMapper extends BaseMapperX<CmCategoryDO> {

    default PageResult<CmCategoryDO> selectPage(CmCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmCategoryDO>()
                .likeIfPresent(CmCategoryDO::getCateName, reqVO.getCateName())
                .eqIfPresent(CmCategoryDO::getIsTop, reqVO.getIsTop())
                .eqIfPresent(CmCategoryDO::getCoverImage, reqVO.getCoverImage())
                .eqIfPresent(CmCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(CmCategoryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CmCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CmCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmCategoryDO::getId));
    }

}