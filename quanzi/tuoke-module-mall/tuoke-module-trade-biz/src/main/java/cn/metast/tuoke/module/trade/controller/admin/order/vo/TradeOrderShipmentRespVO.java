package cn.metast.tuoke.module.trade.controller.admin.order.vo;

import cn.metast.tuoke.framework.excel.core.annotations.DictFormat;
import cn.metast.tuoke.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 订单信息 Response VO")
@Data
@ExcelIgnoreUnannotated
@Accessors(chain = false) // 设置 chain = false，避免导入有问题
public class TradeOrderShipmentRespVO {

//    @ExcelProperty("系统编号")
//    private Long id;

    @ExcelProperty("订单号")
    private String no;

    @ExcelProperty("下单时间")
    private LocalDateTime createTime;

    @ExcelProperty("商品名称")
    private  String spuNameEx;

    @ExcelProperty("购买数量")
    private Integer countEx;

    @ExcelProperty("商品原价")
    private String priceEx;

    @ExcelProperty("收货人")
    private String receiverName;

    @ExcelProperty("收货人手机")
    private String receiverMobile;

    @ExcelProperty("收货人地区")
    private String receiverAreaName;

    @ExcelProperty("收货人详细地址")
    private String receiverDetailAddress;

    @ExcelProperty("发货物流单号")
    private String logisticsNo;

}
