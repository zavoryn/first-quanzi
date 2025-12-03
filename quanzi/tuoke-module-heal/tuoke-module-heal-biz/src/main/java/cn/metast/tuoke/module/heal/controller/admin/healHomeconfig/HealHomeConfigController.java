package cn.metast.tuoke.module.heal.controller.admin.healHomeconfig;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import cn.metast.tuoke.module.heal.controller.admin.healHomeconfig.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healBomeconfig.HealHomeConfigDO;
import cn.metast.tuoke.module.heal.service.healBomeconfig.HealHomeConfigService;

@Tag(name = "管理后台 - 首页模块配置")
@RestController
@RequestMapping("/heal/home-config")
@Validated
public class HealHomeConfigController {

    @Resource
    private HealHomeConfigService homeConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建首页模块配置")
    //@PreAuthorize("@ss.hasPermission('heal:home-config:create')")
    public CommonResult<Long> createHomeConfig(@Valid @RequestBody HealHomeConfigSaveReqVO createReqVO) {
        return success(homeConfigService.createHomeConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新首页模块配置")
    //@PreAuthorize("@ss.hasPermission('heal:home-config:update')")
    public CommonResult<Boolean> updateHomeConfig(@Valid @RequestBody HealHomeConfigSaveReqVO updateReqVO) {
        homeConfigService.updateHomeConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除首页模块配置")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('heal:home-config:delete')")
    public CommonResult<Boolean> deleteHomeConfig(@RequestParam("id") Long id) {
        homeConfigService.deleteHomeConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得首页模块配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('heal:home-config:query')")
    public CommonResult<HealHomeConfigRespVO> getHomeConfig(@RequestParam("id") Long id) {
        HealHomeConfigDO homeConfig = homeConfigService.getHomeConfig(id);
        return success(BeanUtils.toBean(homeConfig, HealHomeConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得首页模块配置分页")
    //@PreAuthorize("@ss.hasPermission('heal:home-config:query')")
    public CommonResult<PageResult<HealHomeConfigRespVO>> getHomeConfigPage(@Valid HealHomeConfigPageReqVO pageReqVO) {
        PageResult<HealHomeConfigDO> pageResult = homeConfigService.getHomeConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HealHomeConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出首页模块配置 Excel")
    //@PreAuthorize("@ss.hasPermission('heal:home-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportHomeConfigExcel(@Valid HealHomeConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HealHomeConfigDO> list = homeConfigService.getHomeConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "首页模块配置.xls", "数据", HealHomeConfigRespVO.class,
                        BeanUtils.toBean(list, HealHomeConfigRespVO.class));
    }

}
