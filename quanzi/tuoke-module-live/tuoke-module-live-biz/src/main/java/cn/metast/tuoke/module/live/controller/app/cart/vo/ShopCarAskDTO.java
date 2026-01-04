package cn.metast.tuoke.module.live.controller.app.cart.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "com.kalacheng.shop.model.ShopCarAskDTO", description = "购物车生成订单请求")
public class ShopCarAskDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id", name = "goodsId")
    public long goodsId;

    @ApiModelProperty(value = "商品skuId", name = "skuId")
    public long skuId;

    @ApiModelProperty(value = "商品数量", name = "goodsNum")
    public int goodsNum;

    @ApiModelProperty(value = "购物车id", name = "carId")
    public long carId;

}
