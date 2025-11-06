package cn.metast.tuoke.module.community.api.cmBlock;

import java.util.List;

/**
 * 拉黑记录 API 接口
 *
 * @author adminXq
 */
public interface BlockApi {

    /**
     * 查询用户在指定圈子中拉黑的用户ID列表
     *
     * @param userId  用户ID
     * @param topicId 圈子ID
     * @return 被拉黑的用户ID列表
     */
    List<Long> getBlockUserIds(Long userId, Long topicId);

}