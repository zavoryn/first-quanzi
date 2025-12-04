package cn.metast.tuoke.module.heal.dal.mysql.deviceuser;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.heal.dal.dataobject.deviceuser.DeviceUserDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.heal.controller.admin.deviceuser.vo.*;

/**
 * 设备绑定用户信息 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface DeviceUserMapper extends BaseMapperX<DeviceUserDO> {

    default PageResult<DeviceUserDO> selectPage(DeviceUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceUserDO>()
                .eqIfPresent(DeviceUserDO::getDeviceSn, reqVO.getDeviceSn())
                .eqIfPresent(DeviceUserDO::getUid, reqVO.getUid())
                .betweenIfPresent(DeviceUserDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DeviceUserDO::getStatus, reqVO.getStatus())
                .orderByDesc(DeviceUserDO::getId));
    }

}
