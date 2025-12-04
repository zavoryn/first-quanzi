package cn.metast.tuoke.module.heal.dal.mysql.device;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.device.DeviceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.device.vo.*;

/**
 * 设备信息 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface WnDeviceMapper extends BaseMapperX<DeviceDO> {

    default PageResult<DeviceDO> selectPage(WnDevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceDO>()
                .eqIfPresent(DeviceDO::getDeviceSn, reqVO.getDeviceSn())
                .likeIfPresent(DeviceDO::getName, reqVO.getName())
                .eqIfPresent(DeviceDO::getModel, reqVO.getModel())
                .eqIfPresent(DeviceDO::getUid, reqVO.getUid())
                .betweenIfPresent(DeviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeviceDO::getId));
    }

}
