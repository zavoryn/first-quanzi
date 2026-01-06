package cn.metast.tuoke.module.live.controller.app.order.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@JsonIgnoreProperties
@ApiModel(value = "com.kalacheng.shop.entity.ShopSubOrder", description = "子商品订单表")
public class ShopSubOrder implements Serializable {
    public static final long serialVersionUID = 1L;

    public long id;

    @ApiModelProperty(value = "父订单(支付订单)id", name = "payId")
    public long payId;

    @ApiModelProperty(value = "商家id")
    public long businessId;

    @ApiModelProperty(value = "商家订单id", name = "businessOrderId")
    public long businessOrderId;

    @ApiModelProperty(value = "订单号", name = "orderNum")
    public String orderNum;

    @ApiModelProperty(value = "商品id", name = "userName")
    public long goodsId;

    @ApiModelProperty(value = "商品名称", name = "userName")
    public String goodsName;

    @ApiModelProperty(value = "商品价格", name = "userName")
    public double goodsPrice;

    @ApiModelProperty(value = "商品简介图片地址", name = "goodsPicture")
    public String goodsPicture;

    @ApiModelProperty(value = "订单状态 1：待付款 2：待发货 3：待收货 4：已完成 5：已取消", name = "status")
    public int status;

    @ApiModelProperty(value = "商品数量", name = "phoneNum")
    public int goodsNum;

    @ApiModelProperty(value = "skuId", name = "composeId")
    public long composeId;

    @ApiModelProperty(value = "sku组合名称", name = "skuName")
    public String skuName;

    @ApiModelProperty(value = "物流名称", name = "logisticsName")
    public String logisticsName;

    @ApiModelProperty(value = "物流单号", name = "logisticsNum")
    public String logisticsNum;

    @ApiModelProperty(value = "添加时间", name = "addTime")
    public String addTime;

    @ApiModelProperty(value = "备注", name = "remake")
    public String remake;
}
