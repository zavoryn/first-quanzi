package cn.metast.tuoke.module.community.dal.dataobject.cmConfig;

import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 圈子配置 DO
 *
 * @author adminXq
 */
@TableName("cm_config")
@KeySequence("cm_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmConfigDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 圈子id
     */
    private Long topicId;

}