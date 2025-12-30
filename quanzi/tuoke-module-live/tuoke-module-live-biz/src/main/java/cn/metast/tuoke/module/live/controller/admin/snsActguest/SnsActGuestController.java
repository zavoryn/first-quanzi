package cn.metast.tuoke.module.live.controller.admin.snsActguest;

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

import cn.metast.tuoke.module.live.controller.admin.snsActguest.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActguest.SnsActGuestDO;
import cn.metast.tuoke.module.live.service.snsActguest.SnsActGuestService;

@Tag(name = "管理后台 - 活动嘉宾")
@RestController
@RequestMapping("/live/sns-act-guest")
@Validated
public class SnsActGuestController {

    @Resource
    private SnsActGuestService snsActGuestService;

    @PostMapping("/create")
    @Operation(summary = "创建活动嘉宾")
    @PreAuthorize("@ss.hasPermission('live:sns-act-guest:create')")
    public CommonResult<Long> createSnsActGuest(@Valid @RequestBody SnsActGuestSaveReqVO createReqVO) {
        return success(snsActGuestService.createSnsActGuest(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动嘉宾")
    @PreAuthorize("@ss.hasPermission('live:sns-act-guest:update')")
    public CommonResult<Boolean> updateSnsActGuest(@Valid @RequestBody SnsActGuestSaveReqVO updateReqVO) {
        snsActGuestService.updateSnsActGuest(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动嘉宾")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-act-guest:delete')")
    public CommonResult<Boolean> deleteSnsActGuest(@RequestParam("id") Long id) {
        snsActGuestService.deleteSnsActGuest(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动嘉宾")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-act-guest:query')")
    public CommonResult<SnsActGuestRespVO> getSnsActGuest(@RequestParam("id") Long id) {
        SnsActGuestDO snsActGuest = snsActGuestService.getSnsActGuest(id);
        return success(BeanUtils.toBean(snsActGuest, SnsActGuestRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动嘉宾分页")
    @PreAuthorize("@ss.hasPermission('live:sns-act-guest:query')")
    public CommonResult<PageResult<SnsActGuestRespVO>> getSnsActGuestPage(@Valid SnsActGuestPageReqVO pageReqVO) {
        PageResult<SnsActGuestDO> pageResult = snsActGuestService.getSnsActGuestPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActGuestRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动嘉宾 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-act-guest:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActGuestExcel(@Valid SnsActGuestPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActGuestDO> list = snsActGuestService.getSnsActGuestPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动嘉宾.xls", "数据", SnsActGuestRespVO.class,
                        BeanUtils.toBean(list, SnsActGuestRespVO.class));
    }

}