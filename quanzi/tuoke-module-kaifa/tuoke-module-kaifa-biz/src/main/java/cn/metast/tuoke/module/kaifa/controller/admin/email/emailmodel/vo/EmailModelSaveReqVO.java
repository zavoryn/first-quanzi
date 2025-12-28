package cn.metast.tuoke.module.kaifa.controller.admin.email.emailmodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 模板-快速文本新增/修改 Request VO")
@Data
public class EmailModelSaveReqVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29301")
    private Long id;

    @Schema(description = "类型（model个人模板、text快速文本)", example = "1")
    private String type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "文本内容(摘要)")
    private String conteText;

    @Schema(description = "状态", example = "1")
    private String status;

}
