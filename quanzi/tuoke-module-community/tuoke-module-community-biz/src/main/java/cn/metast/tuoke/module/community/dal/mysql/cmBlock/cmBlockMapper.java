package cn.metast.tuoke.module.community.dal.mysql.cmBlock;

import java.util.*;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.module.community.dal.dataobject.cmBlock.cmBlockDO;
import org.apache.ibatis.annotations.Mapper;
import cn.metast.tuoke.module.community.controller.admin.cmBlock.vo.*;

/**
 * 拉黑记录 Mapper
 *
 * @author adminXq
 */
@Mapper
public interface cmBlockMapper extends BaseMapperX<cmBlockDO> {

    default PageResult<cmBlockDO> selectPage(cmBlockPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<cmBlockDO>()
                .eqIfPresent(cmBlockDO::getUserId, reqVO.getUserId())
                .eqIfPresent(cmBlockDO::getBlockUserId, reqVO.getBlockUserId())
                .eqIfPresent(cmBlockDO::getTopicId, reqVO.getTopicId())
                .betweenIfPresent(cmBlockDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(cmBlockDO::getId));
    }

    /**
     * 查询用户在指定圈子中拉黑的用户ID列表
     *
     * @param userId  用户ID
     * @param topicId 圈子ID
     * @return 被拉黑的用户ID列表
     */
    default List<Long> selectBlockUserIds(Long userId, Long topicId) {
        List<cmBlockDO> list = selectList(new LambdaQueryWrapperX<cmBlockDO>()
                .eq(cmBlockDO::getUserId, userId)
                .eq(cmBlockDO::getTopicId, topicId));
        List<Long> blockUserIds = new ArrayList<>();
        for (cmBlockDO block : list) {
            blockUserIds.add(block.getBlockUserId());
        }
        return blockUserIds;
    }

    /**
     * 校验是否已存在拉黑记录（去重校验）
     *
     * @param userId      用户ID
     * @param blockUserId 被拉黑用户ID
     * @param topicId     圈子ID
     * @return 是否已存在
     */
    default boolean existsBlock(Long userId, Long blockUserId, Long topicId) {
        return selectCount(new LambdaQueryWrapperX<cmBlockDO>()
                .eq(cmBlockDO::getUserId, userId)
                .eq(cmBlockDO::getBlockUserId, blockUserId)
                .eq(cmBlockDO::getTopicId, topicId)) > 0;
    }

}
