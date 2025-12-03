package cn.metast.tuoke.module.heal.controller.admin.healServiceconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 服务配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class HealServiceConfigRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31066")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "名称", example = "李四")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "类型", example = "1")
    @ExcelProperty("类型")
    private String type;

    @Schema(description = "参数")
    @ExcelProperty("参数")
    private String param;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}