package cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 公众号模板新增/修改 Request VO")
@Data
public class MpTaskTemplateSaveReqVO {

    @Schema(description = "系统主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15242")
    private Long id;

    @Schema(description = "模板url", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "模板内容")
    private String content;

}