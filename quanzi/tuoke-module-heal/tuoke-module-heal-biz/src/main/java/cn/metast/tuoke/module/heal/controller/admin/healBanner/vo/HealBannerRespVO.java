package cn.metast.tuoke.module.heal.controller.admin.healBanner.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 首页banner Response VO")
@Data
@ExcelIgnoreUnannotated
public class HealBannerRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2997")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "图片地址", example = "https://www.metast.cn")
    @ExcelProperty("图片地址")
    private String imageUrl;

    @Schema(description = "链接地址", example = "https://www.metast.cn")
    @ExcelProperty("链接地址")
    private String linkUrl;

    @Schema(description = "上下线 1（true）上线， 0（false）下线", example = "2")
    @ExcelProperty("上下线 1（true）上线， 0（false）下线")
    private String status;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "0 : banner    1 : notice", example = "2")
    @ExcelProperty("0 : banner    1 : notice")
    private Integer type;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "网页类型")
    @ExcelProperty("网页类型")
    private String subject;

    @Schema(description = "参数")
    @ExcelProperty("参数")
    private String param;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}