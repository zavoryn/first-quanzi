package cn.metast.tuoke.module.community.controller.admin.cmPostcollection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户帖子中间新增/修改 Request VO")
@Data
public class CmPostCollectionSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23748")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6169")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "帖子id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30152")
    @NotNull(message = "帖子id不能为空")
    private Long postId;

}