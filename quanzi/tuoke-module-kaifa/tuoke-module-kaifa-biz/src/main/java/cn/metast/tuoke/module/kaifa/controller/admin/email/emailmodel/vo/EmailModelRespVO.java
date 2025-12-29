package cn.metast.tuoke.module.kaifa.controller.admin.email.emailmodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 模板-快速文本 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EmailModelRespVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29301")
    @ExcelProperty("系统主键")
    private Long id;

    @Schema(description = "类型（model个人模板、text快速文本)", example = "1")
    @ExcelProperty("类型（model个人模板、text快速文本)")
    private String type;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "内容")
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "文本内容(摘要)")
    @ExcelProperty("文本内容(摘要)")
    private String conteText;

    @Schema(description = "状态", example = "1")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
