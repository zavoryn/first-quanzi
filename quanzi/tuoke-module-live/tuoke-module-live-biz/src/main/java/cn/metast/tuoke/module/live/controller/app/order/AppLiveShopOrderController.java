package cn.metast.tuoke.module.live.controller.app.order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.live.HttpNone;
import cn.metast.tuoke.framework.common.live.HttpRet;
import cn.metast.tuoke.framework.common.live.HttpRetArr;
import cn.metast.tuoke.framework.common.util.number.MoneyUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.live.controller.app.cart.vo.ShopCarAskDTO;
import cn.metast.tuoke.module.live.controller.app.order.vo.*;
import cn.metast.tuoke.module.member.api.address.MemberAddressApi;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.product.api.property.dto.ProductPropertyValueDetailRespDTO;
import cn.metast.tuoke.module.product.api.sku.ProductSkuApi;
import cn.metast.tuoke.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.metast.tuoke.module.product.api.spu.ProductSpuApi;
import cn.metast.tuoke.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.metast.tuoke.module.system.api.dict.DictDataApi;
import cn.metast.tuoke.module.system.api.dict.dto.DictDataRespDTO;
import cn.metast.tuoke.module.trade.api.cart.TradeCartApi;
import cn.metast.tuoke.module.trade.api.cart.dto.TradeCartRespDTO;
import cn.metast.tuoke.module.trade.api.order.TradeOrderApi;
import cn.metast.tuoke.module.trade.api.order.dto.TradeOrderItemRespDTO;
import cn.metast.tuoke.module.trade.api.order.dto.TradeOrderRespDTO;
import cn.metast.tuoke.module.trade.enums.order.TradeOrderStatusEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "移动端 直播 - 购物车")
@RestController
@RequestMapping("/api/shopOrder")
@Slf4j
public class AppLiveShopOrderController {

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private MemberAddressApi memberAddressApi;

    @Resource
    private TradeCartApi catrApi;

    @Resource
    private TradeOrderApi orderApi;

    @Resource
    private ProductSpuApi prproductSpuApi;
    @Resource
    private ProductSkuApi prproductSkuApi;

    @Resource
    private DictDataApi dictDataApi;


    @PostMapping("/getUserOrderList")
    @Operation(summary = "直播-获取用户订单列表")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", required = false),
            @ApiImplicitParam(name = "status", value = "订单状态 0:全部 1:待付款 2:待发货 3:待收货 4:订单完成", dataType = "int", required = true),
            @ApiImplicitParam(name = "quitStatus", value = "退款订单状态 0:全部 1:待审核 2:待发货 3:待收货 4:退款中 5:退款完成 6:退款失败", dataType = "int", required = true),
    })
    public HttpRetArr<ShopUserOrderDTO> getUserOrderList(int pageIndex, int pageSize, int status, int quitStatus) {

        List<ShopUserOrderDTO> list = new ArrayList<>();

        List<TradeOrderRespDTO> orderList = orderApi.getOrderList_live(pageIndex, pageSize, status, getLoginUserId());
        if (orderList.size() == 0) {
            return HttpRetArr.success("当前没有订单", list);
        }


        List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
        Map<String, String> merchantMap = dictDataList.stream()
                .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));

        for(TradeOrderRespDTO order : orderList){
            ShopUserOrderDTO dto = orderToDTO(order, merchantMap);
            list.add(dto);
        }

        return HttpRetArr.success("获取成功", list);
    }


    @PostMapping("/getUserOrderDetail")
    @Operation(summary = "直播-获取订单详情")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessOrderId", value = "商家订单id", dataType = "long", required = true),
    })
    public HttpRet<ShopUserOrderDetailDTO> getUserOrderDetail(long businessOrderId) {

        List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
        Map<String, String> merchantMap = dictDataList.stream()
                .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));

        TradeOrderRespDTO order = orderApi.getOrder(businessOrderId);
        ShopUserOrderDetailDTO shopUserOrderDetailDTO = orderToUserOrderDTO(order, merchantMap);

        return HttpRet.success("获取成功", shopUserOrderDetailDTO);
    }


    @PostMapping("/confirmReceipt")
    @Operation(summary = "直播-确认收货")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessOrderId", value = "商家订单id", dataType = "long", required = false),
    })
    public HttpRet<HttpNone> confirmReceipt(long businessOrderId) {

        int i = orderApi.confirmReceipt(businessOrderId);

        return HttpRet.success("修改成功");
    }


    @PostMapping("/updateOrderAddress")
    @Operation(summary = "直播-修改订单地址信息")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessOrderId", value = "商家订单id", dataType = "long", required = true),
            @ApiImplicitParam(name = "addressId", value = "订单地址id", dataType = "long", required = true),
    })
    public HttpRet<HttpNone> updateOrderAddress(long businessOrderId, long addressId) {

        orderApi.updateOrderAddress(businessOrderId, addressId);

        return HttpRet.success("取消成功");
    }


    @PostMapping("/cancelOrder")
    @Operation(summary = "直播-取消订单")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessOrderId", value = "商家订单id", dataType = "long", required = true),
    })
    public HttpRet<HttpNone> cancelOrder(long businessOrderId) {

        orderApi.cancelOrder(getLoginUserId(), businessOrderId);

        return HttpRet.success("取消成功");
    }


    @PostMapping("/getOrderNum")
    @Operation(summary = "直播-获取订单数量")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1:买家 2:卖家", dataType = "int", required = true)
    })
    public HttpRet<ShopOrderNumDTO> getOrderNum(int type) {

        Map<String, Long> orderNum = orderApi.getOrderNum(type);

        ShopOrderNumDTO shopOrderNumDTO = new ShopOrderNumDTO();
        shopOrderNumDTO.setToBePayNum(Integer.valueOf(orderNum.get("unpaidCount").toString()));
        shopOrderNumDTO.setToBeDeliveredNum(Integer.valueOf(orderNum.get("undeliveredCount").toString()));
        shopOrderNumDTO.setToBeReceivedNum(Integer.valueOf(orderNum.get("deliveredCount").toString()));
        shopOrderNumDTO.setCancelGoodsNum(Integer.valueOf(orderNum.get("afterSaleCount").toString()));
        shopOrderNumDTO.setFinishedNum(Integer.valueOf(orderNum.get("uncommentedCount").toString()));

        return HttpRet.success("获取成功", shopOrderNumDTO);
    }



    public ShopUserOrderDTO orderToDTO(TradeOrderRespDTO order, Map<String, String> merchantMap) {

        if(order == null){
            return null;
        }

        ShopUserOrderDTO dto = new ShopUserOrderDTO();
        dto.setAnchorId(getLoginUserId());
        dto.setGoodsNum(order.getProductCount());
        dto.setLogisticsNum(order.getLogisticsNo());

        List<TradeOrderItemRespDTO> items = order.getItems();
        List<ShopSubOrder> subOrderList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 订单状态 1：待付款 2：待发货 3：待收货 4：已完成 5：已取消
        int status = 1;
        if(order.getStatus() == TradeOrderStatusEnum.UNDELIVERED.getStatus()){
            status = 2;
        } else if(order.getStatus() == TradeOrderStatusEnum.DELIVERED.getStatus()){
            status = 3;
        } else if(order.getStatus() == TradeOrderStatusEnum.COMPLETED.getStatus()){
            status = 4;
        } else if(order.getStatus() == TradeOrderStatusEnum.CANCELED.getStatus()){
            status = 5;
        }

        for(TradeOrderItemRespDTO item : items){
            ShopSubOrder subOrder = new ShopSubOrder();
            subOrder.setId(item.getId());
            subOrder.setPayId(order.getPayOrderId());
            subOrder.setBusinessId(Long.valueOf(merchantMap.get("businessId")));
            subOrder.setBusinessOrderId(order.getId());
            subOrder.setOrderNum(order.getNo());
            subOrder.setGoodsId(item.getSpuId());
            subOrder.setGoodsName(item.getSpuName());
            subOrder.setGoodsPrice(MoneyUtils.fenToYuan(item.getPrice()).doubleValue());
            subOrder.setGoodsPicture(item.getPicUrl());
            subOrder.setStatus(status);
            subOrder.setGoodsNum(item.getCount());
            subOrder.setComposeId(item.getSkuId());
            subOrder.setSkuName(item.getSpuName());
            subOrder.setLogisticsNum(order.getLogisticsNo());
            subOrder.setAddTime(order.getCreateTime() != null ? order.getCreateTime().format(formatter) : null);

            subOrderList.add(subOrder);
        }

        ShopBusinessOrder businessOrder = new ShopBusinessOrder();
        businessOrder.setId(order.getId());
        businessOrder.setPayId(order.getPayOrderId());
        businessOrder.setUid(getLoginUserId());
        businessOrder.setBusinessId(Long.valueOf(merchantMap.get("businessId")));
        businessOrder.setBusinessName(merchantMap.get("businessName"));
        businessOrder.setBusinessLogo(merchantMap.get("businessLogo"));
        businessOrder.setOrderNum(order.getNo());
//        businessOrder.setPlatformPayOrder(order.getPayOrderId());
        businessOrder.setTransactionAmount(MoneyUtils.fenToYuan(order.getPayPrice()).doubleValue());
        businessOrder.setOrderAmount(MoneyUtils.fenToYuan(order.getPayPrice()).doubleValue());
        businessOrder.setStatus(status);
        businessOrder.setAddTime(order.getCreateTime() != null ? order.getCreateTime().format(formatter) : null);
        businessOrder.setTransactionTime(order.getFinishTime() != null ? order.getFinishTime().format(formatter) : null);
        businessOrder.setName(order.getReceiverName());
        businessOrder.setPhoneNum(order.getReceiverMobile());
        businessOrder.setAddress(order.getReceiverDetailAddress());

        MemberUserRespDTO user = memberUserApi.getUser(getLoginUserId());
        AppUser buyUser = new AppUser();
        buyUser.setUserid(getLoginUserId());
        buyUser.setIsSvip(0);
        buyUser.setUsername(user.getNickname());
        buyUser.setNickname(user.getNickname());
        buyUser.setMobile(user.getMobile());
        buyUser.setStatus(0);
        buyUser.setAvatar(user.getAvatar());
        buyUser.setSex(0);
        buyUser.setScore(user.getPoint());

        dto.setSubOrderList(subOrderList);
        dto.setBusinessOrder(businessOrder);
        dto.setBuyUser(buyUser);

        return dto;
    }

    public ShopUserOrderDetailDTO orderToUserOrderDTO(TradeOrderRespDTO order, Map<String, String> merchantMap) {

        if(order == null){
            return null;
        }

        ShopUserOrderDetailDTO dto = new ShopUserOrderDetailDTO();
        dto.setAnchorId(order.getUserId());
        dto.setGoodsNum(order.getProductCount());
        dto.setLogisticsNum(order.getLogisticsNo());

        List<TradeOrderItemRespDTO> items = order.getItems();
        List<ShopSubOrder> subOrderList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 订单状态 1：待付款 2：待发货 3：待收货 4：已完成 5：已取消
        int status = 1;
        if(order.getStatus() == TradeOrderStatusEnum.UNDELIVERED.getStatus()){
            status = 2;
        } else if(order.getStatus() == TradeOrderStatusEnum.DELIVERED.getStatus()){
            status = 3;
        } else if(order.getStatus() == TradeOrderStatusEnum.COMPLETED.getStatus()){
            status = 4;
        } else if(order.getStatus() == TradeOrderStatusEnum.CANCELED.getStatus()){
            status = 5;
        }

        ShopParentOrder parentOrder = new ShopParentOrder();
        parentOrder.setId(order.getId());
        parentOrder.setUid(order.getUserId());
        parentOrder.setOrderNum(order.getNo());
        parentOrder.setPayOrder(order.getPayOrderId().toString());
        parentOrder.setAmount(MoneyUtils.fenToYuan(order.getPayPrice()).doubleValue());
        parentOrder.setNhrAmount(MoneyUtils.fenToYuan(order.getPayPrice()).doubleValue());
        int paychannelId = 0;
        if(StringUtils.isNotBlank(order.getPayChannelCode()) && order.getPayChannelCode().indexOf("alipay") != -1){
            paychannelId = 1;
        }else if(StringUtils.isNotBlank(order.getPayChannelCode()) && order.getPayChannelCode().indexOf("wx") != -1){
            paychannelId = 2;
        }
        parentOrder.setChannelId(paychannelId);
        parentOrder.setStatus(status);
        parentOrder.setAddTime(order.getCreateTime() != null ? order.getCreateTime().format(formatter) : null);
        parentOrder.setPayTime(order.getPayTime() != null ? order.getPayTime().format(formatter) : null);
        dto.setParentOrder(parentOrder);
        dto.setBuyUserId(order.getUserId());
        MemberUserRespDTO user = memberUserApi.getUser(order.getUserId());
        dto.setBuyUserName(user.getNickname());
        dto.setBuyUserAvatar(user.getAvatar());

        for(TradeOrderItemRespDTO item : items){
            ShopSubOrder subOrder = new ShopSubOrder();
            subOrder.setId(item.getId());
            subOrder.setPayId(order.getPayOrderId());
            subOrder.setBusinessId(Long.valueOf(merchantMap.get("businessId")));
            subOrder.setBusinessOrderId(order.getId());
            subOrder.setOrderNum(order.getNo());
            subOrder.setGoodsId(item.getSpuId());
            subOrder.setGoodsName(item.getSpuName());
            subOrder.setGoodsPrice(MoneyUtils.fenToYuan(item.getPrice()).doubleValue());
            subOrder.setGoodsPicture(item.getPicUrl());
            subOrder.setStatus(status);
            subOrder.setGoodsNum(item.getCount());
            subOrder.setComposeId(item.getSkuId());
            subOrder.setSkuName(item.getSpuName());
            subOrder.setLogisticsNum(order.getLogisticsNo());
            subOrder.setAddTime(order.getCreateTime() != null ? order.getCreateTime().format(formatter) : null);

            subOrderList.add(subOrder);
        }

        ShopBusinessOrder businessOrder = new ShopBusinessOrder();
        businessOrder.setId(order.getId());
        businessOrder.setPayId(order.getPayOrderId());
        businessOrder.setUid(order.getUserId());
        businessOrder.setBusinessId(Long.valueOf(merchantMap.get("businessId")));
        businessOrder.setBusinessName(merchantMap.get("businessName"));
        businessOrder.setBusinessLogo(merchantMap.get("businessLogo"));
        businessOrder.setOrderNum(order.getNo());
//        businessOrder.setPlatformPayOrder(order.getPayOrderId());
        businessOrder.setTransactionAmount(MoneyUtils.fenToYuan(order.getPayPrice()).doubleValue());
        businessOrder.setOrderAmount(MoneyUtils.fenToYuan(order.getPayPrice()).doubleValue());
        businessOrder.setStatus(status);
        businessOrder.setAddTime(order.getCreateTime() != null ? order.getCreateTime().format(formatter) : null);
        businessOrder.setTransactionTime(order.getFinishTime() != null ? order.getFinishTime().format(formatter) : null);
        businessOrder.setName(order.getReceiverName());
        businessOrder.setPhoneNum(order.getReceiverMobile());
        businessOrder.setAddress(order.getReceiverDetailAddress());

        AppUser buyUser = new AppUser();
        buyUser.setUserid(order.getUserId());
        buyUser.setIsSvip(0);
        buyUser.setUsername(user.getNickname());
        buyUser.setNickname(user.getNickname());
        buyUser.setMobile(user.getMobile());
        buyUser.setStatus(0);
        buyUser.setAvatar(user.getAvatar());
        buyUser.setSex(0);
        buyUser.setScore(user.getPoint());

        dto.setSubOrderList(subOrderList);
        dto.setBusinessOrder(businessOrder);
        dto.setGoodsNum(order.getProductCount());
        dto.setLogisticsNum(order.getLogisticsNo());
        dto.setBuyerLogisticsContent(order.getReceiverDetailAddress());
//        dto.setBuyUser(buyUser);

        return dto;
    }

}
