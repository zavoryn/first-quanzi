package cn.metast.tuoke.module.live.controller.admin.snsActnote;

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

import cn.metast.tuoke.module.live.controller.admin.snsActnote.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActnote.SnsActNoteDO;
import cn.metast.tuoke.module.live.service.snsActnote.SnsActNoteService;

@Tag(name = "管理后台 - 活动记录")
@RestController
@RequestMapping("/live/sns-act-note")
@Validated
public class SnsActNoteController {

    @Resource
    private SnsActNoteService snsActNoteService;

    @PostMapping("/create")
    @Operation(summary = "创建活动记录")
    @PreAuthorize("@ss.hasPermission('live:sns-act-note:create')")
    public CommonResult<Long> createSnsActNote(@Valid @RequestBody SnsActNoteSaveReqVO createReqVO) {
        return success(snsActNoteService.createSnsActNote(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动记录")
    @PreAuthorize("@ss.hasPermission('live:sns-act-note:update')")
    public CommonResult<Boolean> updateSnsActNote(@Valid @RequestBody SnsActNoteSaveReqVO updateReqVO) {
        snsActNoteService.updateSnsActNote(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-act-note:delete')")
    public CommonResult<Boolean> deleteSnsActNote(@RequestParam("id") Long id) {
        snsActNoteService.deleteSnsActNote(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-act-note:query')")
    public CommonResult<SnsActNoteRespVO> getSnsActNote(@RequestParam("id") Long id) {
        SnsActNoteDO snsActNote = snsActNoteService.getSnsActNote(id);
        return success(BeanUtils.toBean(snsActNote, SnsActNoteRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动记录分页")
    @PreAuthorize("@ss.hasPermission('live:sns-act-note:query')")
    public CommonResult<PageResult<SnsActNoteRespVO>> getSnsActNotePage(@Valid SnsActNotePageReqVO pageReqVO) {
        PageResult<SnsActNoteDO> pageResult = snsActNoteService.getSnsActNotePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActNoteRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动记录 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-act-note:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActNoteExcel(@Valid SnsActNotePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActNoteDO> list = snsActNoteService.getSnsActNotePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动记录.xls", "数据", SnsActNoteRespVO.class,
                        BeanUtils.toBean(list, SnsActNoteRespVO.class));
    }

}