package cn.metast.tuoke.module.community.controller.admin.dialog;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.module.community.controller.admin.dialog.vo.DialogAnalysisPageReqVO;
import cn.metast.tuoke.module.community.controller.admin.dialog.vo.DialogAnalysisRespVO;
import cn.metast.tuoke.module.community.controller.admin.dialog.vo.DialogAnalysisSaveReqVO;
import cn.metast.tuoke.module.community.dal.dataobject.dialog.DialogAnalysisDO;
import cn.metast.tuoke.module.community.service.dialog.DialogAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 对话分析")
@RestController
@RequestMapping("/community/dialog-analysis")
@Validated
public class DialogAnalysisController {

    @Resource
    private DialogAnalysisService dialogAnalysisService;

    @PostMapping("/create")
    @Operation(summary = "创建对话分析")
    @PreAuthorize("@ss.hasPermission('community:dialog-analysis:create')")
    public CommonResult<Long> createDialogAnalysis(@Valid @RequestBody DialogAnalysisSaveReqVO createReqVO) {
        return success(dialogAnalysisService.createDialogAnalysis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新对话分析")
    @PreAuthorize("@ss.hasPermission('community:dialog-analysis:update')")
    public CommonResult<Boolean> updateDialogAnalysis(@Valid @RequestBody DialogAnalysisSaveReqVO updateReqVO) {
        dialogAnalysisService.updateDialogAnalysis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除对话分析")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('community:dialog-analysis:delete')")
    public CommonResult<Boolean> deleteDialogAnalysis(@RequestParam("id") Long id) {
        dialogAnalysisService.deleteDialogAnalysis(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得对话分析")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('community:dialog-analysis:query')")
    public CommonResult<DialogAnalysisRespVO> getDialogAnalysis(@RequestParam("id") Long id) {
        DialogAnalysisDO dialogAnalysis = dialogAnalysisService.getDialogAnalysis(id);
        return success(BeanUtils.toBean(dialogAnalysis, DialogAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得对话分析分页")
    @PreAuthorize("@ss.hasPermission('community:dialog-analysis:query')")
    public CommonResult<PageResult<DialogAnalysisRespVO>> getDialogAnalysisPage(@Valid DialogAnalysisPageReqVO pageReqVO) {
        PageResult<DialogAnalysisDO> pageResult = dialogAnalysisService.getDialogAnalysisPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DialogAnalysisRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出对话分析 Excel")
    @PreAuthorize("@ss.hasPermission('community:dialog-analysis:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDialogAnalysisExcel(@Valid DialogAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DialogAnalysisDO> list = dialogAnalysisService.getDialogAnalysisPage(pageReqVO).getList();
        ExcelUtils.write(response, "对话分析.xls", "数据", DialogAnalysisRespVO.class,
                        BeanUtils.toBean(list, DialogAnalysisRespVO.class));
    }

    @PostMapping("/import")
    @Operation(summary = "导入HTML聊天记录并分析")
    @PreAuthorize("@ss.hasPermission('community:dialog-analysis:create')")
    public CommonResult<Long> importAndAnalyze(
            @RequestParam("topicId") Long topicId,
            @RequestParam("htmlFile") MultipartFile htmlFile,
            @RequestParam(value = "audioFiles", required = false) List<MultipartFile> audioFiles) {
        return success(dialogAnalysisService.importAndAnalyze(topicId, htmlFile, audioFiles));
    }

    @PostMapping("/retry")
    @Operation(summary = "重新分析")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('community:dialog-analysis:update')")
    public CommonResult<Boolean> retryAnalyze(@RequestParam("id") Long id) {
        dialogAnalysisService.retryAnalyze(id);
        return success(true);
    }

}
