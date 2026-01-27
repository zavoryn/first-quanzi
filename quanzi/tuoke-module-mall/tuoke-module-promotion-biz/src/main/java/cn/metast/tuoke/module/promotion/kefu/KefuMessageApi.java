package cn.metast.tuoke.module.promotion.kefu;

import cn.metast.tuoke.module.promotion.api.kefu.dto.KeFuMessageDTO;

/**
 * 秒杀活动 API 接口
 *
 * @author HUIHUI
 */
public interface KefuMessageApi {

    /**
     * 发送客服小
     */
    void sendMessage(KeFuMessageDTO message);

    Long createConversationGroup(Long userId,Long receiverId);

}
