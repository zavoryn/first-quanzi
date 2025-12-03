package cn.metast.tuoke.module.heal.controller.admin.healHomeconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 首页模块配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class HealHomeConfigRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3039")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "标题", example = "王五")
    @ExcelProperty("标题")
    private String name;

    @Schema(description = "图片地址")
    @ExcelProperty("图片地址")
    private String icon;

    @Schema(description = "链接地址", example = "https://www.metast.cn")
    @ExcelProperty("链接地址")
    private String linkUrl;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "上下线 1（true）上线， 0（false）下线", example = "2")
    @ExcelProperty("上下线 1（true）上线， 0（false）下线")
    private String status;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "参数")
    @ExcelProperty("参数")
    private String param;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
