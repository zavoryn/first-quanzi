package cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报名填写用户信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsActInfoUserPageReqVO extends PageParam {

    @Schema(description = "act_id", example = "28406")
    private Long actId;

    @Schema(description = "报名用户ID", example = "12306")
    private Long userId;

    @Schema(description = "字段名称", example = "芋艿")
    private String fieldName;

    @Schema(description = "选择的时候，选项 ，多个空格分隔")
    private String fieldValue;

    @Schema(description = "报名id，区分多个报名人员", example = "24081")
    private Long actUserId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
