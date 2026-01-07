package cn.metast.tuoke.module.live.service.snsActguest;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActguest.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActguest.SnsActGuestDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 活动嘉宾 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActGuestService {

    /**
     * 创建活动嘉宾
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActGuest(@Valid SnsActGuestSaveReqVO createReqVO);

    /**
     * 更新活动嘉宾
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActGuest(@Valid SnsActGuestSaveReqVO updateReqVO);

    /**
     * 删除活动嘉宾
     *
     * @param id 编号
     */
    void deleteSnsActGuest(Long id);

    /**
     * 获得活动嘉宾
     *
     * @param id 编号
     * @return 活动嘉宾
     */
    SnsActGuestDO getSnsActGuest(Long id);

    /**
     * 获得活动嘉宾分页
     *
     * @param pageReqVO 分页查询
     * @return 活动嘉宾分页
     */
    PageResult<SnsActGuestDO> getSnsActGuestPage(SnsActGuestPageReqVO pageReqVO);

}