package cn.metast.tuoke.module.live.controller.admin.snsActinfocfg;

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

import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfocfg.SnsActInfoCfgDO;
import cn.metast.tuoke.module.live.service.snsActinfocfg.SnsActInfoCfgService;

@Tag(name = "管理后台 - 报名填写信息设置")
@RestController
@RequestMapping("/live/sns-act-info-cfg")
@Validated
public class SnsActInfoCfgController {

    @Resource
    private SnsActInfoCfgService snsActInfoCfgService;

    @PostMapping("/create")
    @Operation(summary = "创建报名填写信息设置")
    @PreAuthorize("@ss.hasPermission('live:sns-act-info-cfg:create')")
    public CommonResult<Long> createSnsActInfoCfg(@Valid @RequestBody SnsActInfoCfgSaveReqVO createReqVO) {
        return success(snsActInfoCfgService.createSnsActInfoCfg(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报名填写信息设置")
    @PreAuthorize("@ss.hasPermission('live:sns-act-info-cfg:update')")
    public CommonResult<Boolean> updateSnsActInfoCfg(@Valid @RequestBody SnsActInfoCfgSaveReqVO updateReqVO) {
        snsActInfoCfgService.updateSnsActInfoCfg(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报名填写信息设置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('live:sns-act-info-cfg:delete')")
    public CommonResult<Boolean> deleteSnsActInfoCfg(@RequestParam("id") Long id) {
        snsActInfoCfgService.deleteSnsActInfoCfg(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报名填写信息设置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('live:sns-act-info-cfg:query')")
    public CommonResult<SnsActInfoCfgRespVO> getSnsActInfoCfg(@RequestParam("id") Long id) {
        SnsActInfoCfgDO snsActInfoCfg = snsActInfoCfgService.getSnsActInfoCfg(id);
        return success(BeanUtils.toBean(snsActInfoCfg, SnsActInfoCfgRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报名填写信息设置分页")
    @PreAuthorize("@ss.hasPermission('live:sns-act-info-cfg:query')")
    public CommonResult<PageResult<SnsActInfoCfgRespVO>> getSnsActInfoCfgPage(@Valid SnsActInfoCfgPageReqVO pageReqVO) {
        PageResult<SnsActInfoCfgDO> pageResult = snsActInfoCfgService.getSnsActInfoCfgPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsActInfoCfgRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报名填写信息设置 Excel")
    @PreAuthorize("@ss.hasPermission('live:sns-act-info-cfg:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsActInfoCfgExcel(@Valid SnsActInfoCfgPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActInfoCfgDO> list = snsActInfoCfgService.getSnsActInfoCfgPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "报名填写信息设置.xls", "数据", SnsActInfoCfgRespVO.class,
                        BeanUtils.toBean(list, SnsActInfoCfgRespVO.class));
    }

}