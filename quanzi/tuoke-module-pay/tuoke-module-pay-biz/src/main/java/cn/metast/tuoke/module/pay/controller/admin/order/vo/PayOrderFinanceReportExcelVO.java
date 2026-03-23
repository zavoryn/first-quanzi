package cn.metast.tuoke.module.pay.controller.admin.order.vo;

import cn.metast.tuoke.framework.excel.core.convert.MoneyConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 财务报表导出 Excel VO
 *
 * @author metast.cn
 */
@Data
public class PayOrderFinanceReportExcelVO {

    @ExcelProperty("到款日期")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @ExcelProperty("客户姓名")
    @ColumnWidth(15)
    private String customerName;

    @ExcelProperty(value = "合同金额", converter = MoneyConvert.class)
    @ColumnWidth(12)
    private Integer contractAmount;

    @ExcelProperty("交易单号")
    @ColumnWidth(30)
    private String channelOrderNo;

    @ExcelProperty("商户单号")
    @ColumnWidth(30)
    private String merchantOrderNo;

    @ExcelProperty("合同开始日期")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime contractStartDate;

    @ExcelProperty("合同结束日期")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime contractEndDate;

    @ExcelProperty("是否续约客户")
    @ColumnWidth(12)
    private String isRenewal;

    @ExcelProperty("软件版本")
    @ColumnWidth(20)
    private String softwareVersion;

    @ExcelProperty("支付状态")
    @ColumnWidth(20)
    private String payStatus;

    @ExcelProperty("支付渠道")
    @ColumnWidth(20)
    private String channelCode;

    @ExcelProperty("是否退款")
    @ColumnWidth(10)
    private String isRefund;

    @ExcelProperty(value = "退款金额", converter = MoneyConvert.class)
    @ColumnWidth(12)
    private Integer refundAmount;

    @ExcelProperty("退款时间")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundTime;
}
