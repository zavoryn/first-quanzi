package cn.metast.tuoke.module.live.dal.dataobject.snsActinfouser;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 报名填写用户信息 DO
 *
 * @author 夏兆金
 */
@TableName("sns_act_info_user")
@KeySequence("sns_act_info_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsActInfoUserDO extends BaseDO {

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
     * 字段名称
     */
    private String fieldName;
    /**
     * 选择的时候，选项 ，多个空格分隔
     */
    private String fieldValue;
    /**
     * 报名id，区分多个报名人员
     */
    private Long actUserId;

}