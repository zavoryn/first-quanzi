package cn.metast.tuoke.module.live.controller.admin.snsCommentreply.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 评论回复新增/修改 Request VO")
@Data
public class SnsCommentReplySaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26304")
    private Long id;

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1089")
    @NotNull(message = "ID不能为空")
    private Long replyId;

    @Schema(description = "评论ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8397")
    @NotNull(message = "评论ID不能为空")
    private Long commentId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "972")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "内容不能为空")
    private String content;

    @Schema(description = "点赞个数")
    private Integer likeNum;

}