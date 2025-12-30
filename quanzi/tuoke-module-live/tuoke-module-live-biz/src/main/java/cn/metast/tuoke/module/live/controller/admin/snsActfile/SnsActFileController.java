package cn.metast.tuoke.module.live.controller.admin.snsActfile;

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

import cn.metast.tuoke.module.live.controller.admin.snsActfile.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActfile.SnsActFileDO;
import cn.metast.tuoke.module.live.service.snsActfile.SnsActFileService;

@Tag(name = "管理后台 - 活动资料")
@RestController
@RequestMapping("/live/sns-act-file")
@Validated
public class SnsActFileController {

    @Resource
    private SnsActFileService snsActFileService;

    @PostMapping("/create")
    @Operation(summary = "创建活动资料")
    @PreAuthorize("@ss.hasPermission('live:sns-act-file:create')")
    public CommonResult<Long> createSnsActFile(@Valid @RequestBody SnsActFileSaveReqVO createReqVO) {
        return success(snsActFileService.createSnsActFile(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动资料")
    @PreAuthorize("@ss.hasPermission('live:sns-act-file:update')")
    public CommonResult<Boolean> updateSnsActFile(@Valid @RequestBody SnsActFileSaveReqVO updateReqVO) {
        snsActFileService.updateSnsActFile(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动资料")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-act-file:delete')")
    public CommonResult<Boolean> deleteSnsActFile(@RequestParam("id") Long id) {
        snsActFileService.deleteSnsActFile(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动资料")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-act-file:query')")
    public CommonResult<SnsActFileRespVO> getSnsActFile(@RequestParam("id") Long id) {
        SnsActFileDO snsActFile = snsActFileService.getSnsActFile(id);
        return success(BeanUtils.toBean(snsActFile, SnsActFileRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动资料分页")
    @PreAuthorize("@ss.hasPermission('live:sns-act-file:query')")
    public CommonResult<PageResult<SnsActFileRespVO>> getSnsActFilePage(@Valid SnsActFilePageReqVO pageReqVO) {
        PageResult<SnsActFileDO> pageResult = snsActFileService.getSnsActFilePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActFileRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动资料 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-act-file:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActFileExcel(@Valid SnsActFilePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActFileDO> list = snsActFileService.getSnsActFilePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动资料.xls", "数据", SnsActFileRespVO.class,
                        BeanUtils.toBean(list, SnsActFileRespVO.class));
    }

}