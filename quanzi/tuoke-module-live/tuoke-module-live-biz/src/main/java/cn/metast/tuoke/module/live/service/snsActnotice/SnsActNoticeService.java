package cn.metast.tuoke.module.live.service.snsActnotice;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActnotice.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActnotice.SnsActNoticeDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 活动公告 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActNoticeService {

    /**
     * 创建活动公告
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActNotice(@Valid SnsActNoticeSaveReqVO createReqVO);

    /**
     * 更新活动公告
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActNotice(@Valid SnsActNoticeSaveReqVO updateReqVO);

    /**
     * 删除活动公告
     *
     * @param id 编号
     */
    void deleteSnsActNotice(Long id);

    /**
     * 获得活动公告
     *
     * @param id 编号
     * @return 活动公告
     */
    SnsActNoticeDO getSnsActNotice(Long id);

    /**
     * 获得活动公告分页
     *
     * @param pageReqVO 分页查询
     * @return 活动公告分页
     */
    PageResult<SnsActNoticeDO> getSnsActNoticePage(SnsActNoticePageReqVO pageReqVO);

}