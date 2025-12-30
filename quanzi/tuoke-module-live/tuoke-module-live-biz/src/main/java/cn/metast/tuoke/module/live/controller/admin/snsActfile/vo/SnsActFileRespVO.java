package cn.metast.tuoke.module.live.controller.admin.snsActfile.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动资料 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActFileRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12943")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1817")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "文件标题")
    @ExcelProperty("文件标题")
    private String title;

    @Schema(description = "时间")
    @ExcelProperty("时间")
    private LocalDateTime time;

    @Schema(description = "文件存储id", example = "6493")
    @ExcelProperty("文件存储id")
    private Long fileId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}