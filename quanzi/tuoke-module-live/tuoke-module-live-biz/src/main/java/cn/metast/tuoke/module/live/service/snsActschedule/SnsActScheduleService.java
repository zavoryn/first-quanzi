package cn.metast.tuoke.module.live.service.snsActschedule;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActschedule.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActschedule.SnsActScheduleDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 活动日程 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActScheduleService {

    /**
     * 创建活动日程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActSchedule(@Valid SnsActScheduleSaveReqVO createReqVO);

    /**
     * 更新活动日程
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActSchedule(@Valid SnsActScheduleSaveReqVO updateReqVO);

    /**
     * 删除活动日程
     *
     * @param id 编号
     */
    void deleteSnsActSchedule(Long id);

    /**
     * 获得活动日程
     *
     * @param id 编号
     * @return 活动日程
     */
    SnsActScheduleDO getSnsActSchedule(Long id);

    /**
     * 获得活动日程分页
     *
     * @param pageReqVO 分页查询
     * @return 活动日程分页
     */
    PageResult<SnsActScheduleDO> getSnsActSchedulePage(SnsActSchedulePageReqVO pageReqVO);

}