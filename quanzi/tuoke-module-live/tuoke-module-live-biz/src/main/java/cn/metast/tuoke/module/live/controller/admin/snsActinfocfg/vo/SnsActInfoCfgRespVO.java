package cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 报名填写信息设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActInfoCfgRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20896")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30344")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "field_key")
    @ExcelProperty("field_key")
    private String fieldKey;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("字段名称")
    private String fieldName;

    @Schema(description = "字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
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