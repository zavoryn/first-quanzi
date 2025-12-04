package cn.metast.tuoke.module.heal.dal.dataobject.healService;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 服务列 DO
 *
 * @author 苏丹家园
 */
@TableName("heal_service")
@KeySequence("heal_service_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealServiceDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String introduce;
    /**
     * 封面url
     */
    private String coverUrl;
    /**
     * 内容
     */
    private String content;
    /**
     * 网页链接
     */
    private String linkUrl;
    /**
     * 价格
     */
    private BigDecimal fee;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 分享个数
     */
    private Integer shareNum;
    /**
     * 点赞个数
     */
    private Integer likeNum;
    /**
     * 评论个数
     */
    private Integer commentNum;
    /**
     * 访问次数
     */
    private Integer visitNum;

}