package cn.metast.tuoke.module.live.service.snsCommentreplylike;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsCommentreplylike.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentreplylike.SnsCommentReplyLikeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsCommentreplylike.SnsCommentReplyLikeMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 评论回复点赞人数 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsCommentReplyLikeServiceImpl implements SnsCommentReplyLikeService {

    @Resource
    private SnsCommentReplyLikeMapper snsCommentReplyLikeMapper;

    @Override
    public Long createSnsCommentReplyLike(SnsCommentReplyLikeSaveReqVO createReqVO) {
        // 插入
        SnsCommentReplyLikeDO snsCommentReplyLike = BeanUtils.toBean(createReqVO, SnsCommentReplyLikeDO.class);
        snsCommentReplyLikeMapper.insert(snsCommentReplyLike);
        // 返回
        return snsCommentReplyLike.getId();
    }

    @Override
    public void updateSnsCommentReplyLike(SnsCommentReplyLikeSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsCommentReplyLikeExists(updateReqVO.getId());
        // 更新
        SnsCommentReplyLikeDO updateObj = BeanUtils.toBean(updateReqVO, SnsCommentReplyLikeDO.class);
        snsCommentReplyLikeMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsCommentReplyLike(Long id) {
        // 校验存在
        validateSnsCommentReplyLikeExists(id);
        // 删除
        snsCommentReplyLikeMapper.deleteById(id);
    }

    private void validateSnsCommentReplyLikeExists(Long id) {
        if (snsCommentReplyLikeMapper.selectById(id) == null) {
            throw exception(SNS_COMMENT_REPLY_LIKE_NOT_EXISTS);
        }
    }

    @Override
    public SnsCommentReplyLikeDO getSnsCommentReplyLike(Long id) {
        return snsCommentReplyLikeMapper.selectById(id);
    }

    @Override
    public PageResult<SnsCommentReplyLikeDO> getSnsCommentReplyLikePage(SnsCommentReplyLikePageReqVO pageReqVO) {
        return snsCommentReplyLikeMapper.selectPage(pageReqVO);
    }

}