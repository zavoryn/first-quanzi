package cn.metast.tuoke.module.community.controller.admin.cmMessage.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 圈子消息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmMessagePageReqVO extends PageParam {

    @Schema(description = "发送者ID", example = "31565")
    private Long fromUserId;

    @Schema(description = "接收者ID", example = "14765")
    private Long toUserId;

    @Schema(description = "消息类型(0系统消息 1评论 2回复 3点赞 4关注 5私信)", example = "2")
    private Integer type;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "关联帖子ID", example = "17553")
    private Long postId;

    @Schema(description = "关联评论ID", example = "3086")
    private Long commentId;

    @Schema(description = "是否已读(0未读 1已读)")
    private Integer isRead;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}