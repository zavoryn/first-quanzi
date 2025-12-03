package cn.metast.tuoke.module.heal.dal.dataobject.device;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备信息 DO
 *
 * @author 超级管理员
 */
@TableName("wn_device")
@KeySequence("wn_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;

    private Long uid;
    @TableField(exist = false)
    private String uname;
    /**
     * 设备SN编号
     */
    private String deviceSn;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备型号
     */
    private String model;

}
