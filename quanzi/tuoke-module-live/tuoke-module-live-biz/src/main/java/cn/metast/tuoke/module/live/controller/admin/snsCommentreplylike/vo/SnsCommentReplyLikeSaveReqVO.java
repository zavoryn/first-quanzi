package cn.metast.tuoke.module.live.controller.admin.snsCommentreplylike.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 评论回复点赞人数新增/修改 Request VO")
@Data
public class SnsCommentReplyLikeSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23655")
    private Long id;

    @Schema(description = "reply_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28419")
    @NotNull(message = "reply_id不能为空")
    private Long replyId;

    @Schema(description = "user_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20028")
    @NotNull(message = "user_id不能为空")
    private Long userId;

}