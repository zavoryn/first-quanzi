package cn.metast.tuoke.module.live.controller.admin.snsAppconfig;

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

import cn.metast.tuoke.module.live.controller.admin.snsAppconfig.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAppconfig.SnsAppConfigDO;
import cn.metast.tuoke.module.live.service.snsAppconfig.SnsAppConfigService;

@Tag(name = "管理后台 - 配置")
@RestController
@RequestMapping("/live/sns-app-config")
@Validated
public class SnsAppConfigController {

    @Resource
    private SnsAppConfigService snsAppConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建配置")
    public CommonResult<Long> createSnsAppConfig(@Valid @RequestBody SnsAppConfigSaveReqVO createReqVO) {
        return success(snsAppConfigService.createSnsAppConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新配置")
    public CommonResult<Boolean> updateSnsAppConfig(@Valid @RequestBody SnsAppConfigSaveReqVO updateReqVO) {
        snsAppConfigService.updateSnsAppConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除配置")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteSnsAppConfig(@RequestParam("id") Long id) {
        snsAppConfigService.deleteSnsAppConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<SnsAppConfigRespVO> getSnsAppConfig(@RequestParam("id") Long id) {
        SnsAppConfigDO snsAppConfig = snsAppConfigService.getSnsAppConfig(id);
        return success(BeanUtils.toBean(snsAppConfig, SnsAppConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得配置分页")
    public CommonResult<PageResult<SnsAppConfigRespVO>> getSnsAppConfigPage(@Valid SnsAppConfigPageReqVO pageReqVO) {
        PageResult<SnsAppConfigDO> pageResult = snsAppConfigService.getSnsAppConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SnsAppConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出配置 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSnsAppConfigExcel(@Valid SnsAppConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsAppConfigDO> list = snsAppConfigService.getSnsAppConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "配置.xls", "数据", SnsAppConfigRespVO.class,
                        BeanUtils.toBean(list, SnsAppConfigRespVO.class));
    }

}
