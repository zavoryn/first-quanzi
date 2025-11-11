package cn.metast.tuoke.module.community.service.cmCollect;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmCollect.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmCollect.CmCollectDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 收藏记录 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmCollectService {

    /**
     * 创建收藏记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmCollect(@Valid CmCollectSaveReqVO createReqVO);

    /**
     * 更新收藏记录
     *
     * @param updateReqVO 更新信息
     */
    void updateCmCollect(@Valid CmCollectSaveReqVO updateReqVO);

    /**
     * 删除收藏记录
     *
     * @param id 编号
     */
    void deleteCmCollect(Long id);

    /**
     * 获得收藏记录
     *
     * @param id 编号
     * @return 收藏记录
     */
    CmCollectDO getCmCollect(Long id);

    /**
     * 获得收藏记录分页
     *
     * @param pageReqVO 分页查询
     * @return 收藏记录分页
     */
    PageResult<CmCollectDO> getCmCollectPage(CmCollectPageReqVO pageReqVO);
    /**
     * 查询是否收藏
     */
    CmCollectDO selectCollect(Long userId,Long postId);
}
