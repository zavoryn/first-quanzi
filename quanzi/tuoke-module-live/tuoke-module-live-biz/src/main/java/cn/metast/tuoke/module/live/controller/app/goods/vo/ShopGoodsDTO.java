package cn.metast.tuoke.module.live.controller.app.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "com.kalacheng.shop.model.ShopGoodsDTO", description = "商品列表返回")
public class ShopGoodsDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id", name = "businessId")
    public long goodsId;

    @ApiModelProperty(value = "商品渠道id", name = "channelId")
    public long channelId;

    @ApiModelProperty(value = "商品渠道名称", name = "channelName")
    public String channelName;

    @ApiModelProperty(value = "商品分类名称", name = "categoryName")
    public String categoryName;

    @ApiModelProperty(value = "商品分类Id", name = "categoryId")
    public long categoryId;

    @ApiModelProperty(value = "商品名称", name = "goodsName")
    public String goodsName;

    @ApiModelProperty(value = "商品详情", name = "present")
    public String present;

    @ApiModelProperty(value = "商品管理排序", name = "sort")
    public int sort;

    @ApiModelProperty(value = "直播商品排序", name = "sort")
    public int liveSort;

    @ApiModelProperty(value = "状态", name = "status")
    public int status;

    @ApiModelProperty(value = "商品简介图片地址", name = "goodsPicture")
    public String goodsPicture;

    @ApiModelProperty(value = "商品详情图片地址", name = "detailPicture")
    public String detailPicture;

//    @ApiModelProperty(value = "商品编号", name = "num")
//    public String num;

    @ApiModelProperty(value = "商品链接", name = "productLinks")
    public String productLinks;

    @ApiModelProperty(value = "商品价格", name = "price")
    public double price;

    @ApiModelProperty(value = "商品已售数量", name = "soldNum")
    public int soldNum;

    @ApiModelProperty(value = "商品优惠价格", name = "favorablePrice")
    public double favorablePrice;

    @ApiModelProperty(value = "是否选取 0 没有选取 1 选取", name = "checked")
    public int checked;

    @ApiModelProperty(value = "属性名称", name = "attrName")
    public String attrName;
}
