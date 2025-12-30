package cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 报名填写信息设置新增/修改 Request VO")
@Data
public class SnsActInfoCfgSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20896")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30344")
    @NotNull(message = "act_id不能为空")
    private Long actId;

    @Schema(description = "field_key")
    private String fieldKey;

    @Schema(description = "字段名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "字段名称不能为空")
    private String fieldName;

    @Schema(description = "字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "字段类型1 单行输入 2 多行输入 3 数字 4 单选 5 多选不能为空")
    private Integer fieldType;

    @Schema(description = "输入 0 必填  1 选填", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "输入 0 必填  1 选填不能为空")
    private String fieldInput;

    @Schema(description = "选择的时候，选项 ，多个空格分隔")
    private String fieldValue;

}