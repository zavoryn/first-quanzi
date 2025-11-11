package cn.metast.tuoke.module.community.dal.dataobject.cmPostlike;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 帖子点赞 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_post_like")
@KeySequence("cm_post_like_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmPostLikeDO extends BaseDO {

    /**
     * 帖子点赞编号
     */
    @TableId
    private Integer id;
    /**
     * 帖子id
     */
    private Long postId;
    /**
     * 评论点赞用户id
     */
    private Long userId;
    /**
     * 状态(0取消,1点赞)
     */
    private Boolean state;

}