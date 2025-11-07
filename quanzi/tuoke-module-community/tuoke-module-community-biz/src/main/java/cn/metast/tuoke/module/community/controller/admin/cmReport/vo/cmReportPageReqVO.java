package cn.metast.tuoke.module.community.controller.admin.cmReport.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 举报记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class cmReportPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "13770")
    private Long userId;

    @Schema(description = "举报用户id", example = "3798")
    private Long reportUserId;

    @Schema(description = "举报原因", example = "不好")
    private String reason;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "圈子id", example = "1024")
    private Long topicId;

}
