package cn.metast.tuoke.module.live.dal.dataobject.snsCommentreply;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评论回复 DO
 *
 * @author 夏兆金
 */
@TableName("sns_comment_reply")
@KeySequence("sns_comment_reply_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsCommentReplyDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * ID
     */
    private Long replyId;
    /**
     * 评论ID
     */
    private Long commentId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞个数
     */
    private Integer likeNum;

}