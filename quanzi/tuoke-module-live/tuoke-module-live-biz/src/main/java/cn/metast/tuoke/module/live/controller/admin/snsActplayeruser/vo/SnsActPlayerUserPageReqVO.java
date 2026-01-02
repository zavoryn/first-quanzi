package cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 参与人信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsActPlayerUserPageReqVO extends PageParam {

    @Schema(description = "act_id", example = "26083")
    private Long actId;

    @Schema(description = "报名用户ID", example = "18805")
    private Long userId;

    @Schema(description = "参与人员信息")
    private String fieldValue;

    @Schema(description = "报名人员id，区分多个报名人员", example = "27910")
    private Long actUserId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    private String title;

}
