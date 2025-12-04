package cn.metast.tuoke.module.heal.service.device;

import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.device.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.device.DeviceDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;

/**
 * 设备信息 Service 接口
 *
 * @author 超级管理员
 */
public interface WnDeviceService {

    /**
     * 创建设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDevice(@Valid WnDeviceSaveReqVO createReqVO);

    /**
     * 更新设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDevice(@Valid WnDeviceSaveReqVO updateReqVO);

    /**
     * 删除设备信息
     *
     * @param id 编号
     */
    void deleteDevice(Long id);

    /**
     * 获得设备信息
     *
     * @param id 编号
     * @return 设备信息
     */
    DeviceDO getDevice(Long id);

    /**
     * 获得设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备信息分页
     */
    PageResult<DeviceDO> getDevicePage(WnDevicePageReqVO pageReqVO);

}
