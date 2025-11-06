package cn.metast.tuoke.module.community.controller.admin.cmFollow.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户关注中间分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmFollowPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "7473")
    private Long userId;

    @Schema(description = "关注的用户id", example = "23199")
    private Long followUserId;

    @Schema(description = "状态(0取消,1关注)")
    private Boolean state;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}