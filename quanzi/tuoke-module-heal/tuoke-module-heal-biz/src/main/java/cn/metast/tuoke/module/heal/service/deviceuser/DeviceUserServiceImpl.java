package cn.metast.tuoke.module.heal.service.deviceuser;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.heal.controller.admin.deviceuser.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.deviceuser.DeviceUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.deviceuser.DeviceUserMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.DEVICE_USER_NOT_EXISTS;

/**
 * 设备绑定用户信息 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class DeviceUserServiceImpl implements DeviceUserService {

    @Resource
    private DeviceUserMapper deviceUserMapper;

    @Override
    public Long createDeviceUser(DeviceUserSaveReqVO createReqVO) {
        // 插入
        DeviceUserDO deviceUser = BeanUtils.toBean(createReqVO, DeviceUserDO.class);
        deviceUserMapper.insert(deviceUser);
        // 返回
        return deviceUser.getId();
    }

    @Override
    public void updateDeviceUser(DeviceUserSaveReqVO updateReqVO) {
        // 校验存在
        validateDeviceUserExists(updateReqVO.getId());
        // 更新
        DeviceUserDO updateObj = BeanUtils.toBean(updateReqVO, DeviceUserDO.class);
        deviceUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeviceUser(Long id) {
        // 校验存在
        validateDeviceUserExists(id);
        // 删除
        deviceUserMapper.deleteById(id);
    }

    private void validateDeviceUserExists(Long id) {
        if (deviceUserMapper.selectById(id) == null) {
            throw exception(DEVICE_USER_NOT_EXISTS);
        }
    }

    @Override
    public DeviceUserDO getDeviceUser(Long id) {
        return deviceUserMapper.selectById(id);
    }

    @Override
    public PageResult<DeviceUserDO> getDeviceUserPage(DeviceUserPageReqVO pageReqVO) {
        return deviceUserMapper.selectPage(pageReqVO);
    }

}
