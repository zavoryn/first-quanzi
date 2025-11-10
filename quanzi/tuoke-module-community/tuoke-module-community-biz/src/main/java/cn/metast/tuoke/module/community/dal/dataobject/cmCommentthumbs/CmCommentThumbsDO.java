package cn.metast.tuoke.module.community.dal.dataobject.cmCommentthumbs;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 评论用户关联 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_comment_thumbs")
@KeySequence("cm_comment_thumbs_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmCommentThumbsDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 评论id
     */
    private Long commentId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 状态
     */
    private Integer status;

}