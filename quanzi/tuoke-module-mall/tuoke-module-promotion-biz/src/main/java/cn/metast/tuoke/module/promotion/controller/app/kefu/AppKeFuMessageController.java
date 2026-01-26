package cn.metast.tuoke.module.promotion.controller.app.kefu;

import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.api.cmTopic.TopicApi;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.conversation.KeFuConversationRespVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageRespVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageSendReqVO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessagePageReqVO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessageSendReqVO;
import cn.metast.tuoke.module.promotion.dal.dataobject.kefu.KeFuConversationDO;
import cn.metast.tuoke.module.promotion.dal.dataobject.kefu.KeFuMessageDO;
import cn.metast.tuoke.module.promotion.dal.mysql.kefu.KeFuMessageMapper;
import cn.metast.tuoke.module.promotion.service.kefu.KeFuConversationService;
import cn.metast.tuoke.module.promotion.service.kefu.KeFuMessageService;
import cn.metast.tuoke.module.system.api.user.AdminUserApi;
import cn.metast.tuoke.module.system.api.user.dto.AdminUserRespDTO;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.*;
import static cn.metast.tuoke.framework.common.util.collection.MapUtils.findAndThen;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 客服消息")
@RestController
@RequestMapping("/promotion/kefu-message")
@Validated
public class AppKeFuMessageController {

    @Resource
    private KeFuMessageService kefuMessageService;

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private KeFuMessageMapper keFuMessageMapper;
    @Resource
    private KeFuConversationService conversationService;
    @Resource
    private TopicApi topicApi;
    @Resource
    private MemberUserApi memberUserApi;
    @PostMapping("/send")
    @Operation(summary = "发送客服消息")
    public CommonResult<Long> sendKefuMessage(@Valid @RequestBody AppKeFuMessageSendReqVO sendReqVO) {
        sendReqVO.setSenderId(getLoginUserId()).setSenderType(UserTypeEnum.MEMBER.getValue()); // 设置用户编号和类型
        return success(kefuMessageService.sendKefuMessage(sendReqVO));
    }

    @GetMapping("/kefuList")
    @Operation(summary = "查询所有客服")
    public CommonResult<MemberUserRespDTO> kefuList() {
        //先默认管理员-客服
        MemberUserRespDTO memberUserRespDTO=memberUserApi.getUser(1971142278074519556L);
        return success(memberUserRespDTO);
    }

    @GetMapping("/getKefuList")
    @Operation(summary = "查询会话客服列表")
    public CommonResult<List<KeFuConversationDO>> getKefuList(@RequestParam("userId") Long userId) {
        List<KeFuConversationDO> conversationList = conversationService.getConversationListByUserId(userId);
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(conversationList)){
            for (KeFuConversationDO keFuConversationDO : conversationList) {
                Long receiverId = keFuConversationDO.getReceiverId();
                if(ObjectUtil.isNotEmpty(receiverId)){
                    AdminUserRespDTO user = adminUserApi.getUser(receiverId);
                    if(ObjectUtil.isNotEmpty(user)){
                        keFuConversationDO.setReceiverUser(user);
                    }
                }
            }
            return success(conversationList);
        }
        return success(new ArrayList<>());
    }

    @PostMapping("/sendByadmin")
    @Operation(summary = "发送客服消息")
    public CommonResult<Long> sendKefuMessageByadmin(@Valid @RequestBody KeFuMessageSendReqVO sendReqVO) {
        sendReqVO.setSenderId(142L).setSenderType(UserTypeEnum.ADMIN.getValue()); // 设置用户编号和类型
        return success(kefuMessageService.sendKefuMessage(sendReqVO));
    }

    @PutMapping("/update-read-status")
    @Operation(summary = "更新客服消息已读状态")
    @Parameter(name = "conversationId", description = "会话编号", required = true)
    public CommonResult<Boolean> updateKefuMessageReadStatus(@RequestParam("conversationId") Long conversationId) {
        kefuMessageService.updateKeFuMessageReadStatus(conversationId, getLoginUserId(), UserTypeEnum.MEMBER.getValue());
        return success(true);
    }

    @PutMapping("/update-read-status-admin")
    @Operation(summary = "更新客服消息已读状态")
    @Parameter(name = "conversationId", description = "会话编号", required = true)
    public CommonResult<Boolean> updateKefuMessageReadStatusAdmin(@RequestParam("conversationId") Long conversationId) {
        kefuMessageService.updateKeFuMessageReadStatus(conversationId, getLoginUserId(), UserTypeEnum.ADMIN.getValue());
        return success(true);
    }

    @GetMapping("/conversationList")
    @Operation(summary = "获得客服会话列表")
    public CommonResult<List<KeFuConversationRespVO>> getConversationList(String kfAdmin) {
        Long loginUserId = getLoginUserId();
        // 查询会话列表
        List<KeFuConversationDO> kefuConversationList_admin = conversationService.getKefuConversationList_admin();
        List<KeFuConversationDO> kefuConversationList_web = conversationService.getKefuConversationList_web(loginUserId);
        List<KeFuConversationRespVO> respList = BeanUtils.toBean("1".equals(kfAdmin) ? kefuConversationList_admin : kefuConversationList_web,
                KeFuConversationRespVO.class);

        for(KeFuConversationRespVO vo : respList){
            vo.setAdminUnreadMessageCount(0);
            if(!"1".equals(kfAdmin) && vo.getUserId().equals(loginUserId)){
                Long userId = vo.getUserId();
                Long receiverId = vo.getReceiverId();
                vo.setUserId(receiverId);
                vo.setReceiverId(userId);
            }

            LambdaQueryWrapperX<KeFuMessageDO> kfWrapper = new LambdaQueryWrapperX<KeFuMessageDO>()
                    .eq(KeFuMessageDO::getConversationId, vo.getId())
                    .eq(KeFuMessageDO::getReadStatus, false)
                    .eqIfPresent(KeFuMessageDO::getReceiverId, "1".equals(kfAdmin) ? null:loginUserId)
                    .eqIfPresent(KeFuMessageDO::getSenderType, "1".equals(kfAdmin) ? 1:null);
            Long aLong = keFuMessageMapper.selectCount(kfWrapper);
            vo.setAdminUnreadMessageCount(aLong.intValue());
        }

        // 拼接数据
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(respList, KeFuConversationRespVO::getUserId));
        respList.forEach(item-> findAndThen(userMap, item.getUserId(),
                memberUser-> item.setUserAvatar(memberUser.getAvatar()).setUserNickname(StringUtils.isNotBlank(memberUser.getName())?memberUser.getName() : memberUser.getNickname())));
        return success(respList);
    }

    @GetMapping("/list")
    @Operation(summary = "获得客服消息列表")
    public CommonResult<List<KeFuMessageRespVO>> getKefuMessageList(@Valid AppKeFuMessagePageReqVO pageReqVO) {
        List<KeFuMessageDO> list = kefuMessageService.getKeFuMessageList(pageReqVO, getLoginUserId());

        // 拼接数据
        List<KeFuMessageRespVO> result = BeanUtils.toBean(list, KeFuMessageRespVO.class);
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertSet(filterList(result,
                item -> UserTypeEnum.ADMIN.getValue().equals(item.getSenderType())), KeFuMessageRespVO::getSenderId));
        result.forEach(item -> findAndThen(userMap, item.getSenderId(), user -> item.setSenderAvatar(user.getAvatar())));
        return success(result);
    }

    @GetMapping("/memberList")
    @Operation(summary = "获得用户消息列表")
    public CommonResult<List<KeFuMessageRespVO>> getKefuMemberMessageList(@Valid AppKeFuMessagePageReqVO pageReqVO) {

//        if(pageReqVO.getReceiverId() == null){
//            return success(new ArrayList<>());
//        }
        Long loginUserId = getLoginUserId();
        List<KeFuMessageDO> list = kefuMessageService.getKeFuMessageList(pageReqVO, getLoginUserId(), pageReqVO.getReceiverId());
        for(KeFuMessageDO item : list){
            if(item.getSenderId().equals(loginUserId)){
                item.setSenderType(1);
            }else{
                item.setSenderType(2);
            }
        }

        // 拼接数据
        List<KeFuMessageRespVO> result = BeanUtils.toBean(list, KeFuMessageRespVO.class);
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertSet(filterList(result,
                item -> UserTypeEnum.ADMIN.getValue().equals(item.getSenderType())), KeFuMessageRespVO::getSenderId));
        result.forEach(item -> findAndThen(userMap, item.getSenderId(), user -> item.setSenderAvatar(user.getAvatar())));
        return success(result);
    }

    @GetMapping("/getConversation")
    @Operation(summary = "获得用户消息列表")
    public CommonResult<Long> getConversation(@Valid AppKeFuMessagePageReqVO pageReqVO) {
        if (pageReqVO.getReceiverId() == null){
            return success(0L);
        }
        Long loginUserId = getLoginUserId();
        // 获得客服会话
        KeFuConversationDO conversation = conversationService.getOrCreateConversation(loginUserId, pageReqVO.getReceiverId(),null);
        return success(conversation.getId());
    }



    /**
     * 获取聊天室组会话conversionId,默认接受人是组用户
     */
    @GetMapping("/getTopicGroupConversionId")
    @Operation(summary = "getTopicGroupConversionId")
    public CommonResult<Map<String,Object>> getTopicGroupConversionId(@RequestParam("topicId") Long topicId) {
        //默认组id唯一
        Long userId=topicApi.getTopicUserId(topicId);
        Long receiverId = Long.parseLong(topicId.toString() + "520");
        KeFuConversationDO conversation = conversationService.getOrCreateConversationGroup(userId, receiverId);
        if (conversation == null) {
            return null;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("conversationId",conversation.getId());
        map.put("receiverId",receiverId);
        return success(map);
    }


    /**
     * 获取圈子总未读数
     */
    @PutMapping("/getCountNum")
    @Operation(summary = "查询总未读数")
    public CommonResult<Long> getCountNum(@RequestParam("topicId") Long topicId) {
        if (topicId == null){
            return success(0L);
        }
        Long count=kefuMessageService.getKeFuMessageListTopicId(topicId);
        return success(count);
    }

    /**
     * 圈子已读状态
     */
    @PutMapping("/update-read-status-topic")
    @Operation(summary = "更新客服消息已读状态")
    @Parameter(name = "conversationId", description = "会话编号", required = true)
    public CommonResult<Boolean> updateKefuMessageReadStatusTopic(@RequestParam("conversationId") Long conversationId) {
        kefuMessageService.updateKeFuMessageReadStatusTopic(conversationId, getLoginUserId(), UserTypeEnum.ADMIN.getValue());
        return success(true);
    }

    /**
     * 圈子聊天室审核列表总数
     */
    @GetMapping("/getTopicMessageCheckCount")
    @Operation(summary = "聊天室总数")
    public CommonResult<Integer> getTopicMessageCheckCount(@Valid AppKeFuMessagePageReqVO pageReqVO) {
        if(pageReqVO.getTopicId()==null){
            return success(0);
        }
        pageReqVO.setStatus(1);
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<KeFuMessageDO> pageResult = kefuMessageService.getKeFuMessageListPage(pageReqVO).getList();
        return success(org.apache.commons.collections4.CollectionUtils.isNotEmpty(pageResult)?pageResult.size():0);
    }
    /**
     * 圈子聊天室审核列表
     */
    @GetMapping("/getTopicMessageCheck")
    @Operation(summary = "圈子会话消息")
    public CommonResult<PageResult<KeFuMessageRespVO>> getTopicMessageCheck(@Valid AppKeFuMessagePageReqVO pageReqVO) {
        PageResult<KeFuMessageDO> pageResult = kefuMessageService.getKeFuMessageListPage(pageReqVO);
        // 拼接结果返回
        List<MemberUserRespDTO> users = memberUserApi.getUserList(
                convertSet(pageResult.getList(), KeFuMessageDO::getSenderId));
        // user 拼接
        Map<Long, MemberUserRespDTO> userMap = convertMap(users, MemberUserRespDTO::getId);

        List<MemberUserRespDTO> users2 = memberUserApi.getUserList(
                convertSet(pageResult.getList(), KeFuMessageDO::getReceiverId));
        Map<Long, MemberUserRespDTO> userMap2 = convertMap(users2, MemberUserRespDTO::getId);
        pageResult.getList().forEach(itemRespVO -> {
             // 获取用户信息
            MemberUserRespDTO memberUserRespDTO = userMap.get(itemRespVO.getSenderId());
            if (memberUserRespDTO != null) {
                itemRespVO.setSenderAvatar(memberUserRespDTO.getAvatar());
                itemRespVO.setSenderName(memberUserRespDTO.getNickname());
            }
            // 获取用户信息
            MemberUserRespDTO memberUserRespDTO2 = userMap2.get(itemRespVO.getReceiverId());
            if (memberUserRespDTO2 != null) {
                itemRespVO.setReceiverAvatar(memberUserRespDTO.getAvatar());
                itemRespVO.setReceiverName(memberUserRespDTO.getNickname());
            }
        });
        return success(BeanUtils.toBean(pageResult, KeFuMessageRespVO.class));
    }

    /**
     * 圈子聊天室审核状态
     */
    @GetMapping("/topicMessageCheckStatus")
    @Operation(summary = "圈子会话消息审核")
    public CommonResult<Boolean> topicMessageCheckStatus(@RequestParam("id") Long id,@RequestParam("status") Integer status) {
        kefuMessageService.updateKeFuMessageStatus(id,status);
        return success(true);
    }





    /**
     * 发送消息圈子消息
     * @param sendReqVO
     * @return
     */
    @PostMapping("/sendTopic")
    @Operation(summary = "发送客服消息")
    public CommonResult<Long> sendTopic(@Valid @RequestBody AppKeFuMessageSendReqVO sendReqVO) {
        sendReqVO.setSenderId(getLoginUserId()).setSenderType(UserTypeEnum.MEMBER.getValue()); // 设置用户编号和类型
        return success(kefuMessageService.sendKefuMessageTopic(sendReqVO));
    }
    /**
     * 圈子会话消息-如果没有插入一条
     */
    @GetMapping("/getTopicMessage")
    @Operation(summary = "圈子会话消息")
    public CommonResult<List<KeFuMessageRespVO>> getTopicMessage(@Valid AppKeFuMessagePageReqVO pageReqVO) {
        Long loginUserId = getLoginUserId();
        pageReqVO.setSendId(loginUserId);
        PageResult<KeFuMessageDO> pageResult = kefuMessageService.getKeFuMessageListNewPage(pageReqVO);
        //List<KeFuMessageDO> list = kefuMessageService.getKeFuMessageList(pageReqVO, loginUserId, pageReqVO.getReceiverId());
        for(KeFuMessageDO item : pageResult.getList()){
            if(item.getSenderId().equals(loginUserId)){
                item.setSenderType(1);
            }else{
                item.setSenderType(2);
            }
        }
        // 拼接数据
        List<KeFuMessageRespVO> result = BeanUtils.toBean(pageResult.getList(), KeFuMessageRespVO.class);
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(result, KeFuMessageRespVO::getSenderId));
        result.forEach(item-> findAndThen(userMap, item.getSenderId(),
                memberUser-> item.setSenderAvatar(memberUser.getAvatar()).setSenderName(memberUser.getNickname())));

        Map<Long, MemberUserRespDTO> userMap1 = memberUserApi.getUserMap(convertSet(result, KeFuMessageRespVO::getReceiverId));
        result.forEach(item-> findAndThen(userMap1, item.getSenderId(),
                memberUser-> item.setReceiverAvatar(memberUser.getAvatar()).setReceiverName(memberUser.getNickname())));

        // 被引用人信息
        Set<Long> quoteUserIds = convertSet(result.stream()
                .filter(item -> item.getQuoteUserId() != null)
                .collect(Collectors.toList()), KeFuMessageRespVO::getQuoteUserId);
        if (!quoteUserIds.isEmpty()) {
            Map<Long, MemberUserRespDTO> quoteUserMap = memberUserApi.getUserMap(quoteUserIds);
            result.forEach(item -> findAndThen(quoteUserMap, item.getQuoteUserId(),
                    memberUser -> item.setQuoteUserName(memberUser.getNickname())));
        }
        return success(result);
    }

    /**
     * 获取conversionId
     */
    @GetMapping("/getTopicConversionId")
    @Operation(summary = "获取conversionId")
    public CommonResult<Long> getTopicConversionId(@RequestParam("receiverId") Long receiverId,@RequestParam("topicId") Long topicId) {
        Long loginUserId = getLoginUserId();
        KeFuConversationDO conversation = conversationService.getOrCreateConversation(loginUserId, receiverId,topicId);
        if (conversation == null) {
            return null;
        }
        return success(conversation.getId());
    }

    /**
     * 判断当前登录用户在某圈子中是否为圈主
     */
    @GetMapping("/isTopicCreator")
    @Operation(summary = "判断当前用户是否为圈主")
    public CommonResult<Boolean> isTopicCreator(@RequestParam("topicId") Long topicId) {
        Long loginUserId = getLoginUserId();
        Boolean isCreator = topicApi.isTopicCreator(loginUserId, topicId);
        return success(isCreator);
    }

    /**
     * 圈子聊天会话
     */
    @PermitAll
    @GetMapping("/getTopicConversationList")
    @Operation(summary = "圈子聊天会话")
    public CommonResult<List<KeFuConversationRespVO>> getTopicConversationList(String kfAdmin) {
        Long loginUserId = getLoginUserId();
        // 查询会话列表
        List<KeFuConversationDO> kefuConversationList_admin = conversationService.getKefuConversationList_admin();
        List<KeFuConversationDO> kefuConversationList_web = conversationService.getKefuConversationList_web(loginUserId);
        List<KeFuConversationRespVO> respList = BeanUtils.toBean("1".equals(kfAdmin) ? kefuConversationList_admin : kefuConversationList_web,
                KeFuConversationRespVO.class);

        for(KeFuConversationRespVO vo : respList){
            vo.setAdminUnreadMessageCount(0);
            if(!"1".equals(kfAdmin) && vo.getUserId().equals(loginUserId)){
                Long userId = vo.getUserId();
                Long receiverId = vo.getReceiverId();
                vo.setUserId(receiverId);
                vo.setReceiverId(userId);
            }

            LambdaQueryWrapperX<KeFuMessageDO> kfWrapper = new LambdaQueryWrapperX<KeFuMessageDO>()
                    .eq(KeFuMessageDO::getConversationId, vo.getId())
                    .eq(KeFuMessageDO::getReadStatus, false)
                    .eqIfPresent(KeFuMessageDO::getReceiverId,loginUserId)
                    .eqIfPresent(KeFuMessageDO::getSenderType, 1);
            Long aLong = keFuMessageMapper.selectCount(kfWrapper);
            vo.setAdminUnreadMessageCount(aLong.intValue());
        }

        // 拼接数据
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(respList, KeFuConversationRespVO::getUserId));
        respList.forEach(item-> findAndThen(userMap, item.getUserId(),
                memberUser-> item.setUserAvatar(memberUser.getAvatar()).setUserNickname(StringUtils.isNotBlank(memberUser.getName())?memberUser.getName() : memberUser.getNickname())));
        return success(respList);
    }

    /**
     * 圈主撤回消息
     */
    @DeleteMapping("/withdraw")
    @Operation(summary = "圈主撤回消息")
    public CommonResult<Boolean> withdrawMessage(@RequestParam("messageId") Long messageId,
                                                 @RequestParam("topicId") Long topicId) {
        kefuMessageService.withdrawMessage(messageId, topicId, getLoginUserId());
        return success(true);
    }
}
