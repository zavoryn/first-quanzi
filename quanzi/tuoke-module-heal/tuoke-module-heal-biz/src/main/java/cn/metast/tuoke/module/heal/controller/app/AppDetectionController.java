package cn.metast.tuoke.module.heal.controller.app;
import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.heal.controller.admin.detection.vo.DetectionPageReqVO;
import cn.metast.tuoke.module.heal.controller.admin.detection.vo.DetectionRespVO;
import cn.metast.tuoke.module.heal.controller.admin.detection.vo.DetectionSaveReqVO;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import cn.metast.tuoke.module.heal.dal.dataobject.detection.DetectionDO;
import cn.metast.tuoke.module.heal.service.archives.ArchivesService;
import cn.metast.tuoke.module.heal.service.detection.DetectionService;
import cn.metast.tuoke.module.heal.util.DetectionExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 检测记录")
@RestController
@RequestMapping("/heal/detection")
@Validated
public class AppDetectionController {

    @Resource
    private DetectionService detectionService;
    @Resource
    private ArchivesService archivesService;
    @Resource
    private DetectionExcelUtil detectionExcelUtil;

    @PostMapping("/create")
    @Operation(summary = "创建检测记录")
    public CommonResult<Long> createDetection(@Valid @RequestBody DetectionSaveReqVO createReqVO) {
        createReqVO.setUid(SecurityFrameworkUtils.getLoginUserId());
        return success(detectionService.createDetection(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检测记录")
    public CommonResult<Boolean> updateDetection(@Valid @RequestBody DetectionSaveReqVO updateReqVO) {
        detectionService.updateDetection(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检测记录")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteDetection(@RequestParam("id") Long id) {
        detectionService.deleteDetection(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检测记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<DetectionRespVO> getDetection(@RequestParam("id") Long id) {
        DetectionDO detection = detectionService.getDetection(id);
        DetectionRespVO detectionRespVO = BeanUtils.toBean(detection, DetectionRespVO.class);
        if(detectionRespVO.getAid() != null){
            ArchivesDO archives = archivesService.getArchives(detectionRespVO.getAid());
            detectionRespVO.setArchive(archives);
        }
        return success(detectionRespVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得检测记录分页")
    public CommonResult<PageResult<DetectionRespVO>> getDetectionPage(@Valid DetectionPageReqVO pageReqVO) {
        if(pageReqVO.getCreateTime() != null && pageReqVO.getCreateTime().length == 1){
            LocalDateTime[] createTime = new LocalDateTime[2];
            createTime[0] = pageReqVO.getCreateTime()[0];
            createTime[1] = pageReqVO.getCreateTime()[0].plusDays(1);
        }
        pageReqVO.setUid(SecurityFrameworkUtils.getLoginUserId());
        PageResult<DetectionDO> pageResult = detectionService.getDetectionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DetectionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检测记录 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDetectionExcel(@Valid DetectionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DetectionDO> list = detectionService.getDetectionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "检测记录.xls", "数据", DetectionRespVO.class,
                        BeanUtils.toBean(list, DetectionRespVO.class));
    }


    @GetMapping("/excelToPdf")
    @Operation(summary = "生成PDF")
    public void excelToPdf(@Valid DetectionRespVO respVO, HttpServletResponse response) throws IOException {

        List<DetectionDO> list = detectionService.getExcelList(respVO);

        ArchivesDO archives = archivesService.getArchives(respVO.getAid());

        String pdfName = detectionExcelUtil.writeExcel(list, archives);
        String filePath = detectionExcelUtil.getAbsoluteFile(pdfName);

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        detectionExcelUtil.setAttachmentResponseHeader(response, pdfName);
        detectionExcelUtil.uploadFile(filePath, response.getOutputStream());
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(pdfName, StandardCharsets.UTF_8.name()));
        response.setContentType("application/pdf;charset=UTF-8");

        detectionExcelUtil.deleteFile(filePath);

        System.out.println("");
    }

    @GetMapping("/getReportData")
    @Operation(summary = "获得检测报告")
    public CommonResult<DetectionRespVO> getReportData(@RequestParam("id") Long id) {
        DetectionDO detection = detectionService.getDetection(id);
        DetectionRespVO detectionRespVO = BeanUtils.toBean(detection, DetectionRespVO.class);
        if(detectionRespVO.getAid() != null){
            ArchivesDO archives = archivesService.getArchives(detectionRespVO.getAid());
            detectionRespVO.setArchive(archives);

            Map<String, Object> reportData = detectionService.getReportData(detection);
            detectionRespVO.setReportData(reportData);
        }
        return success(detectionRespVO);
    }

}
