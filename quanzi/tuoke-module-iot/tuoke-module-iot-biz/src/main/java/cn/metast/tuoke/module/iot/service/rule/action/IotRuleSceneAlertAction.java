package cn.metast.tuoke.module.iot.service.rule.action;

import cn.metast.tuoke.module.iot.dal.dataobject.rule.IotRuleSceneDO;
import cn.metast.tuoke.module.iot.enums.rule.IotRuleSceneActionTypeEnum;
import cn.metast.tuoke.module.iot.mq.message.IotDeviceMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * IoT 告警的 {@link IotRuleSceneAction} 实现类
 *
 * @author metast.cn
 */
@Component
public class IotRuleSceneAlertAction implements IotRuleSceneAction {

    @Override
    public void execute(@Nullable IotDeviceMessage message, IotRuleSceneDO.ActionConfig config) {
        // TODO @元圈：待实现
    }

    @Override
    public IotRuleSceneActionTypeEnum getType() {
        return IotRuleSceneActionTypeEnum.ALERT;
    }

}
