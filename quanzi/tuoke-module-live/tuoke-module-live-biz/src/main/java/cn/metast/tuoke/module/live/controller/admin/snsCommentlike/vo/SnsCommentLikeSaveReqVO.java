package cn.metast.tuoke.module.live.controller.admin.snsCommentlike.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 评论点赞人新增/修改 Request VO")
@Data
public class SnsCommentLikeSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3868")
    private Long id;

    @Schema(description = "post_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19662")
    @NotNull(message = "post_id不能为空")
    private Long postId;

    @Schema(description = "comment_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1886")
    @NotNull(message = "comment_id不能为空")
    private Long commentId;

    @Schema(description = "user_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28720")
    @NotNull(message = "user_id不能为空")
    private Long userId;

}