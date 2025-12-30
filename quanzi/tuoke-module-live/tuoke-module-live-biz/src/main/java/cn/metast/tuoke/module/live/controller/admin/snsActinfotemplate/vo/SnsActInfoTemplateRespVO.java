package cn.metast.tuoke.module.live.controller.admin.snsActinfotemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActInfoTemplateRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7279")
    @ExcelProperty("编号")
    private Long columnId;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17928")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("字段名称")
    private String fieldName;

    @Schema(description = "字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选")
    private Integer fieldType;

    @Schema(description = "输入 0 必填  1 选填", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("输入 0 必填  1 选填")
    private String fieldInput;

    @Schema(description = "选择的时候，选项 ，多个空格分隔")
    @ExcelProperty("选择的时候，选项 ，多个空格分隔")
    private String fieldValue;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}