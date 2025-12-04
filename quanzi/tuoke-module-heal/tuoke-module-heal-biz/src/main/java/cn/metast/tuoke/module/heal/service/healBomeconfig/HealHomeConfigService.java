package cn.metast.tuoke.module.heal.service.healBomeconfig;

import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.healHomeconfig.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healBomeconfig.HealHomeConfigDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;

/**
 * 首页模块配置 Service 接口
 *
 * @author 苏丹家园
 */
public interface HealHomeConfigService {

    /**
     * 创建首页模块配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createHomeConfig(@Valid HealHomeConfigSaveReqVO createReqVO);

    /**
     * 更新首页模块配置
     *
     * @param updateReqVO 更新信息
     */
    void updateHomeConfig(@Valid HealHomeConfigSaveReqVO updateReqVO);

    /**
     * 删除首页模块配置
     *
     * @param id 编号
     */
    void deleteHomeConfig(Long id);

    /**
     * 获得首页模块配置
     *
     * @param id 编号
     * @return 首页模块配置
     */
    HealHomeConfigDO getHomeConfig(Long id);

    /**
     * 获得首页模块配置分页
     *
     * @param pageReqVO 分页查询
     * @return 首页模块配置分页
     */
    PageResult<HealHomeConfigDO> getHomeConfigPage(HealHomeConfigPageReqVO pageReqVO);

}
