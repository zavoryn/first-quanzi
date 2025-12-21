package cn.metast.tuoke.module.iot.dal.dataobject.rule;

import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;
import cn.metast.tuoke.module.iot.enums.rule.IotAlertConfigReceiveTypeEnum;
import cn.metast.tuoke.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * IoT 告警配置 DO
 *
 * @author metast.cn
 */
@TableName("iot_alert_config")
@KeySequence("iot_alert_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotAlertConfig extends BaseDO {

    /**
     * 配置编号
     */
    @TableId
    private Long id;
    /**
     * 配置名称
     */
    private String name;
    /**
     * 配置描述
     */
    private String description;
    /**
     * 配置状态
     *
     * TODO 数据字典
     */
    private Integer level;
    /**
     * 配置状态
     *
     * 枚举 {@link cn.metast.tuoke.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;

    /**
     * 关联的规则场景编号数组
     *
     * 关联 {@link IotRuleSceneDO#getId()}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> ruleSceneIds;

    /**
     * 接收的用户编号数组
     *
     * 关联 {@link AdminUserRespDTO#getId()}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> receiveUserIds;
    /**
     * 接收的类型数组
     *
     * 枚举 {@link IotAlertConfigReceiveTypeEnum}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Integer> receiveTypes;

}
