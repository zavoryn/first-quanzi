package cn.metast.tuoke.module.heal.controller.app;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.heal.controller.admin.device.vo.WnDevicePageReqVO;
import cn.metast.tuoke.module.heal.controller.admin.device.vo.WnDeviceRespVO;
import cn.metast.tuoke.module.heal.controller.admin.device.vo.WnDeviceSaveReqVO;
import cn.metast.tuoke.module.heal.dal.dataobject.device.DeviceDO;
import cn.metast.tuoke.module.heal.service.device.WnDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 设备信息")
@RestController
@RequestMapping("/heal/device")
public class AppDeviceController {

    @Resource
    private WnDeviceService wnDeviceService;

    @PostMapping("/create")
    @Operation(summary = "创建设备信息")
    public CommonResult<Long> createDevice(@Valid @RequestBody WnDeviceSaveReqVO createReqVO) {
        createReqVO.setUid(SecurityFrameworkUtils.getLoginUserId());
        return success(wnDeviceService.createDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备信息")
    public CommonResult<Boolean> updateDevice(@Valid @RequestBody WnDeviceSaveReqVO updateReqVO) {
        wnDeviceService.updateDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteDevice(@RequestParam("id") Long id) {
        wnDeviceService.deleteDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<WnDeviceRespVO> getDevice(@RequestParam("id") Long id) {
        DeviceDO device = wnDeviceService.getDevice(id);
        return success(BeanUtils.toBean(device, WnDeviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备信息分页")
    public CommonResult<PageResult<WnDeviceRespVO>> getDevicePage(@Valid WnDevicePageReqVO pageReqVO) {
        pageReqVO.setUid(SecurityFrameworkUtils.getLoginUserId());
        PageResult<DeviceDO> pageResult = wnDeviceService.getDevicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WnDeviceRespVO.class));
    }


}
