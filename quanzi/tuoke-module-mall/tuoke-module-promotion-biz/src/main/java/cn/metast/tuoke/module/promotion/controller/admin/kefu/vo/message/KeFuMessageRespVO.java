package cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 客服消息 Response VO")
@Data
public class KeFuMessageRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23202")
    private Long id;

    @Schema(description = "会话编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12580")
    private Long conversationId;

    @Schema(description = "发送人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24571")
    private Long senderId;
    @Schema(description = "发送人头像", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://tuoke.com/images/avatar.jpg")
    private String senderAvatar;

    @Schema(description = "发送人类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer senderType;

    @Schema(description = "接收人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29124")
    private Long receiverId;

    @Schema(description = "接收人类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer receiverType;

    @Schema(description = "消息类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer contentType;

    @Schema(description = "消息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "是否已读", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Boolean readStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private String senderName;

    private String receiverAvatar;
    private String receiverName;

    /**
     * 引用的消息内容（被引用消息的文本）
     */
    private String quoteContent;

    /**
     * 引用人ID（被引用消息的发送人ID）
     */
    private Long quoteUserId;

    /**
     * 引用人名称（被引用消息发送人的昵称）
     */
    private String quoteUserName;
}
