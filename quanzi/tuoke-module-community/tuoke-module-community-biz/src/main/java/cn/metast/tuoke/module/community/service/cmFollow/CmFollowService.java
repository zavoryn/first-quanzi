package cn.metast.tuoke.module.community.service.cmFollow;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmFollow.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmFollow.CmFollowDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 用户关注中间 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmFollowService {

    /**
     * 创建用户关注中间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmFollow(@Valid CmFollowSaveReqVO createReqVO);

    /**
     * 更新用户关注中间
     *
     * @param updateReqVO 更新信息
     */
    void updateCmFollow(@Valid CmFollowSaveReqVO updateReqVO);

    /**
     * 删除用户关注中间
     *
     * @param id 编号
     */
    void deleteCmFollow(Long id);

    /**
     * 获得用户关注中间
     *
     * @param id 编号
     * @return 用户关注中间
     */
    CmFollowDO getCmFollow(Long id);

    /**
     * 获得用户关注中间分页
     *
     * @param pageReqVO 分页查询
     * @return 用户关注中间分页
     */
    PageResult<CmFollowDO> getCmFollowPage(CmFollowPageReqVO pageReqVO);

}