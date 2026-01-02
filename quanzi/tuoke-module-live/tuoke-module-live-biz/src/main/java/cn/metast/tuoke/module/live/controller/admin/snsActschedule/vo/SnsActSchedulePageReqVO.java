package cn.metast.tuoke.module.live.controller.admin.snsActschedule.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 活动日程分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsActSchedulePageReqVO extends PageParam {

    @Schema(description = "act_id", example = "8060")
    private Long actId;

    @Schema(description = "item_name", example = "王五")
    private String itemName;

    @Schema(description = "sub_name", example = "赵六")
    private String subName;

    @Schema(description = "sub_stime")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] subStime;

    @Schema(description = "sub_etime")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] subEtime;

    @Schema(description = "sub_context")
    private String subContext;

    @Schema(description = "parent_id", example = "17712")
    private Long parentId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}