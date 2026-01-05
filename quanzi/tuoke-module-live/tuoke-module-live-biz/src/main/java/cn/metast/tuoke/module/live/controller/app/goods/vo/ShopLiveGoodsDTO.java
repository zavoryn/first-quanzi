package cn.metast.tuoke.module.live.controller.app.goods.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "com.kalacheng.shop.model.ShopLiveGoodsDTO", description = "直播间商品列表返回")
public class ShopLiveGoodsDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商家id", name = "businessId")
    public long businessId;

    @ApiModelProperty(value = "商家名称", name = "businessName")
    public String businessName;

    @ApiModelProperty(value = "商家logo", name = "businessLogo")
    public String businessLogo;

    @ApiModelProperty(value = "直播商品列表", name = "liveGoodsList")
    public List<ShopLiveGoods> liveGoodsList;

}
