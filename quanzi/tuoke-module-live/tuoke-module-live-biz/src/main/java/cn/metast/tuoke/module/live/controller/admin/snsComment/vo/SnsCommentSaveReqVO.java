package cn.metast.tuoke.module.live.controller.admin.snsComment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 评论新增/修改 Request VO")
@Data
public class SnsCommentSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23693")
    private Long id;

    @Schema(description = "comment_id", example = "681")
    private Long commentId;

    @Schema(description = "动态ID", example = "27012")
    private Long postId;

    @Schema(description = "用户ID", example = "26641")
    private Long userId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞个数")
    private Integer likeNum;

    @Schema(description = "tail")
    private String tail;

    @Schema(description = "type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "type不能为空")
    private String type;

}