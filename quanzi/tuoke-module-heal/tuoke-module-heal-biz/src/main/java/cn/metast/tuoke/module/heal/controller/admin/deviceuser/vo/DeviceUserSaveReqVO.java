package cn.metast.tuoke.module.heal.controller.admin.deviceuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 设备绑定用户信息新增/修改 Request VO")
@Data
public class DeviceUserSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1444")
    private Long id;

    @Schema(description = "设备SN编号")
    private String deviceSn;

    @Schema(description = "用户uid", example = "595")
    private Long uid;

    @Schema(description = "状态(1-默认)", example = "2")
    private String status;

}
