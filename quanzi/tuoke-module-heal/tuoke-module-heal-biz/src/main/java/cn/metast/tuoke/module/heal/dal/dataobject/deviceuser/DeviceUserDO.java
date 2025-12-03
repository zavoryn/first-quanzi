package cn.metast.tuoke.module.heal.dal.dataobject.deviceuser;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备绑定用户信息 DO
 *
 * @author 超级管理员
 */
@TableName("wn_device_user")
@KeySequence("wn_device_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUserDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 设备SN编号
     */
    private String deviceSn;
    /**
     * 用户uid
     */
    private Long uid;
    /**
     * 状态(1-默认)
     */
    private String status;

}
