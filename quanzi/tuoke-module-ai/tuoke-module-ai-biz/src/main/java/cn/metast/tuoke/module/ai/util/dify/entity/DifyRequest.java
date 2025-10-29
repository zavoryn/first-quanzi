package cn.metast.tuoke.module.ai.util.dify.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DifyRequest implements Serializable {
    private Map<String, Object> inputs;
    private String query;
    private String response_mode;
    private String conversation_id;
    private String user;

    private String platform;

    // 描述
    private String basicInstruction;
    // 背景要求
    private String backgroundDetail;
    // 风格-语气
    private String style;

    // 目标语言
    private String language;

    // dify 对话的会话ID
    private String conversationId;
    // dify 对话的图片
    private String fileUrl;

}
