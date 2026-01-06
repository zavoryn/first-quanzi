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
@ApiModel(value = "com.kalacheng.shop.entity.ShopBusinessOrder", description = "商家订单表")
public class ShopBusinessOrder implements Serializable {
    public static final long serialVersionUID = 1L;

    public long id;

    @ApiModelProperty(value = "父订单id", name = "payId")
    public long payId;

    @ApiModelProperty(value = "用户id", name = "uid")
    public long uid;

    @ApiModelProperty(value = "主播id", name = "anchorId")
    public long anchorId;

    @ApiModelProperty(value = "商家id", name = "userName")
    public long businessId;

    @ApiModelProperty(value = "商家logo", name = "businessLogo")
    public String businessLogo;

    @ApiModelProperty(value = "商家名称", name = "businessName")
    public String businessName;

    @ApiModelProperty(value = "订单号", name = "orderNum")
    public String orderNum;

    @ApiModelProperty(value = "用户支付订单号（平台的）")
    public String platformPayOrder;

    @ApiModelProperty(value = "交易金额(实付金额)", name = "transactionAmount")
    public double transactionAmount;

    @ApiModelProperty(value = "订单金额", name = "transactionAmount")
    public double orderAmount;

    @ApiModelProperty(value = "订单状态 1:待付款 2:待发货 3:待收货 4:订单完成 5:已取消", name = "status")
    public int status;

    @ApiModelProperty(value = "退货订单状态 1:待审核 2:待发货 3:待收货 4:退款中 5:退款完成 6:退款失败 7:审核拒绝", name = "quitStatus")
    public int quitStatus;

    @ApiModelProperty(value = "添加时间", name = "addTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public String addTime;

    @ApiModelProperty(value = "交易成功时间", name = "addTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public String transactionTime;

    @ApiModelProperty(value = "收货人地址id", name = "addressId")
    public long addressId;

    @ApiModelProperty(value = "收货人地址", name = "address")
    public String address;

    @ApiModelProperty(value = "收货人姓名", name = "name")
    public String name;

    @ApiModelProperty(value = "收货人手机号", name = "phoneNum")
    public String phoneNum;

    @ApiModelProperty(value = "发货物流id", name = "logisticsNum")
    public long logisticsId;

    @ApiModelProperty(value = "备注", name = "addTime")
    public String remake;

    @ApiModelProperty(value = "退货物流id", name = "logisticsNum")
    public long refundLogisticsId;

    @ApiModelProperty(value = "申请退款原因", name = "reason")
    public String reason;

    @ApiModelProperty(value = "申请退款备注", name = "addTime")
    public String refundNotes;

    @ApiModelProperty(value = "退款备注图片", name = "addTime")
    public String refundNotesImages;

    @ApiModelProperty(value = "申请退款审核不通过原因", name = "reason")
    public String auditFailureReason;

    @ApiModelProperty(value = "任务id", name = "taskId")
    public long taskId;

    @ApiModelProperty(value = "退款类型 1.仅退款 2.退货退款")
    public int refundType;

    @ApiModelProperty(value = "订单来源id", name = "goodsChannelId")
    public long goodsChannelId;

    @ApiModelProperty(value = "退款完成时间", name = "refundTime")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date refundTime;

    @ApiModelProperty(value = "人工退款金额", name = "refundTime")
    public double manualRefundMoney;

    @ApiModelProperty(value = "人工退款方式 1:支付宝 2:微信 3:人工转账", name = "refundTime")
    public int manualRefundType;

    @ApiModelProperty(value = "退款订单号")
    public String refundOrderNum;

    @ApiModelProperty(value = "是否人工退款 1：是 2：否")
    public int isManualRefund;

    @ApiModelProperty(value = "人工退款操作人")
    public String manualRefundOperator;
}
