package cn.metast.tuoke.module.community.controller.admin.cmBlock.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 拉黑记录新增/修改 Request VO")
@Data
public class cmBlockSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27516")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16962")
    private Long userId;

    @Schema(description = "拉黑用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9914")
    @NotNull(message = "拉黑用户id不能为空")
    private Long blockUserId;

    @Schema(description = "圈子id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1001")
    @NotNull(message = "圈子id不能为空")
    private Long topicId;

}
