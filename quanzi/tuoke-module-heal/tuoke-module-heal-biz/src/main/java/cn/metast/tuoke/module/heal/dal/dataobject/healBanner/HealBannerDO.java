package cn.metast.tuoke.module.heal.dal.dataobject.healBanner;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 首页banner DO
 *
 * @author 苏丹家园
 */
@TableName("heal_banner")
@KeySequence("heal_banner_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealBannerDO extends BaseDO {

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
     * 图片地址
     */
    private String imageUrl;
    /**
     * 链接地址
     */
    private String linkUrl;
    /**
     * 上下线 1（true）上线， 0（false）下线
     */
    private String status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 0 : banner    1 : notice
     */
    private Integer type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 网页类型
     */
    private String subject;
    /**
     * 参数
     */
    private String param;

}