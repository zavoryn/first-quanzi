package cn.metast.tuoke.module.community.dal.dataobject.cmMessage;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 圈子消息 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_message")
@KeySequence("cm_message_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmMessageDO extends BaseDO {

    /**
     * 消息ID
     */
    @TableId
    private Long id;
    /**
     * 发送者ID
     */
    private Long fromUserId;
    /**
     * 接收者ID
     */
    private Long toUserId;
    /**
     * 消息类型(0系统消息 1评论 2回复 3点赞 4关注 5私信)
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 关联帖子ID
     */
    private Long postId;
    /**
     * 关联评论ID
     */
    private Long commentId;
    /**
     * 是否已读(0未读 1已读)
     */
    private Integer isRead;

}