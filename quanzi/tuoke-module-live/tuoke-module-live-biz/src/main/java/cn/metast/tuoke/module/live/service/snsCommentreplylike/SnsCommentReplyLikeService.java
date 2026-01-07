package cn.metast.tuoke.module.live.service.snsCommentreplylike;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsCommentreplylike.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentreplylike.SnsCommentReplyLikeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 评论回复点赞人数 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsCommentReplyLikeService {

    /**
     * 创建评论回复点赞人数
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsCommentReplyLike(@Valid SnsCommentReplyLikeSaveReqVO createReqVO);

    /**
     * 更新评论回复点赞人数
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsCommentReplyLike(@Valid SnsCommentReplyLikeSaveReqVO updateReqVO);

    /**
     * 删除评论回复点赞人数
     *
     * @param id 编号
     */
    void deleteSnsCommentReplyLike(Long id);

    /**
     * 获得评论回复点赞人数
     *
     * @param id 编号
     * @return 评论回复点赞人数
     */
    SnsCommentReplyLikeDO getSnsCommentReplyLike(Long id);

    /**
     * 获得评论回复点赞人数分页
     *
     * @param pageReqVO 分页查询
     * @return 评论回复点赞人数分页
     */
    PageResult<SnsCommentReplyLikeDO> getSnsCommentReplyLikePage(SnsCommentReplyLikePageReqVO pageReqVO);

}