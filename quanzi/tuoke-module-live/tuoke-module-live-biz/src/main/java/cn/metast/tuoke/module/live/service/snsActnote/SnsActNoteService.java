package cn.metast.tuoke.module.live.service.snsActnote;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.live.controller.admin.snsActnote.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActnote.SnsActNoteDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 活动记录 Service 接口
 *
 * @author 夏兆金
 */
public interface SnsActNoteService {

    /**
     * 创建活动记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSnsActNote(@Valid SnsActNoteSaveReqVO createReqVO);

    /**
     * 更新活动记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSnsActNote(@Valid SnsActNoteSaveReqVO updateReqVO);

    /**
     * 删除活动记录
     *
     * @param id 编号
     */
    void deleteSnsActNote(Long id);

    /**
     * 获得活动记录
     *
     * @param id 编号
     * @return 活动记录
     */
    SnsActNoteDO getSnsActNote(Long id);

    /**
     * 获得活动记录分页
     *
     * @param pageReqVO 分页查询
     * @return 活动记录分页
     */
    PageResult<SnsActNoteDO> getSnsActNotePage(SnsActNotePageReqVO pageReqVO);

}