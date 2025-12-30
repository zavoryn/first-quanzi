package cn.metast.tuoke.module.live.controller.admin.snsActinfotemplate;

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

import cn.metast.tuoke.module.live.controller.admin.snsActinfotemplate.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfotemplate.SnsActInfoTemplateDO;
import cn.metast.tuoke.module.live.service.snsActinfotemplate.SnsActInfoTemplateService;

@Tag(name = "管理后台 - 活动模板")
@RestController
@RequestMapping("/live/sns-act-info-template")
@Validated
public class SnsActInfoTemplateController {

    @Resource
    private SnsActInfoTemplateService snsActInfoTemplateService;

    @PostMapping("/create")
    @Operation(summary = "创建活动模板")
    //@PreAuthorize("@ss.hasPermission('live:sns-act-info-template:create')")
    public CommonResult<Long> createSnsActInfoTemplate(@Valid @RequestBody SnsActInfoTemplateSaveReqVO createReqVO) {
        return success(snsActInfoTemplateService.createSnsActInfoTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动模板")
    //@PreAuthorize("@ss.hasPermission('live:sns-act-info-template:update')")
    public CommonResult<Boolean> updateSnsActInfoTemplate(@Valid @RequestBody SnsActInfoTemplateSaveReqVO updateReqVO) {
        snsActInfoTemplateService.updateSnsActInfoTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动模板")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('live:sns-act-info-template:delete')")
    public CommonResult<Boolean> deleteSnsActInfoTemplate(@RequestParam("id") Long id) {
        snsActInfoTemplateService.deleteSnsActInfoTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('live:sns-act-info-template:query')")
    public CommonResult<SnsActInfoTemplateRespVO> getSnsActInfoTemplate(@RequestParam("id") Long id) {
        SnsActInfoTemplateDO snsActInfoTemplate = snsActInfoTemplateService.getSnsActInfoTemplate(id);
        return success(BeanUtils.toBean(snsActInfoTemplate, SnsActInfoTemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动模板分页")
    //@PreAuthorize("@ss.hasPermission('live:sns-act-info-template:query')")
    public CommonResult<PageResult<SnsActInfoTemplateRespVO>> getSnsActInfoTemplatePage(@Valid SnsActInfoTemplatePageReqVO pageReqVO) {
        PageResult<SnsActInfoTemplateDO> pageResult = snsActInfoTemplateService.getSnsActInfoTemplatePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActInfoTemplateRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动模板 Excel")
    //@PreAuthorize("@ss.hasPermission('live:sns-act-info-template:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActInfoTemplateExcel(@Valid SnsActInfoTemplatePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActInfoTemplateDO> list = snsActInfoTemplateService.getSnsActInfoTemplatePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动模板.xls", "数据", SnsActInfoTemplateRespVO.class,
                        BeanUtils.toBean(list, SnsActInfoTemplateRespVO.class));
    }

}
