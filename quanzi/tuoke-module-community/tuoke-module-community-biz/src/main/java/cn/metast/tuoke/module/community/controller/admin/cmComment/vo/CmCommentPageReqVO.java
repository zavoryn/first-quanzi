package cn.metast.tuoke.module.community.controller.admin.cmComment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 圈子评论分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmCommentPageReqVO extends PageParam {

    @Schema(description = "父级id", example = "26634")
    private Long parentId;

    @Schema(description = "评论类型", example = "2")
    private Integer type;

    @Schema(description = "评论作者ID", example = "15743")
    private Long userId;

    @Schema(description = "被回复用户ID", example = "20226")
    private Long toUserId;

    @Schema(description = "评论帖子ID", example = "11472")
    private Long postId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    //圈子id
    private Long topicId;
    //被拉黑用户ID列表（用于排除）
    private List<Long> blockUserIds;

}
