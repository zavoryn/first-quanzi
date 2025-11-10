package cn.metast.tuoke.module.community.dal.dataobject.cmLink;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 首页轮播图 DO
 *
 * @author 苏丹家园
 */
@TableName("cm_link")
@KeySequence("cm_link_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmLinkDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 跳转路径
     */
    private String url;
    /**
     * 图片
     */
    private String img;
    /**
     * 广场轮播图
     */
    private Integer type;

}