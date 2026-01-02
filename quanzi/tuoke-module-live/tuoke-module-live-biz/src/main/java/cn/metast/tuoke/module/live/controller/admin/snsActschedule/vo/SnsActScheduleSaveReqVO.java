package cn.metast.tuoke.module.live.controller.admin.snsActschedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 活动日程新增/修改 Request VO")
@Data
public class SnsActScheduleSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14078")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8060")
    @NotNull(message = "act_id不能为空")
    private Long actId;

    @Schema(description = "item_name", example = "王五")
    private String itemName;

    @Schema(description = "sub_name", example = "赵六")
    private String subName;

    @Schema(description = "sub_stime")
    private LocalDateTime subStime;

    @Schema(description = "sub_etime")
    private LocalDateTime subEtime;

    @Schema(description = "sub_context")
    private String subContext;

    @Schema(description = "parent_id", example = "17712")
    private Long parentId;

}