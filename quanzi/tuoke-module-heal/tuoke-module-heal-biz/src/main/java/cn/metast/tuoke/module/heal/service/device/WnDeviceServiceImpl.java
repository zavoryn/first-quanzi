package cn.metast.tuoke.module.heal.service.device;

import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.metast.tuoke.module.heal.controller.admin.device.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.device.DeviceDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.device.WnDeviceMapper;

import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.DEVICE_NOT_EXISTS;

/**
 * 设备信息 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class WnDeviceServiceImpl implements WnDeviceService {

    @Resource
    private WnDeviceMapper wnDeviceMapper;
    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public Long createDevice(WnDeviceSaveReqVO createReqVO) {
        // 插入
        DeviceDO device = BeanUtils.toBean(createReqVO, DeviceDO.class);
        wnDeviceMapper.insert(device);
        // 返回
        return device.getId();
    }

    @Override
    public void updateDevice(WnDeviceSaveReqVO updateReqVO) {
        // 校验存在
        validateDeviceExists(updateReqVO.getId());
        // 更新
        DeviceDO updateObj = BeanUtils.toBean(updateReqVO, DeviceDO.class);
        wnDeviceMapper.updateById(updateObj);
    }

    @Override
    public void deleteDevice(Long id) {
        // 校验存在
        validateDeviceExists(id);
        // 删除
        wnDeviceMapper.deleteById(id);
    }

    private void validateDeviceExists(Long id) {
        if (wnDeviceMapper.selectById(id) == null) {
            throw exception(DEVICE_NOT_EXISTS);
        }
    }

    @Override
    public DeviceDO getDevice(Long id) {
        return wnDeviceMapper.selectById(id);
    }

    @Override
    public PageResult<DeviceDO> getDevicePage(WnDevicePageReqVO pageReqVO) {
        PageResult<DeviceDO> result = wnDeviceMapper.selectPage(pageReqVO);
        List<DeviceDO> list = result.getList();
        if(list.size() > 0){
            for(DeviceDO item : list){
                if(item.getUid() != null){
                    MemberUserRespDTO user = memberUserApi.getUser(item.getUid());
                    if(user != null){
                        item.setUname(user.getNickname());
                    }
                }
            }
            result.setList(list);
        }
        return result;
    }

}
