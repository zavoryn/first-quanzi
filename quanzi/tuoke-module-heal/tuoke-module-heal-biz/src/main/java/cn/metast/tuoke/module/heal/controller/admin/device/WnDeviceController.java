package cn.metast.tuoke.module.heal.controller.admin.device;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
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

import cn.metast.tuoke.module.heal.controller.admin.device.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.device.DeviceDO;
import cn.metast.tuoke.module.heal.service.device.WnDeviceService;

@Tag(name = "管理后台 - 设备信息")
@RestController
@RequestMapping("/heal/device")
@Validated
public class WnDeviceController {

    @Resource
    private WnDeviceService wnDeviceService;

    @PostMapping("/create")
    @Operation(summary = "创建设备信息")
    @PreAuthorize("@ss.hasPermission('wennuan:device:create')")
    public CommonResult<Long> createDevice(@Valid @RequestBody WnDeviceSaveReqVO createReqVO) {
        return success(wnDeviceService.createDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备信息")
    @PreAuthorize("@ss.hasPermission('wennuan:device:update')")
    public CommonResult<Boolean> updateDevice(@Valid @RequestBody WnDeviceSaveReqVO updateReqVO) {
        wnDeviceService.updateDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wennuan:device:delete')")
    public CommonResult<Boolean> deleteDevice(@RequestParam("id") Long id) {
        wnDeviceService.deleteDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wennuan:device:query')")
    public CommonResult<WnDeviceRespVO> getDevice(@RequestParam("id") Long id) {
        DeviceDO device = wnDeviceService.getDevice(id);
        return success(BeanUtils.toBean(device, WnDeviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备信息分页")
    @PreAuthorize("@ss.hasPermission('wennuan:device:query')")
    public CommonResult<PageResult<WnDeviceRespVO>> getDevicePage(@Valid WnDevicePageReqVO pageReqVO) {
        PageResult<DeviceDO> pageResult = wnDeviceService.getDevicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WnDeviceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备信息 Excel")
    @PreAuthorize("@ss.hasPermission('wennuan:device:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDeviceExcel(@Valid WnDevicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeviceDO> list = wnDeviceService.getDevicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备信息.xls", "数据", WnDeviceRespVO.class,
                        BeanUtils.toBean(list, WnDeviceRespVO.class));
    }

}
