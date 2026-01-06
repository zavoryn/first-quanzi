package cn.metast.tuoke.module.live.dal.dataobject.snsCommentlike;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评论点赞人 DO
 *
 * @author 夏兆金
 */
@TableName("sns_comment_like")
@KeySequence("sns_comment_like_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsCommentLikeDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * post_id
     */
    private Long postId;
    /**
     * comment_id
     */
    private Long commentId;
    /**
     * user_id
     */
    private Long userId;

}