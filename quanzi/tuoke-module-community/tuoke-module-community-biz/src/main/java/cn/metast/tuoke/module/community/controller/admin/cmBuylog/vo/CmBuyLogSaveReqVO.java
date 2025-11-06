package cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 会员购买记录新增/修改 Request VO")
@Data
public class CmBuyLogSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14700")
    private Long id;

    @Schema(description = "会员等级编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13549")
    @NotNull(message = "会员等级编号不能为空")
    private Long levelId;

    @Schema(description = "会员名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "会员名称不能为空")
    private String name;

    @Schema(description = "会员等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "会员等级不能为空")
    private Integer level;

    @Schema(description = "会员经验(价格)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "会员经验(价格)不能为空")
    private Integer experience;

    @Schema(description = "状态(0-待支付 1-购买成功)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态(0-待支付 1-购买成功)不能为空")
    private Integer status;

    @Schema(description = "支付订单编号", example = "1918")
    private Long payOrderId;

    @Schema(description = "是否已支付：[0:未支付 1:已经支付过]", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "是否已支付：[0:未支付 1:已经支付过]不能为空")
    private Boolean payStatus;

    @Schema(description = "订单支付时间")
    private LocalDateTime payTime;

    @Schema(description = "应付金额（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "29053")
    @NotNull(message = "应付金额（总），单位：分不能为空")
    private Integer payPrice;

    @Schema(description = "会员原价（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "17271")
    @NotNull(message = "会员原价（总），单位：分不能为空")
    private Integer totalPrice;

    @Schema(description = "已购买会员金额（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "21137")
    @NotNull(message = "已购买会员金额（总），单位：分不能为空")
    private Integer yzfPrice;

    @Schema(description = "一级返佣比例", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "一级返佣比例不能为空")
    private Integer brokerageFirstPercent;

    @Schema(description = "二级返佣比例", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "二级返佣比例不能为空")
    private Integer brokerageSecondPercent;

}
