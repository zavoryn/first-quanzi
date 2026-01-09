package cn.metast.tuoke.module.product.controller.admin.livegoods;

import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsPageReqVO;
import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsRespVO;
import cn.metast.tuoke.module.product.controller.admin.livegoods.vo.LiveGoodsSaveReqVO;
import cn.metast.tuoke.module.product.dal.dataobject.livegoods.LiveGoodsDO;
import cn.metast.tuoke.module.product.service.livegoods.LiveGoodsService;
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

@Tag(name = "管理后台 - 直播商品列")
@RestController
@RequestMapping("/shop/live-goods")
@Validated
public class LiveGoodsController {

    @Resource
    private LiveGoodsService liveGoodsService;

    @PostMapping("/create")
    @Operation(summary = "创建直播商品列")
    @PreAuthorize("@ss.hasPermission('shop:live-goods:create')")
    public CommonResult<Long> createLiveGoods(@Valid @RequestBody LiveGoodsSaveReqVO createReqVO) {
        return success(liveGoodsService.createLiveGoods(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新直播商品列")
    @PreAuthorize("@ss.hasPermission('shop:live-goods:update')")
    public CommonResult<Boolean> updateLiveGoods(@Valid @RequestBody LiveGoodsSaveReqVO updateReqVO) {
        liveGoodsService.updateLiveGoods(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除直播商品列")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('shop:live-goods:delete')")
    public CommonResult<Boolean> deleteLiveGoods(@RequestParam("id") Long id) {
        liveGoodsService.deleteLiveGoods(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得直播商品列")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('shop:live-goods:query')")
    public CommonResult<LiveGoodsRespVO> getLiveGoods(@RequestParam("id") Long id) {
        LiveGoodsDO liveGoods = liveGoodsService.getLiveGoods(id);
        return success(BeanUtils.toBean(liveGoods, LiveGoodsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得直播商品列分页")
    @PreAuthorize("@ss.hasPermission('shop:live-goods:query')")
    public CommonResult<PageResult<LiveGoodsRespVO>> getLiveGoodsPage(@Valid LiveGoodsPageReqVO pageReqVO) {
        PageResult<LiveGoodsDO> pageResult = liveGoodsService.getLiveGoodsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, LiveGoodsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出直播商品列 Excel")
    @PreAuthorize("@ss.hasPermission('shop:live-goods:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportLiveGoodsExcel(@Valid LiveGoodsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<LiveGoodsDO> list = liveGoodsService.getLiveGoodsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "直播商品列.xls", "数据", LiveGoodsRespVO.class,
                        BeanUtils.toBean(list, LiveGoodsRespVO.class));
    }

}
