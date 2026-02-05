package cn.metast.tuoke.module.trade.controller.admin.order;

import cn.hutool.core.collection.CollUtil;
import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.trade.controller.admin.order.vo.*;
import cn.metast.tuoke.module.trade.convert.order.TradeOrderConvert;
import cn.metast.tuoke.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.metast.tuoke.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.metast.tuoke.module.trade.dal.dataobject.order.TradeOrderLogDO;
import cn.metast.tuoke.module.trade.service.order.TradeOrderLogService;
import cn.metast.tuoke.module.trade.service.order.TradeOrderQueryService;
import cn.metast.tuoke.module.trade.service.order.TradeOrderUpdateService;
import cn.metast.tuoke.module.trade.service.sync.OrderSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.IMPORT;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertList;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 交易订单")
@RestController
@RequestMapping("/trade/order")
@Validated
@Slf4j
public class TradeOrderController {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;
    @Resource
    private TradeOrderQueryService tradeOrderQueryService;
    @Resource
    private TradeOrderLogService tradeOrderLogService;

    @Resource
    private MemberUserApi memberUserApi;

    @Resource
    private OrderSyncService orderSyncService;
    @GetMapping("/page")
    @Operation(summary = "获得交易订单分页")
    @PreAuthorize("@ss.hasPermission('trade:order:query')")
    public CommonResult<PageResult<TradeOrderPageItemRespVO>> getOrderPage(TradeOrderPageReqVO reqVO) {
        // 查询订单
        PageResult<TradeOrderDO> pageResult = tradeOrderQueryService.getOrderPage(reqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }

        // 查询用户信息
        Set<Long> userIds = CollUtil.unionDistinct(convertList(pageResult.getList(), TradeOrderDO::getUserId),
                convertList(pageResult.getList(), TradeOrderDO::getBrokerageUserId, Objects::nonNull));
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(userIds);
        // 查询订单项
        List<TradeOrderItemDO> orderItems = tradeOrderQueryService.getOrderItemListByOrderId(
                convertSet(pageResult.getList(), TradeOrderDO::getId));
        // 最终组合
        return success(TradeOrderConvert.INSTANCE.convertPage(pageResult, orderItems, userMap));
    }

    @GetMapping("/syncTechProductOrder")
    @Operation(summary = "同步小鹅通订单")
    @PreAuthorize("@ss.hasPermission('trade:order:sync')")
    public CommonResult<Boolean> syncTechProductOrder() {
        orderSyncService.syncTechProductOrder();
        return success(true);
    }
    @GetMapping("/summary")
    @Operation(summary = "获得交易订单统计")
    @PreAuthorize("@ss.hasPermission('trade:order:query')")
    public CommonResult<TradeOrderSummaryRespVO> getOrderSummary(TradeOrderPageReqVO reqVO) {
        return success(tradeOrderQueryService.getOrderSummary(reqVO));
    }

    @GetMapping("/get-detail")
    @Operation(summary = "获得交易订单详情")
    @Parameter(name = "id", description = "订单编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('trade:order:query')")
    public CommonResult<TradeOrderDetailRespVO> getOrderDetail(@RequestParam("id") Long id) {
        // 查询订单
        TradeOrderDO order = tradeOrderQueryService.getOrder(id);
        if (order == null) {
            return success(null);
        }
        // 查询订单项
        List<TradeOrderItemDO> orderItems = tradeOrderQueryService.getOrderItemListByOrderId(id);

        // 拼接数据
        MemberUserRespDTO user = memberUserApi.getUser(order.getUserId());
        MemberUserRespDTO brokerageUser = order.getBrokerageUserId() != null ?
                memberUserApi.getUser(order.getBrokerageUserId()) : null;
        List<TradeOrderLogDO> orderLogs = tradeOrderLogService.getOrderLogListByOrderId(id);
        return success(TradeOrderConvert.INSTANCE.convert(order, orderItems, orderLogs, user, brokerageUser));
    }

    @GetMapping("/get-express-track-list")
    @Operation(summary = "获得交易订单的物流轨迹")
    @Parameter(name = "id", description = "交易订单编号")
    @PreAuthorize("@ss.hasPermission('trade:order:query')")
    public CommonResult<List<?>> getOrderExpressTrackList(@RequestParam("id") Long id) {
        return success(TradeOrderConvert.INSTANCE.convertList02(
                tradeOrderQueryService.getExpressTrackList(id)));
    }

    @PutMapping("/delivery")
    @Operation(summary = "订单发货")
    @PreAuthorize("@ss.hasPermission('trade:order:update')")
    public CommonResult<Boolean> deliveryOrder(@RequestBody TradeOrderDeliveryReqVO deliveryReqVO) {
        tradeOrderUpdateService.deliveryOrder(deliveryReqVO);
        return success(true);
    }

    @PutMapping("/update-remark")
    @Operation(summary = "订单备注")
    @PreAuthorize("@ss.hasPermission('trade:order:update')")
    public CommonResult<Boolean> updateOrderRemark(@RequestBody TradeOrderRemarkReqVO reqVO) {
        tradeOrderUpdateService.updateOrderRemark(reqVO);
        return success(true);
    }

    @PutMapping("/update-price")
    @Operation(summary = "订单调价")
    @PreAuthorize("@ss.hasPermission('trade:order:update')")
    public CommonResult<Boolean> updateOrderPrice(@RequestBody TradeOrderUpdatePriceReqVO reqVO) {
        tradeOrderUpdateService.updateOrderPrice(reqVO);
        return success(true);
    }

    @PutMapping("/update-address")
    @Operation(summary = "修改订单收货地址")
    @PreAuthorize("@ss.hasPermission('trade:order:update')")
    public CommonResult<Boolean> updateOrderAddress(@RequestBody TradeOrderUpdateAddressReqVO reqVO) {
        tradeOrderUpdateService.updateOrderAddress(reqVO);
        return success(true);
    }

    @PutMapping("/pick-up-by-id")
    @Operation(summary = "订单核销")
    @Parameter(name = "id", description = "交易订单编号")
    @PreAuthorize("@ss.hasPermission('trade:order:pick-up')")
    public CommonResult<Boolean> pickUpOrderById(@RequestParam("id") Long id) {
        tradeOrderUpdateService.pickUpOrderByAdmin(getLoginUserId(), id);
        return success(true);
    }

    @PutMapping("/pick-up-by-verify-code")
    @Operation(summary = "订单核销")
    @Parameter(name = "pickUpVerifyCode", description = "自提核销码")
    @PreAuthorize("@ss.hasPermission('trade:order:pick-up')")
    public CommonResult<Boolean> pickUpOrderByVerifyCode(@RequestParam("pickUpVerifyCode") String pickUpVerifyCode) {
        tradeOrderUpdateService.pickUpOrderByAdmin(getLoginUserId(), pickUpVerifyCode);
        return success(true);
    }

    @GetMapping("/get-by-pick-up-verify-code")
    @Operation(summary = "查询核销码对应的订单")
    @Parameter(name = "pickUpVerifyCode", description = "自提核销码")
    @PreAuthorize("@ss.hasPermission('trade:order:query')")
    public CommonResult<TradeOrderDetailRespVO> getByPickUpVerifyCode(@RequestParam("pickUpVerifyCode") String pickUpVerifyCode) {
        TradeOrderDO tradeOrder = tradeOrderUpdateService.getByPickUpVerifyCode(pickUpVerifyCode);
        return success(TradeOrderConvert.INSTANCE.convert2(tradeOrder, null));
    }

    @GetMapping("/export")
    @Operation(summary = "导出订单")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrderList(@Validated TradeOrderPageReqVO exportReqVO,
                               HttpServletResponse response) throws IOException {
        // 查询订单
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TradeOrderDO> pageResult = tradeOrderQueryService.getOrderPage(exportReqVO);
        if (CollUtil.isNotEmpty(pageResult.getList())) {
            // 查询用户信息
            Set<Long> userIds = CollUtil.unionDistinct(convertList(pageResult.getList(), TradeOrderDO::getUserId),
                    convertList(pageResult.getList(), TradeOrderDO::getBrokerageUserId, Objects::nonNull));
            Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(userIds);
            // 查询订单项
            List<TradeOrderItemDO> orderItems = tradeOrderQueryService.getOrderItemListByOrderId(
                    convertSet(pageResult.getList(), TradeOrderDO::getId));
            List<TradeOrderPageItemRespVO> appTradeOrderPageItemRespVOPageResult =TradeOrderConvert.INSTANCE.convertPage(pageResult, orderItems, userMap).getList();

            // 输出 Excel
            ExcelUtils.write(response, "订单数据.xls", "数据", TradeOrderRespVO.class,
                    BeanUtils.toBean(appTradeOrderPageItemRespVOPageResult, TradeOrderRespVO.class));
        }
    }

    @GetMapping("/exportTemplate")
    @Operation(summary = "导出发货订单")
    @ApiAccessLog(operateType = EXPORT)
    @PermitAll
    public void exportShipmentTemplate(@Validated TradeOrderPageReqVO exportReqVO,
                               HttpServletResponse response) throws IOException {
        // 查询订单
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<TradeOrderDO> pageResult = tradeOrderQueryService.getOrderPage(exportReqVO);
        if (CollUtil.isNotEmpty(pageResult.getList())) {
            // 查询用户信息
            Set<Long> userIds = CollUtil.unionDistinct(convertList(pageResult.getList(), TradeOrderDO::getUserId),
                    convertList(pageResult.getList(), TradeOrderDO::getBrokerageUserId, Objects::nonNull));
            Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(userIds);
            // 查询订单项
            List<TradeOrderItemDO> orderItems = tradeOrderQueryService.getOrderItemListByOrderId(
                    convertSet(pageResult.getList(), TradeOrderDO::getId));
            List<TradeOrderPageItemRespVO> appTradeOrderPageItemRespVOPageResult =TradeOrderConvert.INSTANCE.convertPage(pageResult, orderItems, userMap).getList();

            // 输出 Excel
            ExcelUtils.write(response, "订单数据.xls", "数据", TradeOrderShipmentRespVO.class,
                    BeanUtils.toBean(appTradeOrderPageItemRespVOPageResult, TradeOrderShipmentRespVO.class));
        }
    }



    @PostMapping("/importShipmentDate")
    @Operation(summary = "批量发货导入")
    @ApiAccessLog(operateType = IMPORT)
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    public CommonResult<TradeOrderImportRespVO> exportSpuList(@RequestParam("file") MultipartFile file,
                                                              @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {

        List<TradeOrderShipmentRespVO> list = ExcelUtils.read(file, TradeOrderShipmentRespVO.class);
        return CommonResult.success(tradeOrderUpdateService.importShipmentDate(list, updateSupport));
    }

}
