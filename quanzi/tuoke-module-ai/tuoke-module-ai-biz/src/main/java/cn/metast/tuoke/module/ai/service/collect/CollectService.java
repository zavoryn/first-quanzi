package cn.metast.tuoke.module.ai.service.collect;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.ai.controller.admin.collect.vo.*;
import cn.metast.tuoke.module.ai.dal.dataobject.collect.CollectDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * AI 功能 收藏 Service 接口
 *
 * @author 精卫拓客
 */
public interface CollectService {

    /**
     * 创建AI 功能 收藏
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCollect(@Valid CollectSaveReqVO createReqVO);

    /**
     * 更新AI 功能 收藏
     *
     * @param updateReqVO 更新信息
     */
    void updateCollect(@Valid CollectSaveReqVO updateReqVO);

    /**
     * 删除AI 功能 收藏
     *
     * @param id 编号
     */
    void deleteCollect(Long id);

    /**
     * 获得AI 功能 收藏
     *
     * @param id 编号
     * @return AI 功能 收藏
     */
    CollectDO getCollect(Long id);

    /**
     * 获得AI 功能 收藏分页
     *
     * @param pageReqVO 分页查询
     * @return AI 功能 收藏分页
     */
    PageResult<CollectDO> getCollectPage(CollectPageReqVO pageReqVO);
    List<CollectDO> getCollectList(CollectPageReqVO pageReqVO);

}
