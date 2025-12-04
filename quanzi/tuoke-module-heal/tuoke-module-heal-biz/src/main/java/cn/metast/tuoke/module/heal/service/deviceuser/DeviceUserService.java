package cn.metast.tuoke.module.heal.service.deviceuser;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.deviceuser.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.deviceuser.DeviceUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 设备绑定用户信息 Service 接口
 *
 * @author 超级管理员
 */
public interface DeviceUserService {

    /**
     * 创建设备绑定用户信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeviceUser(@Valid DeviceUserSaveReqVO createReqVO);

    /**
     * 更新设备绑定用户信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDeviceUser(@Valid DeviceUserSaveReqVO updateReqVO);

    /**
     * 删除设备绑定用户信息
     *
     * @param id 编号
     */
    void deleteDeviceUser(Long id);

    /**
     * 获得设备绑定用户信息
     *
     * @param id 编号
     * @return 设备绑定用户信息
     */
    DeviceUserDO getDeviceUser(Long id);

    /**
     * 获得设备绑定用户信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备绑定用户信息分页
     */
    PageResult<DeviceUserDO> getDeviceUserPage(DeviceUserPageReqVO pageReqVO);

}
