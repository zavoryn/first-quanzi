package cn.metast.tuoke.module.live.controller.admin.snsActachievement.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 活动-成绩分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsActAchievementPageReqVO extends PageParam {

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "图片")
    private String logo;

    @Schema(description = "分数")
    private Integer fraction;

    @Schema(description = "对手名称", example = "王五")
    private String toname;

    @Schema(description = "对手图片")
    private String tologo;

    @Schema(description = "对手分数")
    private Integer tofraction;

    @Schema(description = "创建人", example = "23472")
    private Integer createUserId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}