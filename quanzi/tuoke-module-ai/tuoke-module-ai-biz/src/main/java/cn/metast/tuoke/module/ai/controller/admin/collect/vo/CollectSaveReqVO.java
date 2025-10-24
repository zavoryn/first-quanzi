package cn.metast.tuoke.module.ai.controller.admin.collect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - AI 功能 收藏新增/修改 Request VO")
@Data
public class CollectSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20772")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编号不能为空")
    private String code;

    @Schema(description = "收藏用户", requiredMode = Schema.RequiredMode.REQUIRED, example = "16522")
    private Long uid;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

}
