package cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate;

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

import cn.metast.tuoke.module.mp.controller.admin.mpTasktemplate.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTasktemplate.MpTaskTemplateDO;
import cn.metast.tuoke.module.mp.service.mpTasktemplate.MpTaskTemplateService;

@Tag(name = "管理后台 - 公众号模板")
@RestController
@RequestMapping("/mp/task-template")
@Validated
public class MpTaskTemplateController {

    @Resource
    private MpTaskTemplateService taskTemplateService;

    @PostMapping("/create")
    @Operation(summary = "创建公众号模板")
    //@PreAuthorize("@ss.hasPermission('mp:task-template:create')")
    public CommonResult<Long> createTaskTemplate(@Valid @RequestBody MpTaskTemplateSaveReqVO createReqVO) {
        return success(taskTemplateService.createTaskTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公众号模板")
    //@PreAuthorize("@ss.hasPermission('mp:task-template:update')")
    public CommonResult<Boolean> updateTaskTemplate(@Valid @RequestBody MpTaskTemplateSaveReqVO updateReqVO) {
        taskTemplateService.updateTaskTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公众号模板")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('mp:task-template:delete')")
    public CommonResult<Boolean> deleteTaskTemplate(@RequestParam("id") Long id) {
        taskTemplateService.deleteTaskTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公众号模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
   //@PreAuthorize("@ss.hasPermission('mp:task-template:query')")
    public CommonResult<MpTaskTemplateRespVO> getTaskTemplate(@RequestParam("id") Long id) {
        MpTaskTemplateDO taskTemplate = taskTemplateService.getTaskTemplate(id);
        return success(BeanUtils.toBean(taskTemplate, MpTaskTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公众号模板分页")
    //@PreAuthorize("@ss.hasPermission('mp:task-template:query')")
    public CommonResult<PageResult<MpTaskTemplateRespVO>> getTaskTemplatePage(@Valid MpTaskTemplatePageReqVO pageReqVO) {
        PageResult<MpTaskTemplateDO> pageResult = taskTemplateService.getTaskTemplatePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MpTaskTemplateRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公众号模板 Excel")
    //@PreAuthorize("@ss.hasPermission('mp:task-template:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTaskTemplateExcel(@Valid MpTaskTemplatePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MpTaskTemplateDO> list = taskTemplateService.getTaskTemplatePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "公众号模板.xls", "数据", MpTaskTemplateRespVO.class,
                        BeanUtils.toBean(list, MpTaskTemplateRespVO.class));
    }

}
