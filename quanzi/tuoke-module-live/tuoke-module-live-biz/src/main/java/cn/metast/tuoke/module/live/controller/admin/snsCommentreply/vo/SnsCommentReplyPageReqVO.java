package cn.metast.tuoke.module.live.controller.admin.snsCommentreply.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 评论回复分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SnsCommentReplyPageReqVO extends PageParam {

    @Schema(description = "ID", example = "1089")
    private Long replyId;

    @Schema(description = "评论ID", example = "8397")
    private Long commentId;

    @Schema(description = "用户ID", example = "972")
    private Long userId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "点赞个数")
    private Integer likeNum;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}