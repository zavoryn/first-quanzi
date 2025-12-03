package cn.metast.tuoke.module.heal.controller.admin.healServiceconfig;

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

import cn.metast.tuoke.module.heal.controller.admin.healServiceconfig.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healServiceconfig.HealServiceConfigDO;
import cn.metast.tuoke.module.heal.service.healServiceconfig.HealServiceConfigService;

@Tag(name = "管理后台 - 服务配置")
@RestController
@RequestMapping("/heal/service-config")
@Validated
public class HealServiceConfigController {

    @Resource
    private HealServiceConfigService serviceConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建服务配置")
    //@PreAuthorize("@ss.hasPermission('heal:service-config:create')")
    public CommonResult<Long> createServiceConfig(@Valid @RequestBody HealServiceConfigSaveReqVO createReqVO) {
        return success(serviceConfigService.createServiceConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新服务配置")
    //@PreAuthorize("@ss.hasPermission('heal:service-config:update')")
    public CommonResult<Boolean> updateServiceConfig(@Valid @RequestBody HealServiceConfigSaveReqVO updateReqVO) {
        serviceConfigService.updateServiceConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除服务配置")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('heal:service-config:delete')")
    public CommonResult<Boolean> deleteServiceConfig(@RequestParam("id") Long id) {
        serviceConfigService.deleteServiceConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得服务配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('heal:service-config:query')")
    public CommonResult<HealServiceConfigRespVO> getServiceConfig(@RequestParam("id") Long id) {
        HealServiceConfigDO serviceConfig = serviceConfigService.getServiceConfig(id);
        return success(BeanUtils.toBean(serviceConfig, HealServiceConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得服务配置分页")
    //@PreAuthorize("@ss.hasPermission('heal:service-config:query')")
    public CommonResult<PageResult<HealServiceConfigRespVO>> getServiceConfigPage(@Valid HealServiceConfigPageReqVO pageReqVO) {
        PageResult<HealServiceConfigDO> pageResult = serviceConfigService.getServiceConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HealServiceConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出服务配置 Excel")
    //@PreAuthorize("@ss.hasPermission('heal:service-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportServiceConfigExcel(@Valid HealServiceConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HealServiceConfigDO> list = serviceConfigService.getServiceConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "服务配置.xls", "数据", HealServiceConfigRespVO.class,
                        BeanUtils.toBean(list, HealServiceConfigRespVO.class));
    }

}
