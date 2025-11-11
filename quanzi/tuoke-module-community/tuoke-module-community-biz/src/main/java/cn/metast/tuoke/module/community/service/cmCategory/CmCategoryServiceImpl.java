package cn.metast.tuoke.module.community.service.cmCategory;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmCategory.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCategory.CmCategoryDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmCategory.CmCategoryMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 圈子分类 Service 实现类
 *
 * @author 苏丹家园
 */
@Service
@Validated
public class CmCategoryServiceImpl implements CmCategoryService {

    @Resource
    private CmCategoryMapper cmCategoryMapper;

    @Override
    public Long createCmCategory(CmCategorySaveReqVO createReqVO) {
        // 插入
        CmCategoryDO cmCategory = BeanUtils.toBean(createReqVO, CmCategoryDO.class);
        cmCategoryMapper.insert(cmCategory);
        // 返回
        return cmCategory.getId();
    }

    @Override
    public void updateCmCategory(CmCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateCmCategoryExists(updateReqVO.getId());
        // 更新
        CmCategoryDO updateObj = BeanUtils.toBean(updateReqVO, CmCategoryDO.class);
        cmCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCmCategory(Long id) {
        // 校验存在
        validateCmCategoryExists(id);
        // 删除
        cmCategoryMapper.deleteById(id);
    }

    private void validateCmCategoryExists(Long id) {
        if (cmCategoryMapper.selectById(id) == null) {
            throw exception(CM_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public CmCategoryDO getCmCategory(Long id) {
        return cmCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<CmCategoryDO> getCmCategoryPage(CmCategoryPageReqVO pageReqVO) {
        return cmCategoryMapper.selectPage(pageReqVO);
    }

}