package cn.metast.tuoke.module.live.dal.dataobject.snsActschedule;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动日程 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act_schedule")
@KeySequence("sns_act_schedule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActScheduleDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * act_id
     */
    private Long actId;
    /**
     * item_name
     */
    private String itemName;
    /**
     * sub_name
     */
    private String subName;
    /**
     * sub_stime
     */
    private LocalDateTime subStime;
    /**
     * sub_etime
     */
    private LocalDateTime subEtime;
    /**
     * sub_context
     */
    private String subContext;
    /**
     * parent_id
     */
    private Long parentId;

}