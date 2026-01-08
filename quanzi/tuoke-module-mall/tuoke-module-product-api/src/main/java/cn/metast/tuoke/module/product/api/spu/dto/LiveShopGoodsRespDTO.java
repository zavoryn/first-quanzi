package cn.metast.tuoke.module.product.api.spu.dto;

import cn.metast.tuoke.module.product.enums.spu.ProductSpuStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * 商品 SPU 信息 Response DTO
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
@Data
public class LiveShopGoodsRespDTO {

    /**
     * 编号，自增
     */
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
