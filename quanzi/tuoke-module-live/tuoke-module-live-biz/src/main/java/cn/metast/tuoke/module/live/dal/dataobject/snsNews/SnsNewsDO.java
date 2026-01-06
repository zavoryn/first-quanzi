package cn.metast.tuoke.module.live.dal.dataobject.snsNews;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 新闻信息 DO
 *
 * @author 夏兆金
 */
@TableName("sns_news")
@KeySequence("sns_news_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnsNewsDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * news_id
     */
    private Long newsId;
    /**
     * post_id
     */
    private Long postId;
    /**
     * type_seting
     */
    private Long typeSeting;
    /**
     * 新闻标识
     */
    private Long newsConfig;
    /**
     * 0上线1下线
     */
    private String isFlag;

}
