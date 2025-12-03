package cn.metast.tuoke.module.heal.controller.admin.healBanner.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 首页banner新增/修改 Request VO")
@Data
public class HealBannerSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2997")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "图片地址", example = "https://www.metast.cn")
    private String imageUrl;

    @Schema(description = "链接地址", example = "https://www.metast.cn")
    private String linkUrl;

    @Schema(description = "上下线 1（true）上线， 0（false）下线", example = "2")
    private String status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "0 : banner    1 : notice", example = "2")
    private Integer type;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "网页类型")
    private String subject;

    @Schema(description = "参数")
    private String param;

}