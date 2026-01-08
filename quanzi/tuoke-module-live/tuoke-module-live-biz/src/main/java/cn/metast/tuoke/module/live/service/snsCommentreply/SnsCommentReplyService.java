package cn.metast.tuoke.module.live.service.snsCommentreply;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsCommentreply.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentreply.SnsCommentReplyDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 评论回复 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsCommentReplyService {

    /**
     * 创建评论回复
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsCommentReply(@Valid SnsCommentReplySaveReqVO createReqVO);

    /**
     * 更新评论回复
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsCommentReply(@Valid SnsCommentReplySaveReqVO updateReqVO);

    /**
     * 删除评论回复
     *
     * @param id 编号
     */
    void deleteSnsCommentReply(Long id);

    /**
     * 获得评论回复
     *
     * @param id 编号
     * @return 评论回复
     */
    SnsCommentReplyDO getSnsCommentReply(Long id);

    /**
     * 获得评论回复分页
     *
     * @param pageReqVO 分页查询
     * @return 评论回复分页
     */
    PageResult<SnsCommentReplyDO> getSnsCommentReplyPage(SnsCommentReplyPageReqVO pageReqVO);

}