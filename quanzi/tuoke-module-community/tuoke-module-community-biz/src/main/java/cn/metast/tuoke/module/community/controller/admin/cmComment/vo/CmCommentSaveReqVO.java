package cn.metast.tuoke.module.community.controller.admin.cmComment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 圈子评论新增/修改 Request VO")
@Data
public class CmCommentSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "881")
    private Long id;

    @Schema(description = "父级id", example = "26634")
    private Long parentId;

    @Schema(description = "评论类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer type;

    @Schema(description = "评论作者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15743")
    private Long userId;

    @Schema(description = "被回复用户ID", example = "20226")
    private Long toUserId;

    @Schema(description = "评论帖子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11472")
    private Long postId;

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer status;

}
