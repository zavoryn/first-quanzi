package cn.metast.tuoke.module.community.controller.admin.cmReport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 举报记录新增/修改 Request VO")
@Data
public class cmReportSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18036")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13770")
    private Long userId;

    @Schema(description = "举报用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3798")
    @NotNull(message = "举报用户id不能为空")
    private Long reportUserId;

    @Schema(description = "举报原因", example = "不好")
    private String reason;

    @Schema(description = "圈子id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "圈子id不能为空")
    private Long topicId;

}
