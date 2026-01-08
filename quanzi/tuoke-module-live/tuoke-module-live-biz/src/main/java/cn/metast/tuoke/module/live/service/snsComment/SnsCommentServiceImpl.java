package cn.metast.tuoke.module.live.service.snsComment;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsComment.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsComment.SnsCommentDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsComment.SnsCommentMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 评论 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsCommentServiceImpl implements SnsCommentService {

    @Resource
    private SnsCommentMapper snsCommentMapper;

    @Override
    public Long createSnsComment(SnsCommentSaveReqVO createReqVO) {
        // 插入
        SnsCommentDO snsComment = BeanUtils.toBean(createReqVO, SnsCommentDO.class);
        snsCommentMapper.insert(snsComment);
        // 返回
        return snsComment.getId();
    }

    @Override
    public void updateSnsComment(SnsCommentSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsCommentExists(updateReqVO.getId());
        // 更新
        SnsCommentDO updateObj = BeanUtils.toBean(updateReqVO, SnsCommentDO.class);
        snsCommentMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsComment(Long id) {
        // 校验存在
        validateSnsCommentExists(id);
        // 删除
        snsCommentMapper.deleteById(id);
    }

    private void validateSnsCommentExists(Long id) {
        if (snsCommentMapper.selectById(id) == null) {
            throw exception(SNS_COMMENT_NOT_EXISTS);
        }
    }

    @Override
    public SnsCommentDO getSnsComment(Long id) {
        return snsCommentMapper.selectById(id);
    }

    @Override
    public PageResult<SnsCommentDO> getSnsCommentPage(SnsCommentPageReqVO pageReqVO) {
        return snsCommentMapper.selectPage(pageReqVO);
    }

}