package cn.metast.tuoke.module.community.dal.dataobject.cmCollect;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 收藏记录 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_collect")
@KeySequence("cm_collect_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmCollectDO extends BaseDO {

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
     * 收藏的帖子id
     */
    private Long postId;
    /**
     * 状态(0取消,1收藏)
     */
    private Boolean state;

}