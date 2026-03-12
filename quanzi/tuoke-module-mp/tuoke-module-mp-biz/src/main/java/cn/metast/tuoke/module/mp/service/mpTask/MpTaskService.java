package cn.metast.tuoke.module.mp.service.mpTask;

import java.util.*;

import cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate.vo.MpTaskTemplateSaveReqVO;
import jakarta.validation.*;
import cn.metast.tuoke.module.mp.controller.admin.mpTask.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTask.MpTaskDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 自动开发公众号信息 Service 接口
 *
 * @author 苏丹家园
 */
public interface MpTaskService {

    /**
     * 创建自动开发公众号信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTask(@Valid MpTaskSaveReqVO createReqVO);

    /**
     * 更新自动开发公众号信息
     *
     * @param updateReqVO 更新信息
     */
    void updateTask(@Valid MpTaskSaveReqVO updateReqVO);

    /**
     * 删除自动开发公众号信息
     *
     * @param id 编号
     */
    void deleteTask(Long id);

    /**
     * 获得自动开发公众号信息
     *
     * @param id 编号
     * @return 自动开发公众号信息
     */
    MpTaskDO getTask(Long id);

    /**
     * 获得自动开发公众号信息分页
     *
     * @param pageReqVO 分页查询
     * @return 自动开发公众号信息分页
     */
    PageResult<MpTaskDO> getTaskPage(MpTaskPageReqVO pageReqVO);

    //创建任务
    public Long saveTask(MpTaskSaveReqVO createReqVO);

    public void createEmailTask(List<MpTaskSaveReqVO> createReqVO);

    List<MpTaskDO> selectTaskAll(MpTaskDO mpTaskRespVO);

    void cancelTask(String taskId);

    void createMpTask_tnt();
}
