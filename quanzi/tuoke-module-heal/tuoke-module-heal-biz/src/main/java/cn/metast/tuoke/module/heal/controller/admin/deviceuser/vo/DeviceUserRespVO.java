package cn.metast.tuoke.module.heal.controller.admin.deviceuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备绑定用户信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeviceUserRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1444")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "设备SN编号")
    @ExcelProperty("设备SN编号")
    private String deviceSn;

    @Schema(description = "用户uid", example = "595")
    @ExcelProperty("用户uid")
    private Long uid;

    @Schema(description = "添加时间")
    @ExcelProperty("添加时间")
    private LocalDateTime createTime;

    @Schema(description = "状态(1-默认)", example = "2")
    @ExcelProperty("状态(1-默认)")
    private String status;

}
