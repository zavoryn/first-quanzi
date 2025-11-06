package cn.metast.tuoke.module.community.controller.admin.cmConfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 圈子配置新增/修改 Request VO")
@Data
public class CmConfigSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25595")
    private Long id;

    @Schema(description = "部门id", example = "4803")
    private Long deptId;

    @Schema(description = "圈子id", example = "14062")
    private Long topicId;

}