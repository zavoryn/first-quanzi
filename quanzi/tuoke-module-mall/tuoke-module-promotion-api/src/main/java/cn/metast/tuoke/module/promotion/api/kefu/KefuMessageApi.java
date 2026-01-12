package cn.metast.tuoke.module.promotion.api.kefu;

import cn.metast.tuoke.module.promotion.api.kefu.dto.KeFuConversationRespDTO;
import cn.metast.tuoke.module.promotion.api.kefu.dto.KeFuMessageDTO;

import java.util.List;

/**
 * 客服消息 API 接口
 *
 * @author HUIHUI
 */
public interface KefuMessageApi {

    /**
     * 发送客服消息
     */
    void sendMessage(KeFuMessageDTO message);

    /**
     * 创建群组会话
     */
    Long createConversationGroup(Long userId, Long receiverId);

    /**
     * 获取用户的聊天会话列表
     *
     * @param userId 用户ID
     * @return 会话列表
     */
    List<KeFuConversationRespDTO> getConversationListByUserId(Long userId);

}
