package cn.metast.tuoke.module.community.service.cmLink;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmLink.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmLink.CmLinkDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 首页轮播图 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmLinkService {

    /**
     * 创建首页轮播图
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmLink(@Valid CmLinkSaveReqVO createReqVO);

    /**
     * 更新首页轮播图
     *
     * @param updateReqVO 更新信息
     */
    void updateCmLink(@Valid CmLinkSaveReqVO updateReqVO);

    /**
     * 删除首页轮播图
     *
     * @param id 编号
     */
    void deleteCmLink(Long id);

    /**
     * 获得首页轮播图
     *
     * @param id 编号
     * @return 首页轮播图
     */
    CmLinkDO getCmLink(Long id);

    /**
     * 获得首页轮播图分页
     *
     * @param pageReqVO 分页查询
     * @return 首页轮播图分页
     */
    PageResult<CmLinkDO> getCmLinkPage(CmLinkPageReqVO pageReqVO);

}