package cn.metast.tuoke.module.product.dal.dataobject.livegoods;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 直播商品列 DO
 *
 * @author admin
 */
@TableName("shop_live_goods")
@KeySequence("shop_live_goods_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsDO extends BaseDO {

    /**
     * 主键ID自增
     */
    @TableId
    private Long id;
    /**
     *  主播Id
     */
    private Long anchorId;
    /**
     *  商品渠道id
     */
    private Long channelId;
    /**
     * 商品价格
     */
    private Double favorablePrice;
    /**
     *  商品id
     */
    private Long goodsId;
    /**
     *  商品图片地址
     */
    private String goodsPicture;
    /**
     *  商品价格
     */
    private Double goodsPrice;
    /**
     *  是否讲解
     */
    private Integer ifExplain;
    /**
     *  商品名称
     */
    private String name;
    /**
     * 商品链接
     */
    private String productLinks;
    /**
     *  排序
     */
    private Integer sort;

}
