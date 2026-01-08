package cn.metast.tuoke.module.live.service.snsCommentreply;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsCommentreply.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentreply.SnsCommentReplyDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsCommentreply.SnsCommentReplyMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 评论回复 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsCommentReplyServiceImpl implements SnsCommentReplyService {

    @Resource
    private SnsCommentReplyMapper snsCommentReplyMapper;

    @Override
    public Long createSnsCommentReply(SnsCommentReplySaveReqVO createReqVO) {
        // 插入
        SnsCommentReplyDO snsCommentReply = BeanUtils.toBean(createReqVO, SnsCommentReplyDO.class);
        snsCommentReplyMapper.insert(snsCommentReply);
        // 返回
        return snsCommentReply.getId();
    }

    @Override
    public void updateSnsCommentReply(SnsCommentReplySaveReqVO updateReqVO) {
        // 校验存在
        validateSnsCommentReplyExists(updateReqVO.getId());
        // 更新
        SnsCommentReplyDO updateObj = BeanUtils.toBean(updateReqVO, SnsCommentReplyDO.class);
        snsCommentReplyMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsCommentReply(Long id) {
        // 校验存在
        validateSnsCommentReplyExists(id);
        // 删除
        snsCommentReplyMapper.deleteById(id);
    }

    private void validateSnsCommentReplyExists(Long id) {
        if (snsCommentReplyMapper.selectById(id) == null) {
            throw exception(SNS_COMMENT_REPLY_NOT_EXISTS);
        }
    }

    @Override
    public SnsCommentReplyDO getSnsCommentReply(Long id) {
        return snsCommentReplyMapper.selectById(id);
    }

    @Override
    public PageResult<SnsCommentReplyDO> getSnsCommentReplyPage(SnsCommentReplyPageReqVO pageReqVO) {
        return snsCommentReplyMapper.selectPage(pageReqVO);
    }

}