package cn.metast.tuoke.module.promotion.service.kefu;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageListReqVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageSendNewReqVO;
import cn.metast.tuoke.module.promotion.controller.admin.kefu.vo.message.KeFuMessageSendReqVO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessagePageReqVO;
import cn.metast.tuoke.module.promotion.controller.app.kefu.vo.message.AppKeFuMessageSendReqVO;
import cn.metast.tuoke.module.promotion.dal.dataobject.kefu.KeFuMessageDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 客服消息 Service 接口
 *
 * @author HUIHUI
 */
public interface KeFuMessageService {

    /**
     * 【管理员】发送客服消息
     *
     * @param sendReqVO 信息
     * @return 编号
     */
    Long sendKefuMessage(@Valid KeFuMessageSendReqVO sendReqVO);
    Long sendKeFuMessageByOrder(@Valid KeFuMessageSendNewReqVO sendReqVO);
    /**
     * 【会员】发送客服消息
     *
     * @param sendReqVO 信息
     * @return 编号
     */
    Long sendKefuMessage(AppKeFuMessageSendReqVO sendReqVO);

    Long sendKefuMessageTopic(AppKeFuMessageSendReqVO sendReqVO);
    /**
     * 【管理员】更新消息已读状态
     *
     * @param conversationId 会话编号
     * @param userId         用户编号
     * @param userType       用户类型
     */
    void updateKeFuMessageReadStatus(Long conversationId, Long userId, Integer userType);

    void updateKeFuMessageReadStatusTopic(Long conversationId, Long userId, Integer userType);


    void updateKeFuMessageStatus(Long id, Integer status);

    /**
     * 获得客服消息分页
     *
     * @param pageReqVO 分页查询
     * @return 客服消息分页
     */
    List<KeFuMessageDO> getKeFuMessageList(KeFuMessageListReqVO pageReqVO);

    /**
     * 【会员】获得客服消息分页
     *
     * @param pageReqVO 请求
     * @param userId    用户编号
     * @return 客服消息分页
     */
    List<KeFuMessageDO> getKeFuMessageList(AppKeFuMessagePageReqVO pageReqVO, Long userId);

    List<KeFuMessageDO> getKeFuMessageList(AppKeFuMessagePageReqVO pageReqVO, Long userId, Long receiverId);

    PageResult<KeFuMessageDO> getKeFuMessageListPage(AppKeFuMessagePageReqVO pageReqVO);

    PageResult<KeFuMessageDO> getKeFuMessageListNewPage(AppKeFuMessagePageReqVO pageReqVO);

    //获取总未读数
    Long getKeFuMessageListTopicId(Long topicId);

    /**
     * 圈主撤回消息（逻辑删除）
     *
     * @param messageId 消息ID
     * @param topicId 圈子ID
     * @param operatorId 操作人ID
     */
    void withdrawMessage(Long messageId, Long topicId, Long operatorId);
}
