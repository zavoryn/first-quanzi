package cn.metast.tuoke.module.community.service.cmPostlike;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmPostlike.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostlike.CmPostLikeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmPostlike.CmPostLikeMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 帖子点赞 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmPostLikeServiceImpl implements CmPostLikeService {

    @Resource
    private CmPostLikeMapper cmPostLikeMapper;

    @Override
    public Integer createCmPostLike(CmPostLikeSaveReqVO createReqVO) {
        // 插入
        CmPostLikeDO cmPostLike = BeanUtils.toBean(createReqVO, CmPostLikeDO.class);
        cmPostLikeMapper.insert(cmPostLike);
        // 返回
        return cmPostLike.getId();
    }

    @Override
    public void updateCmPostLike(CmPostLikeSaveReqVO updateReqVO) {
        // 校验存在
        validateCmPostLikeExists(updateReqVO.getId());
        // 更新
        CmPostLikeDO updateObj = BeanUtils.toBean(updateReqVO, CmPostLikeDO.class);
        cmPostLikeMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmPostLike(Integer id) {
        // 校验存在
        validateCmPostLikeExists(id);
        // 删除
        cmPostLikeMapper.deleteById(id);
    }

    private void validateCmPostLikeExists(Integer id) {
        if (cmPostLikeMapper.selectById(id) == null) {
            throw exception(CM_POST_LIKE_NOT_EXISTS);
        }
    }

    @Override
    public CmPostLikeDO getCmPostLike(Integer id) {
        return cmPostLikeMapper.selectById(id);
    }

    @Override
    public PageResult<CmPostLikeDO> getCmPostLikePage(CmPostLikePageReqVO pageReqVO) {
        return cmPostLikeMapper.selectPage(pageReqVO);
    }

    @Override
    public CmPostLikeDO selectLike(Long userId, Long postId) {
        LambdaQueryWrapper<CmPostLikeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CmPostLikeDO::getUserId, userId)
                .eq(CmPostLikeDO::getPostId, postId)
                .eq(CmPostLikeDO::getDeleted, 0)
                .last("LIMIT 1");
        return cmPostLikeMapper.selectOne(wrapper);
    }
}
