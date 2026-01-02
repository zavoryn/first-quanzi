package cn.metast.tuoke.module.live.controller.admin.snsAppconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 配置新增/修改 Request VO")
@Data
public class SnsAppConfigSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30736")
    private Long id;

    @Schema(description = "配置名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "配置名不能为空")
    private String cfgName;

    @Schema(description = "配置值")
    private String cfgValue;

    @Schema(description = "说明", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @NotEmpty(message = "说明不能为空")
    private String cfgRemark;

}