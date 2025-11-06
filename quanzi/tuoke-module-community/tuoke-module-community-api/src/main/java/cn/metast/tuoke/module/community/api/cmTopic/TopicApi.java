package cn.metast.tuoke.module.community.api.cmTopic;

import cn.metast.tuoke.module.community.api.cmTopic.dto.TopicMemberRespDTO;

import java.util.List;

/**
 * 会员用户的 API 接口
 *
 * @author metast.cn
 */
public interface TopicApi {

    /**
     * 获取创建人id
     *
     */
    Long getTopicUserId(Long topicId);

    /**
     * 根据用户ID列表批量获取圈子成员信息
     *
     * @param userIds 用户ID列表
     * @return 圈子成员信息列表
     */
    List<TopicMemberRespDTO> getTopicMemberListByUserIds(List<Long> userIds);

    /**
     * 判断用户在指定圈子中是否为续约客户
     *
     * @param userId 用户ID
     * @param topicId 圈子ID
     * @param currentMemberId 当前成员记录ID
     * @return true-续约客户，false-首次购买
     */
    Boolean isRenewalCustomer(Long userId, Long topicId, Long currentMemberId);
    /**
     * 判断用户是否为圈子创建者
     *
     * @param userId 用户ID
     * @param topicId 圈子ID
     * @return true-圈主，false-非圈主
     */
    Boolean isTopicCreator(Long userId, Long topicId);
}
