package cn.metast.tuoke.module.community.controller.admin.cmLink.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 首页轮播图 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmLinkRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31156")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "跳转路径", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.metast.cn")
    @ExcelProperty("跳转路径")
    private String url;

    @Schema(description = "图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("图片")
    private String img;

    @Schema(description = "广场轮播图", example = "1")
    @ExcelProperty("广场轮播图")
    private Integer type;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}