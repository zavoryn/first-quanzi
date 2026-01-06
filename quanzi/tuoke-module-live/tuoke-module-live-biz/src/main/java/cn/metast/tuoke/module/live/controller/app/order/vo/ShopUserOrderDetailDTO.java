package cn.metast.tuoke.module.live.controller.app.order.vo;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "com.kalacheng.shop.model.ShopUserOrderDetailDTO", description = "订单详情返回")
public class ShopUserOrderDetailDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主播id", name = "anchorId")
    public long anchorId;

    @ApiModelProperty(value = "父订单信息", name = "shopAddress")
    public ShopParentOrder parentOrder;

    // @ApiModelProperty(value = "买家信息", name = "buyUser")
    // public AppUser buyUser;

    @ApiModelProperty(value = "买家ID")
    public long buyUserId;

    @ApiModelProperty(value = "买家名称")
    public String buyUserName;

    @ApiModelProperty(value = "买家头像")
    public String buyUserAvatar;

    @ApiModelProperty(value = "商家订单信息", name = "businessOrder")
    public ShopBusinessOrder businessOrder;

    @ApiModelProperty(value = "对应的商品信息", name = "subOrderList")
    public List<ShopSubOrder> subOrderList;

    @ApiModelProperty(value = "商品总数量", name = "goodsNum")
    public int goodsNum;

//    @ApiModelProperty(value = "退款流程集合", name = "processList")
//    public List<ShopOrderReturnProcessDTO> processList;

    @ApiModelProperty(value = "截至时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date closingDate;

    @ApiModelProperty(value = "物流名称", name = "logisticsName")
    public String logisticsName;

    @ApiModelProperty(value = "买家发货物流单号")
    public String logisticsNum;

    @ApiModelProperty(value = "买家发货物流信息")
    public String buyerLogisticsContent;

    @ApiModelProperty(value = "买家发货物流时间点")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date buyerLogisticsTime;

    @ApiModelProperty(value = "退货物流名称", name = "refundLogisticsName")
    public String refundLogisticsName;

    @ApiModelProperty(value = "退货物流单号")
    public String refundLogisticsNum;

    @ApiModelProperty(value = "卖家发货物流信息")
    public String sellerLogisticsContent;

    @ApiModelProperty(value = "卖家发货物流时间点")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date sellerLogisticsTime;

    @ApiModelProperty(value = "订单显示的天数")
    public String refundShowDay;
}
