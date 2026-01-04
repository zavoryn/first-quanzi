package cn.metast.tuoke.module.live.controller.app.goods.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties
@ApiModel(value = "com.kalacheng.shop.entity.ShopGoods", description = "商品信息表")
public class ShopGoods implements Serializable {
    public static final long serialVersionUID = 1L;

    public long id;

    @ApiModelProperty(value = "商家id", name = "businessId")
    public long businessId;

    @ApiModelProperty(value = "主播Id", name = "anchorId")
    public long anchorId;

    @ApiModelProperty(value = "商品渠道id", name = "channelId")
    public long channelId;

    @ApiModelProperty(value = "商品分类Id", name = "categoryId")
    public long categoryId;

    @ApiModelProperty(value = "商品名称", name = "goodsName")
    public String goodsName;

    @ApiModelProperty(value = "商品详情", name = "present")
    public String present;

    @ApiModelProperty(value = "排序", name = "sort")
    public int sort;

    @ApiModelProperty(value = "商品简介图片地址", name = "goodsPicture")
    public String goodsPicture;

    @ApiModelProperty(value = "商品详情图片地址", name = "detailPicture")
    public String detailPicture;

    @ApiModelProperty(value = "商品编号", name = "num")
    public String num;

    @ApiModelProperty(value = "商品链接", name = "productLinks")
    public String productLinks;

    @ApiModelProperty(value = "状态 1：未上架; 2:上架中; 3:冻结中")
    public int status;

    @ApiModelProperty(value = "商品价格", name = "price")
    public double price;

    @ApiModelProperty(value = "商品优惠价格", name = "favorablePrice")
    public double favorablePrice;

    @ApiModelProperty(value = "创建时间", name = "addTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date addTime;

    @ApiModelProperty(value = "修改时间", name = "upTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date upTime;

    @ApiModelProperty(value = "是否自营 1:自营 2:非自营")
    public int myGoods;

    @ApiModelProperty(value = "商品已售数量")
    public int soldNum;

    @ApiModelProperty(value = "审核备注")
    public String remake;

}
