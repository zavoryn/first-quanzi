package cn.metast.tuoke.module.community.service.cmCommentlike;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmCommentlike.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentlike.CmCommentLikeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 评论点赞 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmCommentLikeService {

    /**
     * 创建评论点赞
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createCmCommentLike(@Valid CmCommentLikeSaveReqVO createReqVO);

    /**
     * 更新评论点赞
     *
     * @param updateReqVO 更新信息
     */
    void updateCmCommentLike(@Valid CmCommentLikeSaveReqVO updateReqVO);

    /**
     * 删除评论点赞
     *
     * @param id 编号
     */
    void deleteCmCommentLike(Integer id);

    /**
     * 获得评论点赞
     *
     * @param id 编号
     * @return 评论点赞
     */
    CmCommentLikeDO getCmCommentLike(Integer id);

    /**
     * 获得评论点赞分页
     *
     * @param pageReqVO 分页查询
     * @return 评论点赞分页
     */
    PageResult<CmCommentLikeDO> getCmCommentLikePage(CmCommentLikePageReqVO pageReqVO);

    CmCommentLikeDO selectCommentLike(Long userId,Long commentId);
}
