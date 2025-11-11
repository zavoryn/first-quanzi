package cn.metast.tuoke.module.community.service.cmCommentlike;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmCommentlike.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCommentlike.CmCommentLikeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmCommentlike.CmCommentLikeMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 评论点赞 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmCommentLikeServiceImpl implements CmCommentLikeService {

    @Resource
    private CmCommentLikeMapper cmCommentLikeMapper;

    @Override
    public Integer createCmCommentLike(CmCommentLikeSaveReqVO createReqVO) {
        // 插入
        CmCommentLikeDO cmCommentLike = BeanUtils.toBean(createReqVO, CmCommentLikeDO.class);
        cmCommentLikeMapper.insert(cmCommentLike);
        // 返回
        return cmCommentLike.getId();
    }

    @Override
    public void updateCmCommentLike(CmCommentLikeSaveReqVO updateReqVO) {
        // 校验存在
        validateCmCommentLikeExists(updateReqVO.getId());
        // 更新
        CmCommentLikeDO updateObj = BeanUtils.toBean(updateReqVO, CmCommentLikeDO.class);
        cmCommentLikeMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmCommentLike(Integer id) {
        // 校验存在
        validateCmCommentLikeExists(id);
        // 删除
        cmCommentLikeMapper.deleteById(id);
    }

    private void validateCmCommentLikeExists(Integer id) {
        if (cmCommentLikeMapper.selectById(id) == null) {
            throw exception(CM_COMMENT_LIKE_NOT_EXISTS);
        }
    }

    @Override
    public CmCommentLikeDO getCmCommentLike(Integer id) {
        return cmCommentLikeMapper.selectById(id);
    }

    @Override
    public PageResult<CmCommentLikeDO> getCmCommentLikePage(CmCommentLikePageReqVO pageReqVO) {
        return cmCommentLikeMapper.selectPage(pageReqVO);
    }

    @Override
    public CmCommentLikeDO selectCommentLike(Long userId, Long commentId) {
        return cmCommentLikeMapper.selectOne(
                CmCommentLikeDO::getUserId, userId,
                CmCommentLikeDO::getCommentId, commentId
     );
    }

}
