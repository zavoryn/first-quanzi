package cn.metast.tuoke.module.live.dal.dataobject.snsComment;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评论 DO
 *
 * @author 夏兆金
 */
@TableName("sns_comment")
@KeySequence("sns_comment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsCommentDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * comment_id
     */
    private Long commentId;
    /**
     * 动态ID
     */
    private Long postId;
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
    /**
     * tail
     */
    private String tail;
    /**
     * type
     */
    private String type;

}