package cn.metast.tuoke.module.heal.controller.admin.deviceuser;

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

import cn.metast.tuoke.module.heal.controller.admin.deviceuser.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.deviceuser.DeviceUserDO;
import cn.metast.tuoke.module.heal.service.deviceuser.DeviceUserService;

@Tag(name = "管理后台 - 设备绑定用户信息")
@RestController
@RequestMapping("/heal/device-user")
@Validated
public class DeviceUserController {

    @Resource
    private DeviceUserService deviceUserService;

    @PostMapping("/create")
    @Operation(summary = "创建设备绑定用户信息")
    @PreAuthorize("@ss.hasPermission('wennuan:device-user:create')")
    public CommonResult<Long> createDeviceUser(@Valid @RequestBody DeviceUserSaveReqVO createReqVO) {
        return success(deviceUserService.createDeviceUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备绑定用户信息")
    @PreAuthorize("@ss.hasPermission('wennuan:device-user:update')")
    public CommonResult<Boolean> updateDeviceUser(@Valid @RequestBody DeviceUserSaveReqVO updateReqVO) {
        deviceUserService.updateDeviceUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备绑定用户信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wennuan:device-user:delete')")
    public CommonResult<Boolean> deleteDeviceUser(@RequestParam("id") Long id) {
        deviceUserService.deleteDeviceUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备绑定用户信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wennuan:device-user:query')")
    public CommonResult<DeviceUserRespVO> getDeviceUser(@RequestParam("id") Long id) {
        DeviceUserDO deviceUser = deviceUserService.getDeviceUser(id);
        return success(BeanUtils.toBean(deviceUser, DeviceUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备绑定用户信息分页")
    @PreAuthorize("@ss.hasPermission('wennuan:device-user:query')")
    public CommonResult<PageResult<DeviceUserRespVO>> getDeviceUserPage(@Valid DeviceUserPageReqVO pageReqVO) {
        PageResult<DeviceUserDO> pageResult = deviceUserService.getDeviceUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeviceUserRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备绑定用户信息 Excel")
    @PreAuthorize("@ss.hasPermission('wennuan:device-user:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDeviceUserExcel(@Valid DeviceUserPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeviceUserDO> list = deviceUserService.getDeviceUserPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备绑定用户信息.xls", "数据", DeviceUserRespVO.class,
                        BeanUtils.toBean(list, DeviceUserRespVO.class));
    }

}
