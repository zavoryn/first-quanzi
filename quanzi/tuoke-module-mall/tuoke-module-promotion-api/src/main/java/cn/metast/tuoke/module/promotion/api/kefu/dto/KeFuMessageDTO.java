package cn.metast.tuoke.module.promotion.api.kefu.dto;
import lombok.Data;

@Data
public class KeFuMessageDTO {
    //消息类型
    private Integer contentType;
    //消息不能为空
    private String content;

    // ========== 后端设置的参数，前端无需传递 ==========
    //发送人编号
    private Long senderId;
    //发送人类型
    private Integer senderType;
    //接收人编号
    private Long receiverId;

    private Long conversationId;

}
