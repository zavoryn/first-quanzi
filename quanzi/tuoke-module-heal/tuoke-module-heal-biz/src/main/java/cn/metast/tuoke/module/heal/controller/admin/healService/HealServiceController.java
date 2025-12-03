package cn.metast.tuoke.module.heal.controller.admin.healService;

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

import cn.metast.tuoke.module.heal.controller.admin.healService.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healService.HealServiceDO;
import cn.metast.tuoke.module.heal.service.healService.HealServiceService;

@Tag(name = "管理后台 - 服务列")
@RestController
@RequestMapping("/heal/service")
@Validated
public class HealServiceController {

    @Resource
    private HealServiceService serviceService;

    @PostMapping("/create")
    @Operation(summary = "创建服务列")
    //@PreAuthorize("@ss.hasPermission('heal:service:create')")
    public CommonResult<Long> createService(@Valid @RequestBody HealServiceSaveReqVO createReqVO) {
        return success(serviceService.createService(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新服务列")
    //@PreAuthorize("@ss.hasPermission('heal:service:update')")
    public CommonResult<Boolean> updateService(@Valid @RequestBody HealServiceSaveReqVO updateReqVO) {
        serviceService.updateService(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除服务列")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('heal:service:delete')")
    public CommonResult<Boolean> deleteService(@RequestParam("id") Long id) {
        serviceService.deleteService(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得服务列")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('heal:service:query')")
    public CommonResult<HealServiceRespVO> getService(@RequestParam("id") Long id) {
        HealServiceDO service = serviceService.getService(id);
        return success(BeanUtils.toBean(service, HealServiceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得服务列分页")
    //@PreAuthorize("@ss.hasPermission('heal:service:query')")
    public CommonResult<PageResult<HealServiceRespVO>> getServicePage(@Valid HealServicePageReqVO pageReqVO) {
        PageResult<HealServiceDO> pageResult = serviceService.getServicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HealServiceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出服务列 Excel")
    //@PreAuthorize("@ss.hasPermission('heal:service:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportServiceExcel(@Valid HealServicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HealServiceDO> list = serviceService.getServicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "服务列.xls", "数据", HealServiceRespVO.class,
                        BeanUtils.toBean(list, HealServiceRespVO.class));
    }

}
