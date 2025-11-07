package cn.metast.tuoke.module.community.controller.admin.cmMessage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 圈子消息新增/修改 Request VO")
@Data
public class CmMessageSaveReqVO {

    @Schema(description = "消息ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7949")
    private Long id;

    @Schema(description = "发送者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31565")
    @NotNull(message = "发送者ID不能为空")
    private Long fromUserId;

    @Schema(description = "接收者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14765")
    @NotNull(message = "接收者ID不能为空")
    private Long toUserId;

    @Schema(description = "消息类型(0系统消息 1评论 2回复 3点赞 4关注 5私信)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "消息类型(0系统消息 1评论 2回复 3点赞 4关注 5私信)不能为空")
    private Integer type;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "关联帖子ID", example = "17553")
    private Long postId;

    @Schema(description = "关联评论ID", example = "3086")
    private Long commentId;

    @Schema(description = "是否已读(0未读 1已读)")
    private Integer isRead;

}