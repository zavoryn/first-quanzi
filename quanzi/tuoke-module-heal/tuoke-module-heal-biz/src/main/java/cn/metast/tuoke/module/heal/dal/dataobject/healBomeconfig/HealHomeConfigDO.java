package cn.metast.tuoke.module.heal.dal.dataobject.healBomeconfig;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 首页模块配置 DO
 *
 * @author 苏丹家园
 */
@TableName("heal_home_config")
@KeySequence("heal_home_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealHomeConfigDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 标题
     */
    private String name;
    /**
     * 图片地址
     */
    private String icon;
    /**
     * 链接地址
     */
    private String linkUrl;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 上下线 1（true）上线， 0（false）下线
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 参数
     */
    private String param;

}