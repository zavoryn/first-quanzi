package cn.metast.tuoke.module.heal.service.healBanner;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.healBanner.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healBanner.HealBannerDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 首页banner Service 接口
 *
 * @author 苏丹家园
 */
public interface HealBannerService {

    /**
     * 创建首页banner
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBanner(@Valid HealBannerSaveReqVO createReqVO);

    /**
     * 更新首页banner
     *
     * @param updateReqVO 更新信息
     */
    void updateBanner(@Valid HealBannerSaveReqVO updateReqVO);

    /**
     * 删除首页banner
     *
     * @param id 编号
     */
    void deleteBanner(Long id);

    /**
     * 获得首页banner
     *
     * @param id 编号
     * @return 首页banner
     */
    HealBannerDO getBanner(Long id);

    /**
     * 获得首页banner分页
     *
     * @param pageReqVO 分页查询
     * @return 首页banner分页
     */
    PageResult<HealBannerDO> getBannerPage(HealBannerPageReqVO pageReqVO);

}