package cn.metast.tuoke.module.heal.controller.admin.healService.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 服务列 Response VO")
@Data
@ExcelIgnoreUnannotated
public class HealServiceRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30768")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "简介")
    @ExcelProperty("简介")
    private String introduce;

    @Schema(description = "封面url", example = "https://www.metast.cn")
    @ExcelProperty("封面url")
    private String coverUrl;

    @Schema(description = "内容")
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "网页链接", example = "https://www.metast.cn")
    @ExcelProperty("网页链接")
    private String linkUrl;

    @Schema(description = "价格")
    @ExcelProperty("价格")
    private BigDecimal fee;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Long sort;

    @Schema(description = "分享个数")
    @ExcelProperty("分享个数")
    private Integer shareNum;

    @Schema(description = "点赞个数")
    @ExcelProperty("点赞个数")
    private Integer likeNum;

    @Schema(description = "评论个数")
    @ExcelProperty("评论个数")
    private Integer commentNum;

    @Schema(description = "访问次数")
    @ExcelProperty("访问次数")
    private Integer visitNum;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}