package cn.metast.tuoke.module.live.controller.app.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties
@ApiModel(value = "com.kalacheng.shop.entity.ShopLiveGoods", description = "直播商品列表")
public class ShopLiveGoods implements Serializable {
    public static final long serialVersionUID = 1L;

    public long id;

    @ApiModelProperty(value = "主播Id", name = "anchorId")
    public long anchorId;

    @ApiModelProperty(value = "商品id", name = "goodsId")
    public long goodsId;

    @ApiModelProperty(value = "商品名称", name = "name")
    public String name;

    @ApiModelProperty(value = "商品图片地址", name = "goodsPicture")
    public String goodsPicture;

    @ApiModelProperty(value = "商品价格", name = "goodsPrice")
    public double goodsPrice;

    @ApiModelProperty(value = "商品渠道id", name = "channelId")
    public long channelId;

    @ApiModelProperty(value = "商品优惠价格", name = "favorablePrice")
    public double favorablePrice;

    @ApiModelProperty(value = "商品链接", name = "productLinks")
    public String productLinks;

    @ApiModelProperty(value = "是否讲解", name = "idExplain")
    public int idExplain;

    @ApiModelProperty(value = "排序", name = "sort")
    public int sort;
}
