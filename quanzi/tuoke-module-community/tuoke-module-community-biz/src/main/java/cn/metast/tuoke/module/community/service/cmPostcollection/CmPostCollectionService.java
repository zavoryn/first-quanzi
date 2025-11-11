package cn.metast.tuoke.module.community.service.cmPostcollection;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmPostcollection.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostcollection.CmPostCollectionDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 用户帖子中间 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmPostCollectionService {

    /**
     * 创建用户帖子中间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmPostCollection(@Valid CmPostCollectionSaveReqVO createReqVO);

    /**
     * 更新用户帖子中间
     *
     * @param updateReqVO 更新信息
     */
    void updateCmPostCollection(@Valid CmPostCollectionSaveReqVO updateReqVO);

    /**
     * 删除用户帖子中间
     *
     * @param id 编号
     */
    void deleteCmPostCollection(Long id);

    /**
     * 获得用户帖子中间
     *
     * @param id 编号
     * @return 用户帖子中间
     */
    CmPostCollectionDO getCmPostCollection(Long id);

    /**
     * 获得用户帖子中间分页
     *
     * @param pageReqVO 分页查询
     * @return 用户帖子中间分页
     */
    PageResult<CmPostCollectionDO> getCmPostCollectionPage(CmPostCollectionPageReqVO pageReqVO);

}