package cn.metast.tuoke.module.heal.controller.app;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.module.heal.controller.admin.healBanner.vo.HealBannerPageReqVO;
import cn.metast.tuoke.module.heal.dal.dataobject.healBanner.HealBannerDO;
import cn.metast.tuoke.module.heal.service.healBanner.HealBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Api("banner管理")
@RestController
@RequestMapping("/api/banner")
public class MBannerController{

    @Autowired
    private HealBannerService bannerService;

    @ApiOperation("banner 列表")
    @GetMapping("/getBannerList")
    @PermitAll
    public CommonResult<List<HealBannerDO>> getBannerList() {
        HealBannerPageReqVO pageReqVO=new HealBannerPageReqVO();
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HealBannerDO> list = bannerService.getBannerPage(pageReqVO).getList();
        return success(list);
    }
}
