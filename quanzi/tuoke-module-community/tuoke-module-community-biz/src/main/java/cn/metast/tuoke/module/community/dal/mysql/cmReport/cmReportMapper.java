package cn.metast.tuoke.module.community.dal.mysql.cmReport;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmReport.cmReportDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmReport.vo.*;

/**
 * 举报记录 Mapper
 *
 * @author adminXq
 */
@Mapper
public interface cmReportMapper extends BaseMapperX<cmReportDO> {

    default PageResult<cmReportDO> selectPage(cmReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<cmReportDO>()
                .eqIfPresent(cmReportDO::getUserId, reqVO.getUserId())
                .eqIfPresent(cmReportDO::getReportUserId, reqVO.getReportUserId())
                .eqIfPresent(cmReportDO::getReason, reqVO.getReason())
                .eqIfPresent(cmReportDO::getTopicId, reqVO.getTopicId())
                .betweenIfPresent(cmReportDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(cmReportDO::getId));
    }

}
