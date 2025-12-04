package cn.metast.tuoke.module.heal.dal.dataobject.healServiceconfig;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 服务配置 DO
 *
 * @author 苏丹家园
 */
@TableName("heal_service_config")
@KeySequence("heal_service_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealServiceConfigDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 参数
     */
    private String param;

}