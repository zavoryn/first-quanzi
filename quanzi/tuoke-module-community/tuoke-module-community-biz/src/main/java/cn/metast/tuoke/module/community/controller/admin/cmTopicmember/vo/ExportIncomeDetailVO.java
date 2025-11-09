package cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 收入明细导出 VO")
@Data
@ColumnWidth(25) // 设置默认列宽
public class ExportIncomeDetailVO {

    @ExcelProperty("操作时间")
    @ColumnWidth(20)
    private String startTime;

    @ExcelProperty("购买次数")
    @ColumnWidth(15)
    private Integer orderNum;

    @ExcelProperty("微信昵称")
    @ColumnWidth(25)
    private String nickname;

    @ExcelProperty("收入金额")
    @ColumnWidth(15)
    private Integer price;

    @ExcelProperty("订单编号")
    @ColumnWidth(15)
    private String no;

    @ExcelProperty("付款途径")
    @ColumnWidth(15)
    private String channelCode;

    @ExcelProperty("产品名称")
    @ColumnWidth(15)
    private String name;

    @ExcelProperty("产品期限")
    @ColumnWidth(15)
    private String endTime;

}
