package cn.metast.tuoke.module.community.service.cmCategory;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmCategory.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCategory.CmCategoryDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 圈子分类 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmCategoryService {

    /**
     * 创建圈子分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmCategory(@Valid CmCategorySaveReqVO createReqVO);

    /**
     * 更新圈子分类
     *
     * @param updateReqVO 更新信息
     */
    void updateCmCategory(@Valid CmCategorySaveReqVO updateReqVO);

    /**
     * 删除圈子分类
     *
     * @param id 编号
     */
    void deleteCmCategory(Long id);

    /**
     * 获得圈子分类
     *
     * @param id 编号
     * @return 圈子分类
     */
    CmCategoryDO getCmCategory(Long id);

    /**
     * 获得圈子分类分页
     *
     * @param pageReqVO 分页查询
     * @return 圈子分类分页
     */
    PageResult<CmCategoryDO> getCmCategoryPage(CmCategoryPageReqVO pageReqVO);

}