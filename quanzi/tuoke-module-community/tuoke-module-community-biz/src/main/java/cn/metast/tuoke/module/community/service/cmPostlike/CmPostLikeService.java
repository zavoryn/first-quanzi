package cn.metast.tuoke.module.community.service.cmPostlike;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmPostlike.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostlike.CmPostLikeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 帖子点赞 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmPostLikeService {

    /**
     * 创建帖子点赞
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createCmPostLike(@Valid CmPostLikeSaveReqVO createReqVO);

    /**
     * 更新帖子点赞
     *
     * @param updateReqVO 更新信息
     */
    void updateCmPostLike(@Valid CmPostLikeSaveReqVO updateReqVO);

    /**
     * 删除帖子点赞
     *
     * @param id 编号
     */
    void deleteCmPostLike(Integer id);

    /**
     * 获得帖子点赞
     *
     * @param id 编号
     * @return 帖子点赞
     */
    CmPostLikeDO getCmPostLike(Integer id);

    /**
     * 获得帖子点赞分页
     *
     * @param pageReqVO 分页查询
     * @return 帖子点赞分页
     */
    PageResult<CmPostLikeDO> getCmPostLikePage(CmPostLikePageReqVO pageReqVO);

    CmPostLikeDO selectLike(Long userId, Long postId);

}
