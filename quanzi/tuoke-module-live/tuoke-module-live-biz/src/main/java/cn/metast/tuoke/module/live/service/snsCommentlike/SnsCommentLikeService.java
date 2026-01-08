package cn.metast.tuoke.module.live.service.snsCommentlike;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsCommentlike.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentlike.SnsCommentLikeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 评论点赞人 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsCommentLikeService {

    /**
     * 创建评论点赞人
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsCommentLike(@Valid SnsCommentLikeSaveReqVO createReqVO);

    /**
     * 更新评论点赞人
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsCommentLike(@Valid SnsCommentLikeSaveReqVO updateReqVO);

    /**
     * 删除评论点赞人
     *
     * @param id 编号
     */
    void deleteSnsCommentLike(Long id);

    /**
     * 获得评论点赞人
     *
     * @param id 编号
     * @return 评论点赞人
     */
    SnsCommentLikeDO getSnsCommentLike(Long id);

    /**
     * 获得评论点赞人分页
     *
     * @param pageReqVO 分页查询
     * @return 评论点赞人分页
     */
    PageResult<SnsCommentLikeDO> getSnsCommentLikePage(SnsCommentLikePageReqVO pageReqVO);

}