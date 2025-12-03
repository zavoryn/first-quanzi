package cn.metast.tuoke.module.heal.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 设备信息新增/修改 Request VO")
@Data
public class WnDeviceSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28893")
    private Long id;
    @Schema(description = "用户ID")
    private Long uid;

    @Schema(description = "设备SN编号")
    private String deviceSn;

    @Schema(description = "设备名称", example = "李四")
    private String name;

    @Schema(description = "设备型号")
    private String model;

}
