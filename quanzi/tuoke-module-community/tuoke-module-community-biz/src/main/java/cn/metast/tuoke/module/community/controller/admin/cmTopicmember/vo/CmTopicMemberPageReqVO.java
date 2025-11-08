package cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 圈子成员分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmTopicMemberPageReqVO extends PageParam {

    @Schema(description = "圈子ID", example = "16198")
    private Long topicId;

    @Schema(description = "用户ID", example = "31473")
    private Long userId;

    @Schema(description = "角色(0普通成员 1管理员 2创建者)")
    private Integer role;

    @Schema(description = "购买次数")
    private Integer orderNum;

    @Schema(description = "状态(0正常 1审核中 2被拒绝3拉黑)", example = "2")
    private Integer status;

    @Schema(description = "禁言结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] muteEndTime;

    @Schema(description = "加入时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] joinTime;

    @Schema(description = "购买时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "到期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "付费时长1一月2两月3个月4四个月5半年", example = "2")
    private Integer type;

    @Schema(description = "互动次数")
    private Integer interNum;

    @Schema(description = "拉黑原因", example = "随便")
    private String blockRemark;

    @Schema(description = "会员备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    private String nickname;
    private String mobile;
    private String beginTime;
    private String finishTime;
}
