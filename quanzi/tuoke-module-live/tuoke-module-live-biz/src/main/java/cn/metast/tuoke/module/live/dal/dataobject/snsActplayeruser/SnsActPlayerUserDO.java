package cn.metast.tuoke.module.live.dal.dataobject.snsActplayeruser;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 参与人信息 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act_player_user")
@KeySequence("sns_act_player_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActPlayerUserDO extends BaseDO {

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
     * 报名用户ID
     */
    private Long userId;
    /**
     * 参与人员信息
     */
    private String fieldValue;
    /**
     * 报名人员id，区分多个报名人员
     */
    private Long actUserId;
    @TableField(exist = false)
    private String title;

}
