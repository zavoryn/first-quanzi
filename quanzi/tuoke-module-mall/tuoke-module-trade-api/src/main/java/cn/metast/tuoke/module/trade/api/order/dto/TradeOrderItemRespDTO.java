package cn.metast.tuoke.module.trade.api.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class TradeOrderItemRespDTO {

    private Long id;

    private Long orderId;

    private Long spuId;
    private String spuName;

    private Long skuId;

    /**
     * 属性数组
     */
    private List<ProductPropertyValueDetailRespDTO> properties;

    private String picUrl;

    private Integer count;

    private Boolean commentStatus;

    // ========== 价格 + 支付基本信息 ==========

    private Integer price;

    private Integer payPrice;

    // ========== 营销基本信息 ==========

    // TODO 芋艿：在捉摸一下

    // ========== 售后基本信息 ==========

    private Long afterSaleId;

    private Integer afterSaleStatus;

}
