package cn.metast.tuoke.module.community.dal.dataobject.cmCommentlike;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评论点赞 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_comment_like")
@KeySequence("cm_comment_like_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmCommentLikeDO extends BaseDO {

    /**
     * 评论点赞编号
     */
    @TableId
    private Integer id;
    /**
     * 评论id
     */
    private Long commentId;
    /**
     * 评论点赞用户id
     */
    private Long userId;
    /**
     * 状态(0取消,1点赞)
     */
    private Boolean state;

}