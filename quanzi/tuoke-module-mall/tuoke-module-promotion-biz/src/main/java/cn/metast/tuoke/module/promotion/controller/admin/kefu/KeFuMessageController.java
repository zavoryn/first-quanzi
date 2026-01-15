package cn.metast.tuoke.module.promotion.controller.admin.kefu;

import cn.metast.tuoke.framework.common.enums.UserTypeEnum;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.community.api.cmTopic.TopicApi;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.conversation.KeFuConversationRespVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageListReqVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageRespVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageSendNewReqVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageSendReqVO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessagePageReqVO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.filterList;
import static cn.metast.tuoke.framework.common.util.collection.MapUtils.findAndThen;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 客服消息")
@RestController
@RequestMapping("/promotion/kefu-message")
@Validated
public class KeFuMessageController {
    @Resource
    private KeFuConversationService conversationService;
    @Resource
    private KeFuMessageService messageService;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private TopicApi topicApi;
    @Resource
    private KeFuMessageService kefuMessageService;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private KeFuMessageMapper keFuMessageMapper;

    @PostMapping("/send")
    @Operation(summary = "发送客服消息")
    @PreAuthorize("@ss.hasPermission('promotion:kefu-message:send')")
    public CommonResult<Long> sendKeFuMessage(@Valid @RequestBody KeFuMessageSendReqVO sendReqVO) {
        sendReqVO.setSenderId(getLoginUserId()).setSenderType(UserTypeEnum.ADMIN.getValue()); // 设置用户编号和类型
        return success(messageService.sendKefuMessage(sendReqVO));
    }

    @PostMapping("/lxSend")
    @Operation(summary = "发送客服消息")
    @PreAuthorize("@ss.hasPermission('promotion:kefu-message:send')")
    public CommonResult<Long> lxSendKeFuMessage(@Valid @RequestBody KeFuMessageSendReqVO sendReqVO) {
        KeFuConversationDO conversation = conversationService.getOrCreateConversation(sendReqVO.getRecvId());
        sendReqVO.setConversationId(conversation.getId());

        sendReqVO.setSenderId(getLoginUserId()).setSenderType(UserTypeEnum.ADMIN.getValue()); // 设置用户编号和类型
        return success(messageService.sendKefuMessage(sendReqVO));
    }

    @PostMapping("/sendByOrder")
    @Operation(summary = "发送客服消息 - 非客服中心页面调佣")
    public CommonResult<Long> sendKeFuMessageByOrder(@Valid @RequestBody KeFuMessageSendNewReqVO sendReqVO) {

        Long loginUserId = getLoginUserId();
        sendReqVO.setContentType(1);
        sendReqVO.setSenderId(loginUserId).setSenderType(UserTypeEnum.ADMIN.getValue()); // 设置用户编号和类型
        return success(messageService.sendKeFuMessageByOrder(sendReqVO));
    }

    @PutMapping("/update-read-status")
    @Operation(summary = "更新客服消息已读状态")
    @Parameter(name = "conversationId", description = "会话编号", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:kefu-message:update')")
    public CommonResult<Boolean> updateKeFuMessageReadStatus(@RequestParam("conversationId") Long conversationId) {
        messageService.updateKeFuMessageReadStatus(conversationId, getLoginUserId(), UserTypeEnum.ADMIN.getValue());
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得客服消息列表")
    @PreAuthorize("@ss.hasPermission('promotion:kefu-message:query')")
    public CommonResult<List<KeFuMessageRespVO>> getKeFuMessageList(@Valid KeFuMessageListReqVO pageReqVO) {
        // 获得数据
        List<KeFuMessageDO> list = messageService.getKeFuMessageList(pageReqVO);

        // 拼接数据
        List<KeFuMessageRespVO> result = BeanUtils.toBean(list, KeFuMessageRespVO.class);
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertSet(filterList(result,
                item -> UserTypeEnum.ADMIN.getValue().equals(item.getSenderType())), KeFuMessageRespVO::getSenderId));
        result.forEach(item -> findAndThen(userMap, item.getSenderId(), user -> item.setSenderAvatar(user.getAvatar())));
        return success(result);
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
        return success(result);
    }

    /**
     * 圈子聊天会话
     */
    @PermitAll
    @GetMapping("/getTopicConversationList")
    @Operation(summary = "圈子聊天会话")
    public CommonResult<List<KeFuConversationRespVO>> getTopicConversationList(@RequestParam("topicId") Long topicId,@RequestParam("nickName") String nickName) {
        // 查询会话列表
        List<KeFuConversationDO> kefuConversationList_web = conversationService.getKefuConversationListTopId(topicId,nickName);
        List<KeFuConversationRespVO> respList = BeanUtils.toBean(kefuConversationList_web,KeFuConversationRespVO.class);
        // 拼接数据
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(respList, KeFuConversationRespVO::getUserId));
        respList.forEach(item-> findAndThen(userMap, item.getUserId(),
                memberUser-> item.setUserAvatar(memberUser.getAvatar()).setUserNickname(StringUtils.isNotBlank(memberUser.getName())?memberUser.getName() : memberUser.getNickname())));
        return success(respList);
    }

}
