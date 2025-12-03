package cn.metast.tuoke.module.heal.controller.app;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.module.heal.controller.admin.healHomeconfig.vo.HealHomeConfigPageReqVO;
import cn.metast.tuoke.module.heal.controller.admin.healService.vo.HealServicePageReqVO;
import cn.metast.tuoke.module.heal.dal.dataobject.healBomeconfig.HealHomeConfigDO;
import cn.metast.tuoke.module.heal.service.healBomeconfig.HealHomeConfigService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.system.api.dict.DictDataApi;
import cn.metast.tuoke.module.system.api.dict.dto.DictDataRespDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Api("首页管理")
@RestController
@RequestMapping("/api/home")
public class MHomeController {

    @Resource
    private HealHomeConfigService homeConfigService;
    @Resource
    private DictDataApi dictDataApi;
    @ApiOperation("首页 列表")
    @GetMapping("/getHomeList")
    @PermitAll
    public CommonResult<List<HealHomeConfigDO>> getBannerList() {
        HealHomeConfigPageReqVO pageReqVO=new HealHomeConfigPageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HealHomeConfigDO> list = homeConfigService.getHomeConfigPage(pageReqVO).getList();
        return success(list);
    }
    //首页显示状态
    @GetMapping("/getPeizhiStatus")
    @PermitAll
    @Operation(summary = "首页状态")
    public CommonResult<String> getPeizhiStatus() {
        DictDataRespDTO dictDataRespDTO=dictDataApi.parseDictData("home_merchant","home_status");
        if(dictDataRespDTO!=null){
            return success(dictDataRespDTO.getValue());
        }
        return success("0");
    }
}
