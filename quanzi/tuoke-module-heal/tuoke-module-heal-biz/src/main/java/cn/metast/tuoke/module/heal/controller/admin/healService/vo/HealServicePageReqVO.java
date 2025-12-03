package cn.metast.tuoke.module.heal.controller.admin.healService.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 服务列分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HealServicePageReqVO extends PageParam {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "简介")
    private String introduce;

    @Schema(description = "封面url", example = "https://www.metast.cn")
    private String coverUrl;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "网页链接", example = "https://www.metast.cn")
    private String linkUrl;

    @Schema(description = "价格")
    private BigDecimal fee;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "分享个数")
    private Integer shareNum;

    @Schema(description = "点赞个数")
    private Integer likeNum;

    @Schema(description = "评论个数")
    private Integer commentNum;

    @Schema(description = "访问次数")
    private Integer visitNum;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}