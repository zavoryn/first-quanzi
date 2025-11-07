package cn.metast.tuoke.module.community.controller.admin.cmMessage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 圈子消息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CmMessageRespVO {

    @Schema(description = "消息ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7949")
    @ExcelProperty("消息ID")
    private Long id;

    @Schema(description = "发送者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31565")
    @ExcelProperty("发送者ID")
    private Long fromUserId;

    @Schema(description = "接收者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14765")
    @ExcelProperty("接收者ID")
    private Long toUserId;

    @Schema(description = "消息类型(0系统消息 1评论 2回复 3点赞 4关注 5私信)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("消息类型(0系统消息 1评论 2回复 3点赞 4关注 5私信)")
    private Integer type;

    @Schema(description = "消息内容")
    @ExcelProperty("消息内容")
    private String content;

    @Schema(description = "关联帖子ID", example = "17553")
    @ExcelProperty("关联帖子ID")
    private Long postId;

    @Schema(description = "关联评论ID", example = "3086")
    @ExcelProperty("关联评论ID")
    private Long commentId;

    @Schema(description = "是否已读(0未读 1已读)")
    @ExcelProperty("是否已读(0未读 1已读)")
    private Integer isRead;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}