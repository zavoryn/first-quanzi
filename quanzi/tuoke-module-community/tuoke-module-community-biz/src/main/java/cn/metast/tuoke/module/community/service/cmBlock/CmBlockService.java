package cn.metast.tuoke.module.community.service.cmBlock;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmBlock.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmBlock.cmBlockDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 拉黑记录 Service 接口
 *
 * @author adminXq
 */
public interface CmBlockService {

    /**
     * 创建拉黑记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createcmBlock(@Valid cmBlockSaveReqVO createReqVO);

    /**
     * 更新拉黑记录
     *
     * @param updateReqVO 更新信息
     */
    void updatecmBlock(@Valid cmBlockSaveReqVO updateReqVO);

    /**
     * 删除拉黑记录
     *
     * @param id 编号
     */
    void deletecmBlock(Long id);

    /**
     * 获得拉黑记录
     *
     * @param id 编号
     * @return 拉黑记录
     */
    cmBlockDO getcmBlock(Long id);

    /**
     * 获得拉黑记录分页
     *
     * @param pageReqVO 分页查询
     * @return 拉黑记录分页
     */
    PageResult<cmBlockDO> getcmBlockPage(cmBlockPageReqVO pageReqVO);

    /**
     * 查询用户在指定圈子中拉黑的用户ID列表
     *
     * @param userId  用户ID
     * @param topicId 圈子ID
     * @return 被拉黑的用户ID列表
     */
    List<Long> getBlockUserIds(Long userId, Long topicId);

    /**
     * 校验是否已存在拉黑记录
     *
     * @param userId      用户ID
     * @param blockUserId 被拉黑用户ID
     * @param topicId     圈子ID
     * @return 是否已存在
     */
    boolean existsBlock(Long userId, Long blockUserId, Long topicId);

}
