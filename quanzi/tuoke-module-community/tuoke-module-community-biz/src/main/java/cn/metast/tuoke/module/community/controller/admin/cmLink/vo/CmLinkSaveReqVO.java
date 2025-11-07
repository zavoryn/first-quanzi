package cn.metast.tuoke.module.community.controller.admin.cmLink.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 首页轮播图新增/修改 Request VO")
@Data
public class CmLinkSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31156")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "标题不能为空")
    private String title;

    @Schema(description = "跳转路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.metast.cn")
    @NotEmpty(message = "跳转路径不能为空")
    private String url;

    @Schema(description = "图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "图片不能为空")
    private String img;

    @Schema(description = "广场轮播图", example = "1")
    private Integer type;

}