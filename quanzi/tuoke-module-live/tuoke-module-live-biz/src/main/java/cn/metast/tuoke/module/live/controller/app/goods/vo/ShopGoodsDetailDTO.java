package cn.metast.tuoke.module.live.controller.app.goods.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "com.kalacheng.shop.model.ShopGoodsDetailDTO", description = "商品详情返回")
public class ShopGoodsDetailDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主播id", name = "anchorId")
    public long anchorId;

    @ApiModelProperty(value = "商家名称", name = "businessName")
    public String businessName;

    @ApiModelProperty(value = "商家logo", name = "businessLogo")
    public String businessLogo;

    @ApiModelProperty(value = "在售商品数量", name = "effectiveGoodsNum")
    public int effectiveGoodsNum;

    @ApiModelProperty(value = "购物车数量", name = "effectiveGoodsNum")
    public int shopCarNum;

    @ApiModelProperty(value = "累计销量", name = "channelName")
    public int totalSoldNum;

    @ApiModelProperty(value = "商品信息", name = "shopGoods")
    public ShopGoods shopGoods;

    @ApiModelProperty(value = "商家商品列表", name = "shopGoods")
    public List<ShopGoodsDTO> shopGoodsDTOS;

    @ApiModelProperty(value = "商品对应属性", name = "shopGoods")
    public List<ShopGoodsAttrDTO> attrDTOList;

    @ApiModelProperty(value = "商品对应规格组合", name = "shopGoods")
    public List<ShopAttrCompose> composeList;

    @ApiModelProperty(value = "推荐商品列表", name = "shopGoods")
    public List<ShopGoodsDTO> recommendGoodsDTOS;

}
