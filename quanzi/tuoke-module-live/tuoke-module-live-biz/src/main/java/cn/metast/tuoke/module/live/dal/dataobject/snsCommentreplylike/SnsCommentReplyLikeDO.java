package cn.metast.tuoke.module.live.dal.dataobject.snsCommentreplylike;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评论回复点赞人数 DO
 *
 * @author 夏兆金
 */
@TableName("sns_comment_reply_like")
@KeySequence("sns_comment_reply_like_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsCommentReplyLikeDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * reply_id
     */
    private Long replyId;
    /**
     * user_id
     */
    private Long userId;

}