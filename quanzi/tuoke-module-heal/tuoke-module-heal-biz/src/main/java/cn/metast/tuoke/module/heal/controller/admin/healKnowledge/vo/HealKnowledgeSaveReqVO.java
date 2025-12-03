package cn.metast.tuoke.module.heal.controller.admin.healKnowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 健康知识新增/修改 Request VO")
@Data
public class HealKnowledgeSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "32285")
    private Long id;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "封面url", example = "https://www.metast.cn")
    private String coverUrl;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "网页链接", example = "https://www.metast.cn")
    private String linkUrl;

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

}