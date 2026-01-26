package cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "用户 App - 发送客服消息 Request VO")
@Data
public class AppKeFuMessageSendReqVO {

    @Schema(description = "消息类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "消息类型不能为空")
    private Integer contentType;
    @Schema(description = "消息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "消息不能为空")
    private String content;

    // ========== 后端设置的参数，前端无需传递 ==========

    @Schema(description = "发送人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24571", hidden = true)
    private Long senderId;
    @Schema(description = "发送人类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1", hidden = true)
    private Integer senderType;
    @Schema(description = "接收人编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "24571", hidden = true)
    private Long receiverId;

    private Long conversationId;

    //私聊传0,聊天室传1==聊天室需要审核
    private Long chatType;
    //圈子id
    private Long topicId;

    /**
     * 引用的消息内容
     */
    private String quoteContent;

    /**
     * 引用人ID（被引用人id）
     */
    private Long quoteUserId;

}
