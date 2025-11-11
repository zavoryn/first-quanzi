package cn.metast.tuoke.module.community.dal.dataobject.cmPostcollection;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户帖子中间 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_post_collection")
@KeySequence("cm_post_collection_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmPostCollectionDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 帖子id
     */
    private Long postId;

}