package cn.metast.tuoke.module.heal.controller.admin.healBanner;

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

import cn.metast.tuoke.module.heal.controller.admin.healBanner.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.healBanner.HealBannerDO;
import cn.metast.tuoke.module.heal.service.healBanner.HealBannerService;

@Tag(name = "管理后台 - 首页banner")
@RestController
@RequestMapping("/heal/banner")
@Validated
public class HealBannerController {

    @Resource
    private HealBannerService bannerService;

    @PostMapping("/create")
    @Operation(summary = "创建首页banner")
   //@PreAuthorize("@ss.hasPermission('heal:banner:create')")
    public CommonResult<Long> createBanner(@Valid @RequestBody HealBannerSaveReqVO createReqVO) {
        return success(bannerService.createBanner(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新首页banner")
    //@PreAuthorize("@ss.hasPermission('heal:banner:update')")
    public CommonResult<Boolean> updateBanner(@Valid @RequestBody HealBannerSaveReqVO updateReqVO) {
        bannerService.updateBanner(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除首页banner")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('heal:banner:delete')")
    public CommonResult<Boolean> deleteBanner(@RequestParam("id") Long id) {
        bannerService.deleteBanner(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得首页banner")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('heal:banner:query')")
    public CommonResult<HealBannerRespVO> getBanner(@RequestParam("id") Long id) {
        HealBannerDO banner = bannerService.getBanner(id);
        return success(BeanUtils.toBean(banner, HealBannerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得首页banner分页")
    //@PreAuthorize("@ss.hasPermission('heal:banner:query')")
    public CommonResult<PageResult<HealBannerRespVO>> getBannerPage(@Valid HealBannerPageReqVO pageReqVO) {
        PageResult<HealBannerDO> pageResult = bannerService.getBannerPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HealBannerRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出首页banner Excel")
    //@PreAuthorize("@ss.hasPermission('heal:banner:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBannerExcel(@Valid HealBannerPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HealBannerDO> list = bannerService.getBannerPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "首页banner.xls", "数据", HealBannerRespVO.class,
                        BeanUtils.toBean(list, HealBannerRespVO.class));
    }

}
