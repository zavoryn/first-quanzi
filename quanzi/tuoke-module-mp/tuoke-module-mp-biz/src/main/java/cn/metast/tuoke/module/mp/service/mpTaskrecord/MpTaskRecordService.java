package cn.metast.tuoke.module.mp.service.mpTaskrecord;

import java.util.*;

import cn.metast.tuoke.module.mp.controller.admin.mpTask.vo.MpTaskSaveReqVO;
import jakarta.validation.*;
import cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTaskrecord.MpTaskRecordDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 公众号发送记录 Service 接口
 *
 * @author 苏丹家园
 */
public interface MpTaskRecordService {

    /**
     * 创建公众号发送记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaskRecord(@Valid MpTaskRecordSaveReqVO createReqVO);

    /**
     * 更新公众号发送记录
     *
     * @param updateReqVO 更新信息
     */
    void updateTaskRecord(@Valid MpTaskRecordSaveReqVO updateReqVO);

    /**
     * 删除公众号发送记录
     *
     * @param id 编号
     */
    void deleteTaskRecord(Long id);

    /**
     * 获得公众号发送记录
     *
     * @param id 编号
     * @return 公众号发送记录
     */
    MpTaskRecordDO getTaskRecord(Long id);

    /**
     * 获得公众号发送记录分页
     *
     * @param pageReqVO 分页查询
     * @return 公众号发送记录分页
     */
    PageResult<MpTaskRecordDO> getTaskRecordPage(MpTaskRecordPageReqVO pageReqVO);

    List<MpTaskRecordDO> getTaskRecordList(MpTaskSaveReqVO taskDto);
}
