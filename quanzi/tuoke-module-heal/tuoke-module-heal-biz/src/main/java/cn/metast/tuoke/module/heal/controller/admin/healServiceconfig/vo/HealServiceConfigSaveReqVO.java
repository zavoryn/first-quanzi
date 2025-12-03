package cn.metast.tuoke.module.heal.controller.admin.healServiceconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 服务配置新增/修改 Request VO")
@Data
public class HealServiceConfigSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31066")
    private Long id;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "参数")
    private String param;

}