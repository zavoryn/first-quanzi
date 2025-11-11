package cn.metast.tuoke.module.community.dal.mysql.cmLink;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmLink.CmLinkDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmLink.vo.*;

/**
 * 首页轮播图 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface CmLinkMapper extends BaseMapperX<CmLinkDO> {

    default PageResult<CmLinkDO> selectPage(CmLinkPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CmLinkDO>()
                .eqIfPresent(CmLinkDO::getTitle, reqVO.getTitle())
                .eqIfPresent(CmLinkDO::getUrl, reqVO.getUrl())
                .eqIfPresent(CmLinkDO::getImg, reqVO.getImg())
                .eqIfPresent(CmLinkDO::getType, reqVO.getType())
                .betweenIfPresent(CmLinkDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CmLinkDO::getId));
    }

}