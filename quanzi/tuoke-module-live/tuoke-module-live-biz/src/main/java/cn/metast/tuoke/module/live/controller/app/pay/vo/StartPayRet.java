package cn.metast.tuoke.module.live.controller.app.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "com.kalacheng.busfinance.modelvo.StartPayRet", description = "支付返回给前端的信息")
public class StartPayRet {

    @ApiModelProperty(value = "订单ID", name = "orderId")
    public String orderId;

    @ApiModelProperty(value = "支付宝支付信息", name = "aliPayInfo")
    public String aliPayInfo;

    @ApiModelProperty(value = "扫码URL")
    public String scanUrl=null;

    @ApiModelProperty(value = "微信支付信息", name = "WXPayInfo")
    public String WXPayInfo;

    @ApiModelProperty(value = "支付URL", name = "url")
    public String url;

    @ApiModelProperty(value = "appid", name = "appid")
    public String appid;

    @ApiModelProperty(value = "小程序原始ID", name = "originalId")
    public String originalId;

    @ApiModelProperty(value = "ios支付id", name = "originalId")
    public String iosPayId;
}
