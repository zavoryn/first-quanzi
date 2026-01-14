package cn.metast.tuoke.module.promotion.api.kefu;

import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.promotion.api.kefu.dto.KeFuConversationRespDTO;
import cn.metast.tuoke.module.promotion.api.kefu.dto.KeFuMessageDTO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessageSendReqVO;
import cn.metast.tuoke.module.promotion.dal.dataobject.kefu.KeFuConversationDO;
import cn.metast.tuoke.module.promotion.service.kefu.KeFuConversationService;
import cn.metast.tuoke.module.promotion.service.kefu.KeFuMessageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 消息接口实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class KefuMessageApiImpl implements KefuMessageApi {
    @Resource
    private KeFuMessageService kefuMessageService;
    @Resource
    private KeFuConversationService conversationService;

    @Override
    public void sendMessage(KeFuMessageDTO messageDTO) {
        AppKeFuMessageSendReqVO sendReqVO = BeanUtils.toBean(messageDTO, AppKeFuMessageSendReqVO.class);
        sendReqVO.setSenderId(getLoginUserId()).setSenderType(UserTypeEnum.MEMBER.getValue()); // 设置用户编号和类型
        kefuMessageService.sendKefuMessageTopic(sendReqVO);
    }

    @Override
    public Long createConversationGroup(Long userId, Long receiverId) {
        KeFuConversationDO keFuConversationDO = conversationService.getOrCreateConversationGroup(userId, receiverId);
        return keFuConversationDO.getId();
    }

    @Override
    public List<KeFuConversationRespDTO> getConversationListByUserId(Long userId) {
        List<KeFuConversationDO> conversations = conversationService.getConversationListByUserId(userId);
        return conversations.stream().map(this::convertConversation).collect(Collectors.toList());
    }

    private KeFuConversationRespDTO convertConversation(KeFuConversationDO conversation) {
        KeFuConversationRespDTO dto = new KeFuConversationRespDTO();
        dto.setId(conversation.getId());
        dto.setUserId(conversation.getUserId());
        dto.setReceiverId(conversation.getReceiverId());
        dto.setLastMessageContent(conversation.getLastMessageContent());
        dto.setLastMessageContentType(conversation.getLastMessageContentType());
        dto.setLastMessageTime(conversation.getLastMessageTime());
        dto.setAdminUnreadMessageCount(conversation.getAdminUnreadMessageCount());
        dto.setAdminPinned(conversation.getAdminPinned());
        return dto;
    }
}
