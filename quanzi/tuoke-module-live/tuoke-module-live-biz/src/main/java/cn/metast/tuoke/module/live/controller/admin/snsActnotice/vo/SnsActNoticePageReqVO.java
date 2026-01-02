package cn.metast.tuoke.module.live.controller.admin.snsActnotice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 活动公告分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsActNoticePageReqVO extends PageParam {

    @Schema(description = "act_id", example = "7203")
    private Long actId;

    @Schema(description = "公共标题")
    private String title;

    @Schema(description = "时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] time;

    @Schema(description = "公告内容")
    private String context;

    @Schema(description = "公告类型（1通知 2公告）", example = "1")
    private String noticeType;

    @Schema(description = "公告状态（0正常 1关闭）", example = "1")
    private String status;

    @Schema(description = "re")
    private String re;

    @Schema(description = "发送用户的id", example = "26156")
    private String fromId;

    @Schema(description = "接收用户的id", example = "29471")
    private String toId;

    @Schema(description = "single 单个 some  特定人  all 全部")
    private String scope;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}