package cn.metast.tuoke.module.heal.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WnDeviceRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28893")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "用户ID")
    private Long uid;
    @ExcelProperty("用户")
    private String uname;

    @Schema(description = "设备SN编号")
    @ExcelProperty("设备SN编号")
    private String deviceSn;

    @Schema(description = "设备名称", example = "李四")
    @ExcelProperty("设备名称")
    private String name;

    @Schema(description = "设备型号")
    @ExcelProperty("设备型号")
    private String model;

    @Schema(description = "添加时间")
    @ExcelProperty("添加时间")
    private LocalDateTime createTime;

}
