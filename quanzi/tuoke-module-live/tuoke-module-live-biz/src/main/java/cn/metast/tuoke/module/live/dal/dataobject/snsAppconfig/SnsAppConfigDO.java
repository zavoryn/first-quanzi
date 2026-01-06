package cn.metast.tuoke.module.live.dal.dataobject.snsAppconfig;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 配置 DO
 *
 * @author 夏兆金
 */
@TableName("sns_app_config")
@KeySequence("sns_app_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsAppConfigDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 配置名
     */
    private String cfgName;
    /**
     * 配置值
     */
    private String cfgValue;
    /**
     * 说明
     */
    private String cfgRemark;

}