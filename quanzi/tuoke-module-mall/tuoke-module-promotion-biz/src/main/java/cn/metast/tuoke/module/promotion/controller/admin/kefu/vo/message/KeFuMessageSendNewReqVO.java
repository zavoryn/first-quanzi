package cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(description = "管理后台 - 发送客服消息 Request VO")
@Data
public class KeFuMessageSendNewReqVO {

    @Schema(description = "会话编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12580")
    private Long conversationId;

    @Schema(description = "会话对象编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long userId;

    @Schema(description = "消息类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer contentType;

    @Schema(description = "消息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "消息不能为空")
    private String content;

    // ========== 后端设置的参数，前端无需传递 ==========

    @Schema(description = "发送人编号", example = "24571", hidden = true)
    private Long senderId;
    @Schema(description = "发送人类型", example = "1", hidden = true)
    private Integer senderType;

}
