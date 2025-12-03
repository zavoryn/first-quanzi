package cn.metast.tuoke.module.heal.controller.admin.healHomeconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 首页模块配置新增/修改 Request VO")
@Data
public class HealHomeConfigSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3039")
    private Long id;

    @Schema(description = "标题", example = "王五")
    private String name;

    @Schema(description = "图片地址")
    private String icon;

    @Schema(description = "链接地址", example = "https://www.metast.cn")
    private String linkUrl;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "上下线 1（true）上线， 0（false）下线", example = "2")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "参数")
    private String param;

}
