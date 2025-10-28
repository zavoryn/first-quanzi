package cn.metast.tuoke.module.ai.service.collect;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.ai.controller.admin.collect.vo.*;
import cn.metast.tuoke.module.ai.dal.dataobject.collect.CollectDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.ai.dal.mysql.collect.CollectMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.ai.enums.ErrorCodeConstants.*;

/**
 * AI 功能 收藏 Service 实现类
 *
 * @author 精卫拓客
 */
@Service
@Validated
public class CollectServiceImpl implements CollectService {

    @Resource
    private CollectMapper collectMapper;

    @Override
    public Long createCollect(CollectSaveReqVO createReqVO) {
        // 插入
        CollectDO collect = BeanUtils.toBean(createReqVO, CollectDO.class);
        collectMapper.insert(collect);
        // 返回
        return collect.getId();
    }

    @Override
    public void updateCollect(CollectSaveReqVO updateReqVO) {
        // 校验存在
        validateCollectExists(updateReqVO.getId());
        // 更新
        CollectDO updateObj = BeanUtils.toBean(updateReqVO, CollectDO.class);
        collectMapper.updateById(updateObj);
    }

    @Override
    public void deleteCollect(Long id) {
        // 校验存在
        validateCollectExists(id);
        // 删除
        collectMapper.deleteById(id);
    }

    private void validateCollectExists(Long id) {
        if (collectMapper.selectById(id) == null) {
            throw exception(COLLECT_NOT_EXISTS);
        }
    }

    @Override
    public CollectDO getCollect(Long id) {
        return collectMapper.selectById(id);
    }

    @Override
    public PageResult<CollectDO> getCollectPage(CollectPageReqVO pageReqVO) {
        return collectMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CollectDO> getCollectList(CollectPageReqVO pageReqVO) {
        return collectMapper.selectList(pageReqVO);
    }

}
