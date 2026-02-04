package cn.metast.tuoke.module.trade.controller.admin.order.vo;

import cn.metast.tuoke.framework.excel.core.annotations.DictFormat;
import cn.metast.tuoke.framework.excel.core.convert.DictConvert;
import cn.metast.tuoke.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "管理后台 - 订单信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TradeOrderRespVO {

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1146347329394184195")
    @ExcelProperty("订单号")
    private String no;

    @Schema(description = "下单时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("下单时间")
    private LocalDateTime createTime;

    @Schema(description = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @ExcelProperty("商品名称")
    private  String spuNameEx;

    @Schema(description = "购买数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("购买数量")
    private Integer countEx;

    @Schema(description = "商品原价（单）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @ExcelProperty("商品原价")
    private String priceEx;


    @Schema(description = "订单状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value="订单状态", converter = DictConvert.class)
    @DictFormat("TRADE_ORDER_STATUS")
    private Integer status;

    @Schema(description = "订单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value="订单类型", converter = DictConvert.class)
    @DictFormat("TRADE_ORDER_TYPE")
    private Integer type;

    @Schema(description = "支付渠道", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx_lite")
    @ExcelProperty(value="支付渠道", converter = DictConvert.class)
    @DictFormat("PAY_CHANNEL_CODE")
    private String payChannelCode;

    @Schema(description = "配送方式", example = "10")
    @ExcelProperty(value="配送方式", converter = DictConvert.class)
    @DictFormat("TRADE_DELIVERY_TYPE")
    private Integer deliveryType;

    @Schema(description = "用户备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @ExcelProperty("用户备注")
    private String userRemark;

    @Schema(description = "商家备注", example = "你猜一下")
    @ExcelProperty("商家备注")
    private String remark;

    @Schema(description = "发货物流单号", example = "1024")
    @ExcelProperty("发货物流单号")
    private String logisticsNo;

    @Schema(description = "买家", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @ExcelProperty("买家")
    private String nicknameEx;


    @Schema(description = "收货人", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("收货人")
    private String receiverName;

    @Schema(description = "收货人手机", requiredMode = Schema.RequiredMode.REQUIRED, example = "13800138000")
    @ExcelProperty("收货人手机")
    private String receiverMobile;

    @Schema(description = "收货人地区", requiredMode = Schema.RequiredMode.REQUIRED, example = "上海 上海市 普陀区")
    @ExcelProperty("收货人地区")
    private String receiverAreaName;

    @Schema(description = "收货人详细地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "中关村大街 1 号")
    @ExcelProperty("收货人详细地址")
    private String receiverDetailAddress;

}
