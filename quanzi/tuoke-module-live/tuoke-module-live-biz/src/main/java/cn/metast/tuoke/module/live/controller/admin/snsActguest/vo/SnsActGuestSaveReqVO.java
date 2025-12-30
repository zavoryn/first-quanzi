package cn.metast.tuoke.module.live.controller.admin.snsActguest.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 活动嘉宾新增/修改 Request VO")
@Data
public class SnsActGuestSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28014")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15421")
    @NotNull(message = "act_id不能为空")
    private Long actId;

    @Schema(description = "name", example = "李四")
    private String name;

    @Schema(description = "avatar")
    private String avatar;

    @Schema(description = "introduce")
    private String introduce;

}