package cn.metast.tuoke.module.community.service.cmComment;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmComment.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmComment.CmCommentDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 圈子评论 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmCommentService {

    /**
     * 创建圈子评论
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmComment(@Valid CmCommentSaveReqVO createReqVO);

    /**
     * 更新圈子评论
     *
     * @param updateReqVO 更新信息
     */
    void updateCmComment(@Valid CmCommentSaveReqVO updateReqVO);

    /**
     * 删除圈子评论
     *
     * @param id 编号
     */
    void deleteCmComment(Long id);

    /**
     * 获得圈子评论
     *
     * @param id 编号
     * @return 圈子评论
     */
    CmCommentDO getCmComment(Long id);

    /**
     * 获得圈子评论分页
     *
     * @param pageReqVO 分页查询
     * @return 圈子评论分页
     */
    PageResult<CmCommentDO> getCmCommentPage(CmCommentPageReqVO pageReqVO);

    PageResult<CmCommentDO> getCmCommentPageNew(CmCommentPageReqVO pageReqVO);

    Long selectCmCommentCount(Long postId,Long userId);

    List<CmCommentDO> getCmCommentPageShow(CmCommentPageReqVO pageReqVO);
}
