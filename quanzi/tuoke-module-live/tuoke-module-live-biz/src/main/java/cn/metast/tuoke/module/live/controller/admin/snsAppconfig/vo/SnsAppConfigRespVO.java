package cn.metast.tuoke.module.live.controller.admin.snsAppconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsAppConfigRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30736")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "配置名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("配置名")
    private String cfgName;

    @Schema(description = "配置值")
    @ExcelProperty("配置值")
    private String cfgValue;

    @Schema(description = "说明", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @ExcelProperty("说明")
    private String cfgRemark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}