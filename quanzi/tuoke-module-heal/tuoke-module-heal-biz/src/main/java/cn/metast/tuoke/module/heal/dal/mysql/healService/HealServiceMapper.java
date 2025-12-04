package cn.metast.tuoke.module.heal.dal.mysql.healService;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.healService.HealServiceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.healService.vo.*;

/**
 * 服务列 Mapper
 *
 * @author 苏丹家园
 */
@Mapper
public interface HealServiceMapper extends BaseMapperX<HealServiceDO> {

    default PageResult<HealServiceDO> selectPage(HealServicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HealServiceDO>()
                .eqIfPresent(HealServiceDO::getTitle, reqVO.getTitle())
                .eqIfPresent(HealServiceDO::getIntroduce, reqVO.getIntroduce())
                .eqIfPresent(HealServiceDO::getCoverUrl, reqVO.getCoverUrl())
                .eqIfPresent(HealServiceDO::getContent, reqVO.getContent())
                .eqIfPresent(HealServiceDO::getLinkUrl, reqVO.getLinkUrl())
                .eqIfPresent(HealServiceDO::getFee, reqVO.getFee())
                .eqIfPresent(HealServiceDO::getSort, reqVO.getSort())
                .eqIfPresent(HealServiceDO::getShareNum, reqVO.getShareNum())
                .eqIfPresent(HealServiceDO::getLikeNum, reqVO.getLikeNum())
                .eqIfPresent(HealServiceDO::getCommentNum, reqVO.getCommentNum())
                .eqIfPresent(HealServiceDO::getVisitNum, reqVO.getVisitNum())
                .betweenIfPresent(HealServiceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HealServiceDO::getSort));
    }

}
