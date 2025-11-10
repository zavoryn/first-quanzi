package cn.metast.tuoke.module.community.dal.dataobject.cmFollow;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户关注中间 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_follow")
@KeySequence("cm_follow_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmFollowDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 关注的用户id
     */
    private Long followUserId;
    /**
     * 状态(0取消,1关注)
     */
    private Boolean state;

}