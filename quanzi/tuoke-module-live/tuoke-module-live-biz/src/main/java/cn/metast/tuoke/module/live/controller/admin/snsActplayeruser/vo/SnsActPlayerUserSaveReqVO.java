package cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 参与人信息新增/修改 Request VO")
@Data
public class SnsActPlayerUserSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24989")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26083")
    @NotNull(message = "act_id不能为空")
    private Long actId;

    @Schema(description = "报名用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18805")
    @NotNull(message = "报名用户ID不能为空")
    private Long userId;

    @Schema(description = "参与人员信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "参与人员信息不能为空")
    private String fieldValue;

    @Schema(description = "报名人员id，区分多个报名人员", example = "27910")
    private Long actUserId;

    private String title;

}
