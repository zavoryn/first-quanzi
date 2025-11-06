package cn.metast.tuoke.module.community.controller.admin.cmCollect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 收藏记录新增/修改 Request VO")
@Data
public class CmCollectSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12103")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24529")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "收藏的帖子id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14267")
    @NotNull(message = "收藏的帖子id不能为空")
    private Long postId;

    @Schema(description = "状态(0取消,1收藏)")
    private Boolean state;

}