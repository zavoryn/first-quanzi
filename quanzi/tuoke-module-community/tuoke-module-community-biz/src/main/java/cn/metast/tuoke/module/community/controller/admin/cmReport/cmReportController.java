package cn.metast.tuoke.module.community.controller.admin.cmReport;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.metast.tuoke.module.community.controller.admin.cmReport.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmReport.cmReportDO;
import cn.metast.tuoke.module.community.service.cmReport.cmReportService;

@Tag(name = "管理后台 - 举报记录")
@RestController
@RequestMapping("/community/cm-report")
@Validated
public class cmReportController {

    @Resource
    private cmReportService cmReportService;

    @PostMapping("/create")
    @Operation(summary = "创建举报记录")
    @PreAuthorize("@ss.hasPermission('community:cm-report:create')")
    public CommonResult<Long> createcmReport(@Valid @RequestBody cmReportSaveReqVO createReqVO) {
        return success(cmReportService.createcmReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新举报记录")
    @PreAuthorize("@ss.hasPermission('community:cm-report:update')")
    public CommonResult<Boolean> updatecmReport(@Valid @RequestBody cmReportSaveReqVO updateReqVO) {
        cmReportService.updatecmReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除举报记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('community:cm-report:delete')")
    public CommonResult<Boolean> deletecmReport(@RequestParam("id") Long id) {
        cmReportService.deletecmReport(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得举报记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('community:cm-report:query')")
    public CommonResult<cmReportRespVO> getcmReport(@RequestParam("id") Long id) {
        cmReportDO cmReport = cmReportService.getcmReport(id);
        return success(BeanUtils.toBean(cmReport, cmReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得举报记录分页")
    @PreAuthorize("@ss.hasPermission('community:cm-report:query')")
    public CommonResult<PageResult<cmReportRespVO>> getcmReportPage(@Valid cmReportPageReqVO pageReqVO) {
        PageResult<cmReportDO> pageResult = cmReportService.getcmReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, cmReportRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出举报记录 Excel")
    @PreAuthorize("@ss.hasPermission('community:cm-report:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportcmReportExcel(@Valid cmReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<cmReportDO> list = cmReportService.getcmReportPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "举报记录.xls", "数据", cmReportRespVO.class,
                        BeanUtils.toBean(list, cmReportRespVO.class));
    }

}