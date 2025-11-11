package cn.metast.tuoke.module.community.service.cmReport;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.community.controller.admin.cmReport.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmReport.cmReportDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.community.dal.mysql.cmReport.cmReportMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.community.enums.ErrorCodeConstants.*;

/**
 * 举报记录 Service 实现类
 *
 * @author adminXq
 */
@Service
@Validated
public class cmReportServiceImpl implements cmReportService {

    @Resource
    private cmReportMapper cmReportMapper;

    @Override
    public Long createcmReport(cmReportSaveReqVO createReqVO) {
        // 插入
        cmReportDO cmReport = BeanUtils.toBean(createReqVO, cmReportDO.class);
        cmReportMapper.insert(cmReport);
        // 返回
        return cmReport.getId();
    }

    @Override
    public void updatecmReport(cmReportSaveReqVO updateReqVO) {
        // 校验存在
        validatecmReportExists(updateReqVO.getId());
        // 更新
        cmReportDO updateObj = BeanUtils.toBean(updateReqVO, cmReportDO.class);
        cmReportMapper.updateById(updateObj);
    }

    @Override
    public void deletecmReport(Long id) {
        // 校验存在
        validatecmReportExists(id);
        // 删除
        cmReportMapper.deleteById(id);
    }

    private void validatecmReportExists(Long id) {
        if (cmReportMapper.selectById(id) == null) {
            throw exception(CM_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public cmReportDO getcmReport(Long id) {
        return cmReportMapper.selectById(id);
    }

    @Override
    public PageResult<cmReportDO> getcmReportPage(cmReportPageReqVO pageReqVO) {
        return cmReportMapper.selectPage(pageReqVO);
    }

}