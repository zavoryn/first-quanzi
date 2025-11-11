package cn.metast.tuoke.module.community.service.cmPostcollection;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmPostcollection.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostcollection.CmPostCollectionDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmPostcollection.CmPostCollectionMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 用户帖子中间 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmPostCollectionServiceImpl implements CmPostCollectionService {

    @Resource
    private CmPostCollectionMapper cmPostCollectionMapper;

    @Override
    public Long createCmPostCollection(CmPostCollectionSaveReqVO createReqVO) {
        // 插入
        CmPostCollectionDO cmPostCollection = BeanUtils.toBean(createReqVO, CmPostCollectionDO.class);
        cmPostCollectionMapper.insert(cmPostCollection);
        // 返回
        return cmPostCollection.getId();
    }

    @Override
    public void updateCmPostCollection(CmPostCollectionSaveReqVO updateReqVO) {
        // 校验存在
        validateCmPostCollectionExists(updateReqVO.getId());
        // 更新
        CmPostCollectionDO updateObj = BeanUtils.toBean(updateReqVO, CmPostCollectionDO.class);
        cmPostCollectionMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmPostCollection(Long id) {
        // 校验存在
        validateCmPostCollectionExists(id);
        // 删除
        cmPostCollectionMapper.deleteById(id);
    }

    private void validateCmPostCollectionExists(Long id) {
        if (cmPostCollectionMapper.selectById(id) == null) {
            throw exception(CM_POST_COLLECTION_NOT_EXISTS);
        }
    }

    @Override
    public CmPostCollectionDO getCmPostCollection(Long id) {
        return cmPostCollectionMapper.selectById(id);
    }

    @Override
    public PageResult<CmPostCollectionDO> getCmPostCollectionPage(CmPostCollectionPageReqVO pageReqVO) {
        return cmPostCollectionMapper.selectPage(pageReqVO);
    }

}