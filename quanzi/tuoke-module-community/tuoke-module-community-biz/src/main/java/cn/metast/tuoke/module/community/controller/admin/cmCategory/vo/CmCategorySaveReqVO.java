package cn.metast.tuoke.module.community.controller.admin.cmCategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 圈子分类新增/修改 Request VO")
@Data
public class CmCategorySaveReqVO {

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10113")
    private Long id;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "元圈")
    @NotEmpty(message = "分类名称不能为空")
    private String cateName;

    @Schema(description = "是否推荐")
    private Integer isTop;

    @Schema(description = "图片地址")
    private String coverImage;

    @Schema(description = "排序")
    private String sort;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    private Integer status;

}