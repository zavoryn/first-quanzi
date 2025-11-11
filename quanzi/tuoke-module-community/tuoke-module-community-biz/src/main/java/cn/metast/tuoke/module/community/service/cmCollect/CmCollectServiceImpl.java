package cn.metast.tuoke.module.community.service.cmCollect;

import cn.metast.tuoke.module.community.dal.dataobject.cmPostlike.CmPostLikeDO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmCollect.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCollect.CmCollectDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmCollect.CmCollectMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 收藏记录 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmCollectServiceImpl implements CmCollectService {

    @Resource
    private CmCollectMapper cmCollectMapper;

    @Override
    public Long createCmCollect(CmCollectSaveReqVO createReqVO) {
        // 插入
        CmCollectDO cmCollect = BeanUtils.toBean(createReqVO, CmCollectDO.class);
        cmCollectMapper.insert(cmCollect);
        // 返回
        return cmCollect.getId();
    }

    @Override
    public void updateCmCollect(CmCollectSaveReqVO updateReqVO) {
        // 校验存在
        validateCmCollectExists(updateReqVO.getId());
        // 更新
        CmCollectDO updateObj = BeanUtils.toBean(updateReqVO, CmCollectDO.class);
        cmCollectMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmCollect(Long id) {
        // 校验存在
        validateCmCollectExists(id);
        // 删除
        cmCollectMapper.deleteById(id);
    }

    private void validateCmCollectExists(Long id) {
        if (cmCollectMapper.selectById(id) == null) {
            throw exception(CM_COLLECT_NOT_EXISTS);
        }
    }

    @Override
    public CmCollectDO getCmCollect(Long id) {
        return cmCollectMapper.selectById(id);
    }

    @Override
    public PageResult<CmCollectDO> getCmCollectPage(CmCollectPageReqVO pageReqVO) {
        return cmCollectMapper.selectPage(pageReqVO);
    }

    @Override
    public CmCollectDO selectCollect(Long userId, Long postId) {
        return cmCollectMapper.selectOne(
                CmCollectDO::getUserId, userId,
                CmCollectDO::getPostId, postId,
                CmCollectDO::getDeleted, 0
        );
    }
}
