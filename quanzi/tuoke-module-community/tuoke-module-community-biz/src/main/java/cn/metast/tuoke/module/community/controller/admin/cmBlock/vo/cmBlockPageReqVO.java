package cn.metast.tuoke.module.community.controller.admin.cmBlock.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 拉黑记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class cmBlockPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "16962")
    private Long userId;

    @Schema(description = "拉黑用户id", example = "9914")
    private Long blockUserId;

    @Schema(description = "圈子id", example = "1001")
    private Long topicId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
