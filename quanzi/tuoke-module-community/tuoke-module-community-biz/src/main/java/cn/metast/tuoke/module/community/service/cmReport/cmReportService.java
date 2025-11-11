package cn.metast.tuoke.module.community.service.cmReport;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmReport.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmReport.cmReportDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;

/**
 * 举报记录 Service 接口
 *
 * @author adminXq
 */
public interface cmReportService {

    /**
     * 创建举报记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createcmReport(@Valid cmReportSaveReqVO createReqVO);

    /**
     * 更新举报记录
     *
     * @param updateReqVO 更新信息
     */
    void updatecmReport(@Valid cmReportSaveReqVO updateReqVO);

    /**
     * 删除举报记录
     *
     * @param id 编号
     */
    void deletecmReport(Long id);

    /**
     * 获得举报记录
     *
     * @param id 编号
     * @return 举报记录
     */
    cmReportDO getcmReport(Long id);

    /**
     * 获得举报记录分页
     *
     * @param pageReqVO 分页查询
     * @return 举报记录分页
     */
    PageResult<cmReportDO> getcmReportPage(cmReportPageReqVO pageReqVO);

}