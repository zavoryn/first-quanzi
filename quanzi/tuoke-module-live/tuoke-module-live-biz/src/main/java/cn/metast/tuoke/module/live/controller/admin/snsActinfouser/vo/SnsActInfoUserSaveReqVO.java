package cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 报名填写用户信息新增/修改 Request VO")
@Data
public class SnsActInfoUserSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17086")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28406")
    @NotNull(message = "act_id不能为空")
    private Long actId;

    @Schema(description = "报名用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12306")
    @NotNull(message = "报名用户ID不能为空")
    private Long userId;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "字段名称不能为空")
    private String fieldName;

    @Schema(description = "选择的时候，选项 ，多个空格分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "选择的时候，选项 ，多个空格分隔不能为空")
    private String fieldValue;

    @Schema(description = "报名id，区分多个报名人员", example = "24081")
    private Long actUserId;

}
