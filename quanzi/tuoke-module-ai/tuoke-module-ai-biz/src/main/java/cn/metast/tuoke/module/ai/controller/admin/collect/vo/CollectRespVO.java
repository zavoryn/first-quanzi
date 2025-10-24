package cn.metast.tuoke.module.ai.controller.admin.collect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - AI 功能 收藏 Response VO")
@Data
public class CollectRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20772")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "收藏用户", requiredMode = Schema.RequiredMode.REQUIRED, example = "16522")
    private Long uid;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
