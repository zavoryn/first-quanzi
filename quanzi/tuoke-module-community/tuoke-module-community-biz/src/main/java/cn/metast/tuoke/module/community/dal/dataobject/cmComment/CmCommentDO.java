package cn.metast.tuoke.module.community.dal.dataobject.cmComment;

import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 圈子评论 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_comment")
@KeySequence("cm_comment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmCommentDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 评论类型
     */
    private Integer type;
    /**
     * 评论作者ID
     */
    private Long userId;
    /**
     * 被回复用户ID
     */
    private Long toUserId;
    /**
     * 评论帖子ID
     */
    private Long postId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 状态
     */
    private Integer status;

    //评论点赞数
    private Integer likeCount;

    //用户昵称
    @TableField(exist = false)
    private String nickname;
    //用户头像
    @TableField(exist = false)
    private String avatar;
    //圈子名称
    @TableField(exist = false)
    private String topicName;
    /** 回复列表 */
    @TableField(exist = false)
    private List<CmCommentDO> replies;
    /** 回复人 */
    @TableField(exist = false)
    private String replyTo;
    /** 帖子 */
    @TableField(exist = false)
    private CmPostDO post;

}
