package cn.metast.tuoke.module.live.controller.app.order.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "com.kalacheng.shop.model.ShopUserOrderDTO", description = "用户(主播)订单列表返回")
public class ShopUserOrderDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主播id", name = "anchorId")
    public long anchorId;

    @ApiModelProperty(value = "买家信息", name = "buyUser")
    public AppUser buyUser;

    @ApiModelProperty(value = "商家订单信息", name = "businessOrder")
    public ShopBusinessOrder businessOrder;

    @ApiModelProperty(value = "对应的商品信息", name = "subOrderList")
    public List<ShopSubOrder> subOrderList;

    @ApiModelProperty(value = "商品总数量", name = "goodsNum")
    public int goodsNum;

    @ApiModelProperty(value = "买家发货物流单号")
    public String logisticsNum;

    @ApiModelProperty(value = "退货物流单号")
    public String refundLogisticsNum;

}
