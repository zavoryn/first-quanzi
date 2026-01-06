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
@ApiModel(value = "com.kalacheng.shop.entity.ShopParentOrder", description = "父商品订单表")
public class ShopParentOrder implements Serializable {
    public static final long serialVersionUID = 1L;

    public long id;

    @ApiModelProperty(value = "用户id", name = "uid")
    public long uid;

    @ApiModelProperty(value = "订单号", name = "orderNum")
    public String orderNum;

    @ApiModelProperty(value = "支付交易号(支付宝或者微信返回)", name = "payOrder")
    public String payOrder;

    @ApiModelProperty(value = "订单金额", name = "amount")
    public double amount;

    @ApiModelProperty(value = "实付金额", name = "amount")
    public double nhrAmount;

    @ApiModelProperty(value = "支付渠道 1.支付宝 2.微信  ", name = "channelId")
    public long channelId;

    @ApiModelProperty(value = "订单状态 1.待付款 2.付款成功 3.付款失败 ", name = "status")
    public int status;

    @ApiModelProperty(value = "下单时间", name = "addTime")
    public String addTime;

    @ApiModelProperty(value = "支付时间", name = "addTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public String payTime;

    @ApiModelProperty(value = "备注", name = "remake")
    public String remake;

}
