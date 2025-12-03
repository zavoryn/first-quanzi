package cn.metast.tuoke.module.heal.controller.app;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.heal.controller.admin.healService.vo.HealServicePageReqVO;
import cn.metast.tuoke.module.heal.controller.admin.healService.vo.HealServiceRespVO;
import cn.metast.tuoke.module.heal.dal.dataobject.healService.HealServiceDO;
import cn.metast.tuoke.module.heal.service.healService.HealServiceService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Api("服务管理")
@RestController
@RequestMapping("/api/service")
public class MServiceController {
    @Resource
    private HealServiceService serviceService;
    @GetMapping("/getServiceList")
    @PermitAll
    @Operation(summary = "获得服务列分页")
    public CommonResult<PageResult<HealServiceRespVO>> getServicePage(@Valid HealServicePageReqVO pageReqVO) {
        PageResult<HealServiceDO> pageResult = serviceService.getServicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HealServiceRespVO.class));
    }
    @GetMapping("/getServiceId")
    @Operation(summary = "获得服务列分页")
    @PermitAll
    public CommonResult<HealServiceRespVO> getServiceId(@RequestParam("id") Long id) {
        HealServiceDO service = serviceService.getService(id);
        return success(BeanUtils.toBean(service, HealServiceRespVO.class));
    }
}
