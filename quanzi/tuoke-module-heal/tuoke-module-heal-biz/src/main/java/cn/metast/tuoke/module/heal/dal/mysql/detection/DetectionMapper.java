package cn.metast.tuoke.module.heal.dal.mysql.detection;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.detection.DetectionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.detection.vo.*;

/**
 * 检测记录 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface DetectionMapper extends BaseMapperX<DetectionDO> {

    default PageResult<DetectionDO> selectPage(DetectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DetectionDO>()
                .eqIfPresent(DetectionDO::getDeviceSn, reqVO.getDeviceSn())
                .eqIfPresent(DetectionDO::getUid, reqVO.getUid())
                .eqIfPresent(DetectionDO::getAid, reqVO.getAid())
                .likeIfPresent(DetectionDO::getName, reqVO.getName())
                .eqIfPresent(DetectionDO::getReport, reqVO.getReport())
                .eqIfPresent(DetectionDO::getType, reqVO.getType())
                .eqIfPresent(DetectionDO::getIswc, reqVO.getIswc())
                .betweenIfPresent(DetectionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DetectionDO::getId));
    }

    public List<DetectionDO> getExcelList(DetectionRespVO respVO);

    public DetectionDO selectByUid(Long uId);
}
