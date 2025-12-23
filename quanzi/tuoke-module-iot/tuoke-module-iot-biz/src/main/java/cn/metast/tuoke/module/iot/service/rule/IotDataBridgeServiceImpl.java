package cn.metast.tuoke.module.iot.service.rule;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.iot.controller.admin.rule.vo.databridge.IotDataBridgePageReqVO;
import cn.metast.tuoke.module.iot.controller.admin.rule.vo.databridge.IotDataBridgeSaveReqVO;
import cn.metast.tuoke.module.iot.dal.dataobject.rule.IotDataBridgeDO;
import cn.metast.tuoke.module.iot.dal.mysql.rule.IotDataBridgeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.iot.enums.ErrorCodeConstants.DATA_BRIDGE_NOT_EXISTS;

/**
 * IoT 数据桥梁 Service 实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class IotDataBridgeServiceImpl implements IotDataBridgeService {

    @Resource
    private IotDataBridgeMapper dataBridgeMapper;

    @Override
    public Long createDataBridge(IotDataBridgeSaveReqVO createReqVO) {
        // 插入
        IotDataBridgeDO dataBridge = BeanUtils.toBean(createReqVO, IotDataBridgeDO.class);
        dataBridgeMapper.insert(dataBridge);
        // 返回
        return dataBridge.getId();
    }

    @Override
    public void updateDataBridge(IotDataBridgeSaveReqVO updateReqVO) {
        // 校验存在
        validateDataBridgeExists(updateReqVO.getId());
        // 更新
        IotDataBridgeDO updateObj = BeanUtils.toBean(updateReqVO, IotDataBridgeDO.class);
        dataBridgeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataBridge(Long id) {
        // 校验存在
        validateDataBridgeExists(id);
        // 删除
        dataBridgeMapper.deleteById(id);
    }

    private void validateDataBridgeExists(Long id) {
        if (dataBridgeMapper.selectById(id) == null) {
            throw exception(DATA_BRIDGE_NOT_EXISTS);
        }
    }

    @Override
    public IotDataBridgeDO getDataBridge(Long id) {
        return dataBridgeMapper.selectById(id);
    }

    @Override
    public PageResult<IotDataBridgeDO> getDataBridgePage(IotDataBridgePageReqVO pageReqVO) {
        return dataBridgeMapper.selectPage(pageReqVO);
    }

}