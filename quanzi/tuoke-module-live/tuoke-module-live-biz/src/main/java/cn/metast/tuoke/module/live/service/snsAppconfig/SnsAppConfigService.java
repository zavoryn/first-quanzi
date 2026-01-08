package cn.metast.tuoke.module.live.service.snsAppconfig;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsAppconfig.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAppconfig.SnsAppConfigDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 配置 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsAppConfigService {

    /**
     * 创建配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsAppConfig(@Valid SnsAppConfigSaveReqVO createReqVO);

    /**
     * 更新配置
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsAppConfig(@Valid SnsAppConfigSaveReqVO updateReqVO);

    /**
     * 删除配置
     *
     * @param id 编号
     */
    void deleteSnsAppConfig(Long id);

    /**
     * 获得配置
     *
     * @param id 编号
     * @return 配置
     */
    SnsAppConfigDO getSnsAppConfig(Long id);

    /**
     * 获得配置分页
     *
     * @param pageReqVO 分页查询
     * @return 配置分页
     */
    PageResult<SnsAppConfigDO> getSnsAppConfigPage(SnsAppConfigPageReqVO pageReqVO);

}