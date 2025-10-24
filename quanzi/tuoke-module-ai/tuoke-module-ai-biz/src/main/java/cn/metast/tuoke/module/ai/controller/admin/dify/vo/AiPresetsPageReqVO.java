package cn.metast.tuoke.module.ai.controller.admin.dify.vo;

import cn.metast.tuoke.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - AI API 密钥分页 Request VO")
@Data
public class AiPresetsPageReqVO extends PageParam {

    @Schema(description = "名称", example = "")
    private String name;

    @Schema(description = "类型", example = "")
    private String type;

    @Schema(description = "状态", example = "0")
    private Integer status;

}
