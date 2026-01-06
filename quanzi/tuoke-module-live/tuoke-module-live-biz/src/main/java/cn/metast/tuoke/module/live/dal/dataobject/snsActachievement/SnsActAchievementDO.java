package cn.metast.tuoke.module.live.dal.dataobject.snsActachievement;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动-成绩 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act_achievement")
@KeySequence("sns_act_achievement_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActAchievementDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片
     */
    private String logo;
    /**
     * 分数
     */
    private Integer fraction;
    /**
     * 对手名称
     */
    private String toname;
    /**
     * 对手图片
     */
    private String tologo;
    /**
     * 对手分数
     */
    private Integer tofraction;
    /**
     * 创建人
     */
    private Integer createUserId;

}