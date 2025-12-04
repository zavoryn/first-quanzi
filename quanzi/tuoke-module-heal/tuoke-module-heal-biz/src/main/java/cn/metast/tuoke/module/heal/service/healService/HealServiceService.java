package cn.metast.tuoke.module.heal.service.healService;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.healService.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healService.HealServiceDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 服务列 Service 接口
 *
 * @author 苏丹家园
 */
public interface HealServiceService {

    /**
     * 创建服务列
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createService(@Valid HealServiceSaveReqVO createReqVO);

    /**
     * 更新服务列
     *
     * @param updateReqVO 更新信息
     */
    void updateService(@Valid HealServiceSaveReqVO updateReqVO);

    /**
     * 删除服务列
     *
     * @param id 编号
     */
    void deleteService(Long id);

    /**
     * 获得服务列
     *
     * @param id 编号
     * @return 服务列
     */
    HealServiceDO getService(Long id);

    /**
     * 获得服务列分页
     *
     * @param pageReqVO 分页查询
     * @return 服务列分页
     */
    PageResult<HealServiceDO> getServicePage(HealServicePageReqVO pageReqVO);

}