package cn.metast.tuoke.module.live.controller.admin.snsActguest.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 活动嘉宾分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsActGuestPageReqVO extends PageParam {

    @Schema(description = "act_id", example = "15421")
    private Long actId;

    @Schema(description = "name", example = "李四")
    private String name;

    @Schema(description = "avatar")
    private String avatar;

    @Schema(description = "introduce")
    private String introduce;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}