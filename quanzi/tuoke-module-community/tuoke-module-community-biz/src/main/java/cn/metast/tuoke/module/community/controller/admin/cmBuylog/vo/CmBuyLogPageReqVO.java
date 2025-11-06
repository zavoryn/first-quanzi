package cn.metast.tuoke.module.community.controller.admin.cmBuylog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 会员购买记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmBuyLogPageReqVO extends PageParam {

    @Schema(description = "会员等级编号", example = "13549")
    private Long levelId;

    @Schema(description = "会员名称", example = "李四")
    private String name;

    @Schema(description = "会员等级")
    private Integer level;

    @Schema(description = "会员经验(价格)")
    private Integer experience;

    @Schema(description = "状态(0-待支付 1-购买成功)", example = "1")
    private Integer status;

    @Schema(description = "支付订单编号", example = "1918")
    private Long payOrderId;

    @Schema(description = "是否已支付：[0:未支付 1:已经支付过]", example = "1")
    private Boolean payStatus;

    @Schema(description = "订单支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payTime;

    @Schema(description = "应付金额（总），单位：分", example = "29053")
    private Integer payPrice;

    @Schema(description = "会员原价（总），单位：分", example = "17271")
    private Integer totalPrice;

    @Schema(description = "已购买会员金额（总），单位：分", example = "21137")
    private Integer yzfPrice;

    @Schema(description = "一级返佣比例")
    private Integer brokerageFirstPercent;

    @Schema(description = "二级返佣比例")
    private Integer brokerageSecondPercent;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    //支付人
    private String creator;

}
