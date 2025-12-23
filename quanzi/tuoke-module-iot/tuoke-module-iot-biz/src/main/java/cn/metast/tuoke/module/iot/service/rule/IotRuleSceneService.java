package cn.metast.tuoke.module.iot.service.rule;

import cn.metast.tuoke.module.iot.dal.dataobject.rule.IotRuleSceneDO;
import cn.metast.tuoke.module.iot.enums.rule.IotRuleSceneTriggerTypeEnum;
import cn.metast.tuoke.module.iot.mq.message.IotDeviceMessage;

import java.util.List;

/**
 * IoT 规则场景 Service 接口
 *
 * @author metast.cn
 */
public interface IotRuleSceneService {

    /**
     * 【缓存】获得指定设备的场景列表
     *
     * @param productKey 产品 Key
     * @param deviceName 设备名称
     * @return 场景列表
     */
    List<IotRuleSceneDO> getRuleSceneListByProductKeyAndDeviceNameFromCache(String productKey, String deviceName);

    /**
     * 基于 {@link IotRuleSceneTriggerTypeEnum#DEVICE} 场景，执行规则场景
     *
     * @param message 消息
     */
    void executeRuleSceneByDevice(IotDeviceMessage message);

    /**
     * 基于 {@link IotRuleSceneTriggerTypeEnum#TIMER} 场景，执行规则场景
     *
     * @param id 场景编号
     */
    void executeRuleSceneByTimer(Long id);

    /**
     * TODO 元圈：测试方法，需要删除
     */
    void test();

}
