package cn.metast.tuoke.module.community.controller.admin.cmCommentthumbs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 评论用户关联新增/修改 Request VO")
@Data
public class CmCommentThumbsSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12153")
    private Long id;

    @Schema(description = "评论id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17057")
    @NotNull(message = "评论id不能为空")
    private Long commentId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26441")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    private Integer status;

}