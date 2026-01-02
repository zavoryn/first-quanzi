package cn.metast.tuoke.module.live.controller.admin.snsActnotice;

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

import cn.metast.tuoke.module.live.controller.admin.snsActnotice.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActnotice.SnsActNoticeDO;
import cn.metast.tuoke.module.live.service.snsActnotice.SnsActNoticeService;

@Tag(name = "管理后台 - 活动公告")
@RestController
@RequestMapping("/live/sns-act-notice")
@Validated
public class SnsActNoticeController {

    @Resource
    private SnsActNoticeService snsActNoticeService;

    @PostMapping("/create")
    @Operation(summary = "创建活动公告")
    @PreAuthorize("@ss.hasPermission('live:sns-act-notice:create')")
    public CommonResult<Long> createSnsActNotice(@Valid @RequestBody SnsActNoticeSaveReqVO createReqVO) {
        return success(snsActNoticeService.createSnsActNotice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动公告")
    @PreAuthorize("@ss.hasPermission('live:sns-act-notice:update')")
    public CommonResult<Boolean> updateSnsActNotice(@Valid @RequestBody SnsActNoticeSaveReqVO updateReqVO) {
        snsActNoticeService.updateSnsActNotice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动公告")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-act-notice:delete')")
    public CommonResult<Boolean> deleteSnsActNotice(@RequestParam("id") Long id) {
        snsActNoticeService.deleteSnsActNotice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动公告")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-act-notice:query')")
    public CommonResult<SnsActNoticeRespVO> getSnsActNotice(@RequestParam("id") Long id) {
        SnsActNoticeDO snsActNotice = snsActNoticeService.getSnsActNotice(id);
        return success(BeanUtils.toBean(snsActNotice, SnsActNoticeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动公告分页")
    @PreAuthorize("@ss.hasPermission('live:sns-act-notice:query')")
    public CommonResult<PageResult<SnsActNoticeRespVO>> getSnsActNoticePage(@Valid SnsActNoticePageReqVO pageReqVO) {
        PageResult<SnsActNoticeDO> pageResult = snsActNoticeService.getSnsActNoticePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActNoticeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动公告 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-act-notice:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActNoticeExcel(@Valid SnsActNoticePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActNoticeDO> list = snsActNoticeService.getSnsActNoticePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动公告.xls", "数据", SnsActNoticeRespVO.class,
                        BeanUtils.toBean(list, SnsActNoticeRespVO.class));
    }

}