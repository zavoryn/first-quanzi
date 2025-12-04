package cn.metast.tuoke.module.heal.service.healServiceconfig;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.healServiceconfig.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healServiceconfig.HealServiceConfigDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 服务配置 Service 接口
 *
 * @author 苏丹家园
 */
public interface HealServiceConfigService {

    /**
     * 创建服务配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createServiceConfig(@Valid HealServiceConfigSaveReqVO createReqVO);

    /**
     * 更新服务配置
     *
     * @param updateReqVO 更新信息
     */
    void updateServiceConfig(@Valid HealServiceConfigSaveReqVO updateReqVO);

    /**
     * 删除服务配置
     *
     * @param id 编号
     */
    void deleteServiceConfig(Long id);

    /**
     * 获得服务配置
     *
     * @param id 编号
     * @return 服务配置
     */
    HealServiceConfigDO getServiceConfig(Long id);

    /**
     * 获得服务配置分页
     *
     * @param pageReqVO 分页查询
     * @return 服务配置分页
     */
    PageResult<HealServiceConfigDO> getServiceConfigPage(HealServiceConfigPageReqVO pageReqVO);

}