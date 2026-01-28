package cn.metast.tuoke.module.promotion.service.kefu;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.collection.CollectionUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.api.cmTopic.TopicApi;
import cn.metast.tuoke.module.community.api.cmBlock.BlockApi;
import cn.metast.tuoke.module.infra.api.websocket.WebSocketSenderApi;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageListReqVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageRespVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageSendNewReqVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageSendReqVO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessagePageReqVO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessageSendReqVO;
import cn.metast.tuoke.module.promotion.dal.dataobject.kefu.KeFuConversationDO;
import cn.metast.tuoke.module.promotion.dal.dataobject.kefu.KeFuMessageDO;
import cn.metast.tuoke.module.promotion.dal.mysql.kefu.KeFuConversationMapper;
import cn.metast.tuoke.module.promotion.dal.mysql.kefu.KeFuMessageMapper;
import cn.metast.tuoke.module.system.api.user.AdminUserApi;
import cn.metast.tuoke.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.*;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;
import static cn.metast.tuoke.module.promotion.enums.ErrorCodeConstants.KEFU_CONVERSATION_NOT_EXISTS;
import static cn.metast.tuoke.module.promotion.enums.WebSocketMessageTypeConstants.KEFU_MESSAGE_ADMIN_READ;
import static cn.metast.tuoke.module.promotion.enums.WebSocketMessageTypeConstants.KEFU_MESSAGE_TYPE;

/**
 * 客服消息 Service 实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class KeFuMessageServiceImpl implements KeFuMessageService {

    @Resource
    private KeFuMessageMapper keFuMessageMapper;
    @Resource
    private KeFuConversationService conversationService;
    @Resource
    private KeFuConversationMapper conversationMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private WebSocketSenderApi webSocketSenderApi;
    @Resource
    private TopicApi topicApi;
    @Resource
    private BlockApi blockApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long sendKefuMessage(KeFuMessageSendReqVO sendReqVO) {
        // 1.1 校验会话是否存在
        KeFuConversationDO conversation = conversationService.validateKefuConversationExists(sendReqVO.getConversationId());
        // 1.2 校验接收人是否存在
        validateReceiverExist(conversation.getUserId(), UserTypeEnum.MEMBER.getValue());

        // 2.1 保存消息
        KeFuMessageDO kefuMessage = BeanUtils.toBean(sendReqVO, KeFuMessageDO.class);
        kefuMessage.setReceiverId(conversation.getUserId()).setReceiverType(UserTypeEnum.MEMBER.getValue()); // 设置接收人
        keFuMessageMapper.insert(kefuMessage);
        // 2.2 更新会话消息冗余
        kefuMessage.setReceiverId(SecurityFrameworkUtils.getLoginUserId());
        conversationService.updateConversationLastMessage(kefuMessage);

        // 3.1 发送消息给会员
        AdminUserRespDTO user = adminUserApi.getUser(kefuMessage.getSenderId());
        KeFuMessageRespVO message = BeanUtils.toBean(kefuMessage, KeFuMessageRespVO.class).setSenderAvatar(user.getAvatar());
        getSelf().sendAsyncMessageToMember(conversation.getUserId(), KEFU_MESSAGE_TYPE, message);
        // 3.2 通知所有管理员更新对话
        getSelf().sendAsyncMessageToAdmin(KEFU_MESSAGE_TYPE, message);
        return kefuMessage.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long sendKeFuMessageByOrder(KeFuMessageSendNewReqVO sendReqVO2) {
        // 1.1 设置会话编号
        KeFuConversationDO conversation2 = conversationService.getOrCreateConversation(sendReqVO2.getUserId());
        sendReqVO2.setConversationId(conversation2.getId());

        KeFuMessageSendReqVO sendReqVO = BeanUtils.toBean(sendReqVO2, KeFuMessageSendReqVO.class);

        // 1.1 校验会话是否存在
        KeFuConversationDO conversation = conversationService.validateKefuConversationExists(sendReqVO.getConversationId());
        // 1.2 校验接收人是否存在
        validateReceiverExist(conversation.getUserId(), UserTypeEnum.MEMBER.getValue());

        // 2.1 保存消息
        KeFuMessageDO kefuMessage = BeanUtils.toBean(sendReqVO, KeFuMessageDO.class);
        kefuMessage.setReceiverId(conversation.getUserId()).setReceiverType(UserTypeEnum.MEMBER.getValue()); // 设置接收人
        keFuMessageMapper.insert(kefuMessage);
        // 2.2 更新会话消息冗余
        conversationService.updateConversationLastMessage(kefuMessage);

        // 3.1 发送消息给会员
        getSelf().sendAsyncMessageToMember(conversation.getUserId(), KEFU_MESSAGE_TYPE, kefuMessage);
        // 3.2 通知所有管理员更新对话
        getSelf().sendAsyncMessageToAdmin(KEFU_MESSAGE_TYPE, kefuMessage);
        return kefuMessage.getId();
    }

    @Override
    public Long sendKefuMessage(AppKeFuMessageSendReqVO sendReqVO) {
        // 1.1 设置会话编号
        KeFuMessageDO kefuMessage = BeanUtils.toBean(sendReqVO, KeFuMessageDO.class);
        KeFuConversationDO conversation = conversationService.getOrCreateConversation(sendReqVO.getSenderId());
        kefuMessage.setConversationId(conversation.getId());
        // 1.2 保存消息
        keFuMessageMapper.insert(kefuMessage);

        // 2. 更新会话消息冗余
        conversationService.updateConversationLastMessage(kefuMessage);
        // 3. 通知所有管理员更新对话
        MemberUserRespDTO user = memberUserApi.getUser(kefuMessage.getSenderId());
        KeFuMessageRespVO message = BeanUtils.toBean(kefuMessage, KeFuMessageRespVO.class).setSenderAvatar(user.getAvatar());
        getSelf().sendAsyncMessageToAdmin(KEFU_MESSAGE_TYPE, message);
        getSelf().sendLxMessageByHttp(message);
        return kefuMessage.getId();
    }

    @Override
    public Long sendKefuMessageTopic(AppKeFuMessageSendReqVO sendReqVO) {
        // 1.1 设置会话编号
        KeFuMessageDO kefuMessage = BeanUtils.toBean(sendReqVO, KeFuMessageDO.class);
        /*KeFuConversationDO conversation;
        if(sendReqVO.getReceiverId() !=  null){
            conversation = conversationService.getOrCreateConversation(sendReqVO.getSenderId(), sendReqVO.getReceiverId());
        }else{
            conversation = conversationService.getOrCreateConversation(sendReqVO.getSenderId());
        }*/

        // 如果有引用内容，仅校验当前发送人是否为圈主
        /*if (sendReqVO.getQuoteContent() != null) {
            if (!topicApi.isTopicCreator(sendReqVO.getSenderId(), kefuMessage.getTopicId())) {
                throw exception(CM_MESSAGE_QUOTE_NO_PERMISSION);
            }
            // quoteUserId 由前端传入，表示被引用消息的发送人 ID
        }*/

        //聊天室消息
        if(kefuMessage.getChatType()!=null && kefuMessage.getChatType()==1){
            //判断发送人是否是管理员
            Long userId=topicApi.getTopicUserId(kefuMessage.getTopicId());
            if(!kefuMessage.getSenderId().equals(userId)){
                kefuMessage.setStatus(1);//待审核
            }

        }
        kefuMessage.setConversationId(sendReqVO.getConversationId());
        // 1.2 保存消息
        keFuMessageMapper.insert(kefuMessage);

        // 2. 更新会话消息冗余
        conversationService.updateConversationLastMessage(kefuMessage);
        // 3. 通知所有管理员更新对话
        if(sendReqVO.getReceiverId() != null){
            if(sendReqVO.getReceiverId().toString().contains("520")){
                getSelf().sendAsyncMessageToGroup(KEFU_MESSAGE_TYPE, kefuMessage);
            }else {
                kefuMessage.setSenderType(2);
                getSelf().sendAsyncMessageToMember(sendReqVO.getReceiverId(), KEFU_MESSAGE_TYPE, kefuMessage);
            }
        }else {
            getSelf().sendAsyncMessageToAdmin(KEFU_MESSAGE_TYPE, kefuMessage);
        }
        return kefuMessage.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateKeFuMessageReadStatus(Long conversationId, Long userId, Integer userType) {
        // 1.1 校验会话是否存在
        KeFuConversationDO conversation = conversationService.validateKefuConversationExists(conversationId);
        // 1.2 如果是会员端处理已读，需要传递 userId；万一用户模拟一个 conversationId
        if (UserTypeEnum.MEMBER.getValue().equals(userType) && ObjUtil.notEqual(conversation.getUserId(), userId)) {
            throw exception(KEFU_CONVERSATION_NOT_EXISTS);
        }
        // 1.3 查询会话所有的未读消息 (tips: 多个客服，一个人点了，就都点了)
        List<KeFuMessageDO> messageList = keFuMessageMapper.selectListByConversationIdAndUserTypeAndReadStatus(conversationId, userType, Boolean.FALSE);
        if (CollUtil.isEmpty(messageList)) {
            return;
        }

        // 2.1 情况二：更新未读消息状态为已读
        keFuMessageMapper.updateReadStatusBatchByIds(convertSet(messageList, KeFuMessageDO::getId),
                new KeFuMessageDO().setReadStatus(Boolean.TRUE));
        // 2.2 将管理员未读消息计数更新为零
        conversationService.updateAdminUnreadMessageCountToZero(conversationId);

        // 2.3 发送消息通知会员，管理员已读 -> 会员更新发送的消息状态
        KeFuMessageDO keFuMessage = getFirst(filterList(messageList, message -> UserTypeEnum.MEMBER.getValue().equals(message.getSenderType())));
        assert keFuMessage != null; // 断言避免警告
        getSelf().sendAsyncMessageToMember(keFuMessage.getSenderId(), KEFU_MESSAGE_ADMIN_READ,
                new KeFuMessageRespVO().setConversationId(keFuMessage.getConversationId()));
        // 2.4 通知所有管理员消息已读
        getSelf().sendAsyncMessageToAdmin(KEFU_MESSAGE_ADMIN_READ,
                new KeFuMessageRespVO().setConversationId(keFuMessage.getConversationId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateKeFuMessageReadStatusTopic(Long conversationId, Long userId, Integer userType) {
        // 1.1 校验会话是否存在
        // KeFuConversationDO conversation = conversationService.validateKefuConversationExists(conversationId);
        // 1.2 如果是会员端处理已读，需要传递 userId；万一用户模拟一个 conversationId
       /* if (UserTypeEnum.MEMBER.getValue().equals(userType) && ObjUtil.notEqual(conversation.getUserId(), userId)) {
            throw exception(KEFU_CONVERSATION_NOT_EXISTS);
        }*/
        // 1.3 查询会话所有的未读消息 (tips: 多个客服，一个人点了，就都点了)
        List<KeFuMessageDO> messageList = keFuMessageMapper.selectListByConversationIdAndUserTypeAndReadStatusNew(conversationId, userType,userId, Boolean.FALSE);
        if (CollUtil.isEmpty(messageList)) {
            return;
        }

        // 2.1 情况二：更新未读消息状态为已读
        keFuMessageMapper.updateReadStatusBatchByIds(convertSet(messageList, KeFuMessageDO::getId),
                new KeFuMessageDO().setReadStatus(Boolean.TRUE));
        // 2.2 将管理员未读消息计数更新为零
        conversationService.updateAdminUnreadMessageCountToZero(conversationId);

        // 2.3 发送消息通知会员，管理员已读 -> 会员更新发送的消息状态
        KeFuMessageDO keFuMessage = getFirst(filterList(messageList, message -> UserTypeEnum.MEMBER.getValue().equals(message.getSenderType())));
        assert keFuMessage != null; // 断言避免警告
        getSelf().sendAsyncMessageToMember(keFuMessage.getSenderId(), KEFU_MESSAGE_ADMIN_READ,
                new KeFuMessageRespVO().setConversationId(keFuMessage.getConversationId()));
        // 2.4 通知所有管理员消息已读
        getSelf().sendAsyncMessageToAdmin(KEFU_MESSAGE_ADMIN_READ,
                new KeFuMessageRespVO().setConversationId(keFuMessage.getConversationId()));
    }

    @Override
    public void updateKeFuMessageStatus(Long id, Integer status) {
        keFuMessageMapper.updateById(new KeFuMessageDO().setId(id).setStatus(status));
    }

    private void validateReceiverExist(Long receiverId, Integer receiverType) {
        if (UserTypeEnum.ADMIN.getValue().equals(receiverType)) {
            adminUserApi.validateUser(receiverId);
        }
        if (UserTypeEnum.MEMBER.getValue().equals(receiverType)) {
            memberUserApi.validateUser(receiverId);
        }
    }

    @Async
    public void sendAsyncMessageToMember(Long userId, String messageType, Object content) {
        webSocketSenderApi.sendObject(UserTypeEnum.MEMBER.getValue(), userId, messageType, content);
    }
    @Async
    public void sendAsyncMessageToGroup(String messageType, Object content) {
        webSocketSenderApi.sendObject(UserTypeEnum.MEMBER.getValue(), messageType, content);
    }

    @Async
    public void sendAsyncMessageToAdmin(String messageType, Object content) {
        webSocketSenderApi.sendObject(UserTypeEnum.ADMIN.getValue(), messageType, content);
    }

    @Override
    public List<KeFuMessageDO> getKeFuMessageList(KeFuMessageListReqVO pageReqVO) {
        return keFuMessageMapper.selectList(pageReqVO);
    }

    @Override
    public List<KeFuMessageDO> getKeFuMessageList(AppKeFuMessagePageReqVO pageReqVO, Long userId) {
        // 1. 获得客服会话
        KeFuConversationDO conversation = conversationService.getConversationByUserId(userId);
        if (conversation == null) {
            return Collections.emptyList();
        }
        // 2. 设置会话编号
        pageReqVO.setConversationId(conversation.getId());
        return keFuMessageMapper.selectList(BeanUtils.toBean(pageReqVO, KeFuMessageListReqVO.class));
    }

    @Override
    public List<KeFuMessageDO> getKeFuMessageList(AppKeFuMessagePageReqVO pageReqVO, Long userId, Long receiverId) {
        /*if(pageReqVO.getConversationId() == null){
            // 1. 获得客服会话
            KeFuConversationDO conversation = conversationService.getOrCreateConversation(userId, receiverId,null);
            if (conversation == null) {
                return Collections.emptyList();
            }
            // 2. 设置会话编号
            pageReqVO.setConversationId(conversation.getId());
        }*/
        pageReqVO.setSendId(userId);
        return keFuMessageMapper.selectListTopic(BeanUtils.toBean(pageReqVO, KeFuMessageListReqVO.class));
    }

    @Override
    public PageResult<KeFuMessageDO> getKeFuMessageListPage(AppKeFuMessagePageReqVO pageReqVO) {
        return keFuMessageMapper.getKeFuMessageListPage(pageReqVO);
    }

    @Override
    public PageResult<KeFuMessageDO> getKeFuMessageListNewPage(AppKeFuMessagePageReqVO pageReqVO) {
        // 获取当前用户在该圈子中拉黑的用户ID列表
        if (pageReqVO.getTopicId() != null && pageReqVO.getSendId() != null) {
            List<Long> blockUserIds = blockApi.getBlockUserIds(pageReqVO.getSendId(), pageReqVO.getTopicId());
            pageReqVO.setBlockUserIds(blockUserIds);
        }
        return keFuMessageMapper.getKeFuMessageListNewPage(pageReqVO);
    }

    @Override
    public Long getKeFuMessageListTopicId(Long topicId) {
        Long loginUserId = getLoginUserId();
        LambdaQueryWrapper<KeFuMessageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.apply("conversation_id IN (SELECT id FROM promotion_kefu_conversation WHERE topic_id = {0} AND group_status = 0)", topicId);
        wrapper.eq(KeFuMessageDO::getReceiverId, loginUserId);
        wrapper.eq(KeFuMessageDO::getReadStatus, 0);
        return keFuMessageMapper.selectCount(wrapper);
    }

    @Override
    public void withdrawMessage(Long messageId, Long topicId, Long operatorId) {
        // 1. 校验是否为圈主
        if (!topicApi.isTopicCreator(operatorId, topicId)) {
            throw exception(CM_MESSAGE_WITHDRAW_NO_PERMISSION);
        }
        // 2. 校验消息是否存在
        KeFuMessageDO message = keFuMessageMapper.selectById(messageId);
        if (message == null) {
            throw exception(CM_MESSAGE_NOT_EXISTS);
        }
        // 3. 逻辑删除消息
        keFuMessageMapper.deleteById(messageId);
    }


    private KeFuMessageServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

    /**
     * 接收消息发送http
     * @param message
     */
    public void sendLxMessageByHttp(KeFuMessageRespVO message) {
        //获取商城用户对应的分配客户Id
        String kefuId = null;
        Long senderId = message.getSenderId();
        MemberUserRespDTO user = memberUserApi.getUser(senderId);
        if(ObjectUtil.isNotEmpty(user) && StringUtils.isNotEmpty(user.getKefuId())){
            //获取固定客服
            kefuId = user.getKefuId();
        }else{
            //获取会话客服
            KeFuConversationDO conversation = conversationService.getConversation(message.getConversationId());
            if(ObjectUtil.isNotEmpty(conversation) && ObjectUtil.isNotEmpty(conversation.getReceiverId())){
                kefuId = conversation.getReceiverId().toString();
            }else{
                //获取在线客服
//                List<Long> sessionList = webSocketSenderApi.getSessionList();
//                if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(sessionList)){
//                    // 随机选择一个在线客服
//                    int randomIndex = new java.util.Random().nextInt(sessionList.size());
//                    kefuId = String.valueOf(sessionList.get(randomIndex));
//                }else{
                //获取固定用户
                List<AdminUserRespDTO> userList = adminUserApi.getUserList();
                if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(userList)){
                    kefuId = userList.get(0).getId().toString();
                }
//                }
                if (ObjectUtil.isNotEmpty(conversation) && StringUtils.isNotEmpty(kefuId)){
                    conversation.setReceiverId(Long.parseLong(kefuId));
                    conversationMapper.updateById(conversation);
                }
            }
        }
        if (StringUtils.isNotEmpty(kefuId)){
            message.setReceiverId(Long.parseLong(kefuId));
            //发送消息
            adminUserApi.sendMessage(JSONObject.toJSONString(message), Long.parseLong(kefuId));
        }
    }
}
