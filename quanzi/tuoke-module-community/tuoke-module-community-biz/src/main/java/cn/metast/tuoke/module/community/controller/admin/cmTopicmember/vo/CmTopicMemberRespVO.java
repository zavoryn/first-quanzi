package cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 圈子成员 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmTopicMemberRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22514")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "圈子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16198")
    @ExcelProperty("圈子ID")
    private Long topicId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31473")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "角色(0普通成员 1管理员 2创建者)")
    @ExcelProperty("角色(0普通成员 1管理员 2创建者)")
    private Integer role;

    @Schema(description = "购买次数")
    @ExcelProperty("购买次数")
    private Integer orderNum;

    @Schema(description = "状态(0正常 1审核中 2被拒绝3拉黑)", example = "2")
    @ExcelProperty("状态(0正常 1审核中 2被拒绝3拉黑)")
    private Integer status;

    @Schema(description = "禁言结束时间")
    @ExcelProperty("禁言结束时间")
    private LocalDateTime muteEndTime;

    @Schema(description = "加入时间")
    @ExcelProperty("加入时间")
    private LocalDateTime joinTime;

    @Schema(description = "购买时间")
    @ExcelProperty("购买时间")
    private LocalDateTime startTime;

    @Schema(description = "到期时间")
    @ExcelProperty("到期时间")
    private LocalDateTime endTime;

    @Schema(description = "付费时长1一月2两月3个月4四个月5半年", example = "2")
    @ExcelProperty("付费时长1一月2两月3个月4四个月5半年")
    private Integer type;

    @Schema(description = "互动次数")
    @ExcelProperty("互动次数")
    private Integer interNum;

    @Schema(description = "拉黑原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "随便")
    @ExcelProperty("拉黑原因")
    private String blockRemark;

    @Schema(description = "会员备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @ExcelProperty("会员备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private Long endDays;

    //是否合约首次1首次2续费
    private Integer isContract;

    private String topicName;
    private String coverImage;
    private String nickname;
    private String name;
    private String mobile;
    private String avatar;
    //购买时长
    private Long remainingDays;
    //支付金额
    private Integer price;
    //退款金额
    private Integer refundPrice;
}
