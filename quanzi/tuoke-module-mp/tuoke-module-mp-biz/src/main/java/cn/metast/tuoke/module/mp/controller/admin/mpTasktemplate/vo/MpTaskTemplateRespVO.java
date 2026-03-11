package cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 公众号模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MpTaskTemplateRespVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15242")
    @ExcelProperty("系统主键")
    private Long id;

    @Schema(description = "模板url", example = "https://www.iocoder.cn")
    @ExcelProperty("模板url")
    private String url;

    @Schema(description = "模板内容")
    @ExcelProperty("模板内容")
    private String content;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}