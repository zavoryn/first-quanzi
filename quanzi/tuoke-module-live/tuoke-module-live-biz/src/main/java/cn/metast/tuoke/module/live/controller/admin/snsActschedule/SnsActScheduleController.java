package cn.metast.tuoke.module.live.controller.admin.snsActschedule;

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

import cn.metast.tuoke.module.live.controller.admin.snsActschedule.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActschedule.SnsActScheduleDO;
import cn.metast.tuoke.module.live.service.snsActschedule.SnsActScheduleService;

@Tag(name = "管理后台 - 活动日程")
@RestController
@RequestMapping("/live/sns-act-schedule")
@Validated
public class SnsActScheduleController {

    @Resource
    private SnsActScheduleService snsActScheduleService;

    @PostMapping("/create")
    @Operation(summary = "创建活动日程")
    @PreAuthorize("@ss.hasPermission('live:sns-act-schedule:create')")
    public CommonResult<Long> createSnsActSchedule(@Valid @RequestBody SnsActScheduleSaveReqVO createReqVO) {
        return success(snsActScheduleService.createSnsActSchedule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动日程")
    @PreAuthorize("@ss.hasPermission('live:sns-act-schedule:update')")
    public CommonResult<Boolean> updateSnsActSchedule(@Valid @RequestBody SnsActScheduleSaveReqVO updateReqVO) {
        snsActScheduleService.updateSnsActSchedule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动日程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-act-schedule:delete')")
    public CommonResult<Boolean> deleteSnsActSchedule(@RequestParam("id") Long id) {
        snsActScheduleService.deleteSnsActSchedule(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动日程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-act-schedule:query')")
    public CommonResult<SnsActScheduleRespVO> getSnsActSchedule(@RequestParam("id") Long id) {
        SnsActScheduleDO snsActSchedule = snsActScheduleService.getSnsActSchedule(id);
        return success(BeanUtils.toBean(snsActSchedule, SnsActScheduleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动日程分页")
    @PreAuthorize("@ss.hasPermission('live:sns-act-schedule:query')")
    public CommonResult<PageResult<SnsActScheduleRespVO>> getSnsActSchedulePage(@Valid SnsActSchedulePageReqVO pageReqVO) {
        PageResult<SnsActScheduleDO> pageResult = snsActScheduleService.getSnsActSchedulePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActScheduleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动日程 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-act-schedule:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActScheduleExcel(@Valid SnsActSchedulePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActScheduleDO> list = snsActScheduleService.getSnsActSchedulePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动日程.xls", "数据", SnsActScheduleRespVO.class,
                        BeanUtils.toBean(list, SnsActScheduleRespVO.class));
    }

}