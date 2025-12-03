package cn.metast.tuoke.module.heal.controller.admin.detection;

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

import cn.metast.tuoke.module.heal.controller.admin.detection.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.detection.DetectionDO;
import cn.metast.tuoke.module.heal.service.detection.DetectionService;

@Tag(name = "管理后台 - 检测记录")
@RestController
@RequestMapping("/heal/detection")
@Validated
public class DetectionController {

    @Resource
    private DetectionService detectionService;

    @PostMapping("/create")
    @Operation(summary = "创建检测记录")
    @PreAuthorize("@ss.hasPermission('wennuan:detection:create')")
    public CommonResult<Long> createDetection(@Valid @RequestBody DetectionSaveReqVO createReqVO) {
        return success(detectionService.createDetection(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检测记录")
    @PreAuthorize("@ss.hasPermission('wennuan:detection:update')")
    public CommonResult<Boolean> updateDetection(@Valid @RequestBody DetectionSaveReqVO updateReqVO) {
        detectionService.updateDetection(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检测记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wennuan:detection:delete')")
    public CommonResult<Boolean> deleteDetection(@RequestParam("id") Long id) {
        detectionService.deleteDetection(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检测记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wennuan:detection:query')")
    public CommonResult<DetectionRespVO> getDetection(@RequestParam("id") Long id) {
        DetectionDO detection = detectionService.getDetection(id);
        return success(BeanUtils.toBean(detection, DetectionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检测记录分页")
    @PreAuthorize("@ss.hasPermission('wennuan:detection:query')")
    public CommonResult<PageResult<DetectionRespVO>> getDetectionPage(@Valid DetectionPageReqVO pageReqVO) {
        PageResult<DetectionDO> pageResult = detectionService.getDetectionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DetectionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检测记录 Excel")
    @PreAuthorize("@ss.hasPermission('wennuan:detection:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDetectionExcel(@Valid DetectionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DetectionDO> list = detectionService.getDetectionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "检测记录.xls", "数据", DetectionRespVO.class,
                        BeanUtils.toBean(list, DetectionRespVO.class));
    }

}
