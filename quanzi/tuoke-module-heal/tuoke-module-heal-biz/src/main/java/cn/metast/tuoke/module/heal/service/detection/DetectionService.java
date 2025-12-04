package cn.metast.tuoke.module.heal.service.detection;

import java.util.*;

import cn.hutool.json.JSONObject;
import jakarta.validation.*;
import cn.metast.tuoke.module.heal.controller.admin.detection.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.detection.DetectionDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 检测记录 Service 接口
 *
 * @author 超级管理员
 */
public interface DetectionService {

    /**
     * 创建检测记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDetection(@Valid DetectionSaveReqVO createReqVO);

    /**
     * 更新检测记录
     *
     * @param updateReqVO 更新信息
     */
    void updateDetection(@Valid DetectionSaveReqVO updateReqVO);

    /**
     * 删除检测记录
     *
     * @param id 编号
     */
    void deleteDetection(Long id);

    /**
     * 获得检测记录
     *
     * @param id 编号
     * @return 检测记录
     */
    DetectionDO getDetection(Long id);

    DetectionDO getDetectionUid(Long uId);

    /**
     * 获得检测记录分页
     *
     * @param pageReqVO 分页查询
     * @return 检测记录分页
     */
    PageResult<DetectionDO> getDetectionPage(DetectionPageReqVO pageReqVO);

    List<DetectionDO> getExcelList(DetectionRespVO respVO);

    Map<String, Object> getReportData(DetectionDO item);

}
