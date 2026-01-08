package cn.metast.tuoke.module.live.service.snsCommentlike;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsCommentlike.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsCommentlike.SnsCommentLikeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsCommentlike.SnsCommentLikeMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 评论点赞人 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsCommentLikeServiceImpl implements SnsCommentLikeService {

    @Resource
    private SnsCommentLikeMapper snsCommentLikeMapper;

    @Override
    public Long createSnsCommentLike(SnsCommentLikeSaveReqVO createReqVO) {
        // 插入
        SnsCommentLikeDO snsCommentLike = BeanUtils.toBean(createReqVO, SnsCommentLikeDO.class);
        snsCommentLikeMapper.insert(snsCommentLike);
        // 返回
        return snsCommentLike.getId();
    }

    @Override
    public void updateSnsCommentLike(SnsCommentLikeSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsCommentLikeExists(updateReqVO.getId());
        // 更新
        SnsCommentLikeDO updateObj = BeanUtils.toBean(updateReqVO, SnsCommentLikeDO.class);
        snsCommentLikeMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsCommentLike(Long id) {
        // 校验存在
        validateSnsCommentLikeExists(id);
        // 删除
        snsCommentLikeMapper.deleteById(id);
    }

    private void validateSnsCommentLikeExists(Long id) {
        if (snsCommentLikeMapper.selectById(id) == null) {
            throw exception(SNS_COMMENT_LIKE_NOT_EXISTS);
        }
    }

    @Override
    public SnsCommentLikeDO getSnsCommentLike(Long id) {
        return snsCommentLikeMapper.selectById(id);
    }

    @Override
    public PageResult<SnsCommentLikeDO> getSnsCommentLikePage(SnsCommentLikePageReqVO pageReqVO) {
        return snsCommentLikeMapper.selectPage(pageReqVO);
    }

}