package cn.metast.tuoke.module.live.controller.admin.snsActinfotemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 活动模板新增/修改 Request VO")
@Data
public class SnsActInfoTemplateSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7279")
    @NotNull(message = "编号不能为空")
    private Long columnId;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17928")
    private Long id;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "字段名称不能为空")
    private String fieldName;

    @Schema(description = "字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选不能为空")
    private Integer fieldType;

    @Schema(description = "输入 0 必填  1 选填", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "输入 0 必填  1 选填不能为空")
    private String fieldInput;

    @Schema(description = "选择的时候，选项 ，多个空格分隔")
    private String fieldValue;

}