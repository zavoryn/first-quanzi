package cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 会员购买记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmBuyLogRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14700")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "会员等级编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13549")
    @ExcelProperty("会员等级编号")
    private Long levelId;

    @Schema(description = "会员名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("会员名称")
    private String name;

    @Schema(description = "会员等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("会员等级")
    private Integer level;

    @Schema(description = "会员经验(价格)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("会员经验(价格)")
    private Integer experience;

    @Schema(description = "状态(0-待支付 1-购买成功)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态(0-待支付 1-购买成功)")
    private Integer status;

    @Schema(description = "支付订单编号", example = "1918")
    @ExcelProperty("支付订单编号")
    private Long payOrderId;

    @Schema(description = "是否已支付：[0:未支付 1:已经支付过]", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("是否已支付：[0:未支付 1:已经支付过]")
    private Boolean payStatus;

    @Schema(description = "订单支付时间")
    @ExcelProperty("订单支付时间")
    private LocalDateTime payTime;

    @Schema(description = "应付金额（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "29053")
    @ExcelProperty("应付金额（总），单位：分")
    private Integer payPrice;

    @Schema(description = "会员原价（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "17271")
    @ExcelProperty("会员原价（总），单位：分")
    private Integer totalPrice;

    @Schema(description = "已购买会员金额（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "21137")
    @ExcelProperty("已购买会员金额（总），单位：分")
    private Integer yzfPrice;

    @Schema(description = "一级返佣比例", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("一级返佣比例")
    private Integer brokerageFirstPercent;

    @Schema(description = "二级返佣比例", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("二级返佣比例")
    private Integer brokerageSecondPercent;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    /**
     * 退款金额
     */
    private Integer refundPrice;
    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    private String nickname;
    private String mobile;
    private String avatar;
    private String topicName;

}
