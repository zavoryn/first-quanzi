package cn.metast.tuoke.module.live.service.snsComment;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsComment.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsComment.SnsCommentDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 评论 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsCommentService {

    /**
     * 创建评论
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsComment(@Valid SnsCommentSaveReqVO createReqVO);

    /**
     * 更新评论
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsComment(@Valid SnsCommentSaveReqVO updateReqVO);

    /**
     * 删除评论
     *
     * @param id 编号
     */
    void deleteSnsComment(Long id);

    /**
     * 获得评论
     *
     * @param id 编号
     * @return 评论
     */
    SnsCommentDO getSnsComment(Long id);

    /**
     * 获得评论分页
     *
     * @param pageReqVO 分页查询
     * @return 评论分页
     */
    PageResult<SnsCommentDO> getSnsCommentPage(SnsCommentPageReqVO pageReqVO);

}