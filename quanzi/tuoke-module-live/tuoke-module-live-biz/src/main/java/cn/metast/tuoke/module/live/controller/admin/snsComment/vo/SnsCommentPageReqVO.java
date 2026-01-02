package cn.metast.tuoke.module.live.controller.admin.snsComment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评论分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsCommentPageReqVO extends PageParam {

    @Schema(description = "comment_id", example = "681")
    private Long commentId;

    @Schema(description = "动态ID", example = "27012")
    private Long postId;

    @Schema(description = "用户ID", example = "26641")
    private Long userId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞个数")
    private Integer likeNum;

    @Schema(description = "tail")
    private String tail;

    @Schema(description = "type", example = "1")
    private String type;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}