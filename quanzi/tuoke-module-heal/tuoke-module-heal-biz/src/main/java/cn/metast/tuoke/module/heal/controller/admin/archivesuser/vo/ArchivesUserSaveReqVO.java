package cn.metast.tuoke.module.heal.controller.admin.archivesuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 档案信息关联用户新增/修改 Request VO")
@Data
public class ArchivesUserSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22335")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28722")
    @NotNull(message = "用户ID不能为空")
    private Long uid;

    @Schema(description = "档案ID", example = "20560")
    private Long archivesId;

}
