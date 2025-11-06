package cn.metast.tuoke.module.community.controller.admin.cmFollow.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户关注中间新增/修改 Request VO")
@Data
public class CmFollowSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14283")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7473")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "关注的用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23199")
    @NotNull(message = "关注的用户id不能为空")
    private Long followUserId;

    @Schema(description = "状态(0取消,1关注)")
    private Boolean state;

}