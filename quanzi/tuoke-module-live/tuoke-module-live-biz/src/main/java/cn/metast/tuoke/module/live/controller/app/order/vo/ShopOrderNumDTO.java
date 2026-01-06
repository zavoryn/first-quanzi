package cn.metast.tuoke.module.live.controller.app.order.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "com.kalacheng.libuser.model.ShopOrderNumDTO", description = "订单数量信息")
public class ShopOrderNumDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "待付款", name = "toBePayNum")
    public int toBePayNum;

    @ApiModelProperty(value = "待发货", name = "toBeDeliveredNum")
    public int toBeDeliveredNum;

    @ApiModelProperty(value = "待收货", name = "toBeReceivedNum")
    public int toBeReceivedNum;

    @ApiModelProperty(value = "退货", name = "cancelGoodsNum")
    public int cancelGoodsNum;

    @ApiModelProperty(value = "已完成", name = "finishedNum")
    public int finishedNum;
}
