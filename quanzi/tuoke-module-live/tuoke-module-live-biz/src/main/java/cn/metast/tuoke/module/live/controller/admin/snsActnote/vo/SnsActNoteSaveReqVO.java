package cn.metast.tuoke.module.live.controller.admin.snsActnote.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 活动记录新增/修改 Request VO")
@Data
public class SnsActNoteSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23947")
    private Long id;

    @Schema(description = "user_id", example = "15047")
    private Long userId;

    @Schema(description = "地址")
    private String addr;

}