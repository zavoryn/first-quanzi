package cn.metast.tuoke.module.community.controller.admin.cmCategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 圈子分类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmCategoryRespVO {

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10113")
    @ExcelProperty("分类ID")
    private Long id;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "元圈")
    @ExcelProperty("分类名称")
    private String cateName;

    @Schema(description = "是否推荐")
    @ExcelProperty("是否推荐")
    private Integer isTop;

    @Schema(description = "图片地址")
    @ExcelProperty("图片地址")
    private String coverImage;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private String sort;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}