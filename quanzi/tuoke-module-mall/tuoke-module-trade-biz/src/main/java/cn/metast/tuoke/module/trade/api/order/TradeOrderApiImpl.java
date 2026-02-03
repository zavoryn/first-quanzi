package cn.metast.tuoke.module.trade.api.order;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.member.api.address.MemberAddressApi;
import cn.metast.tuoke.module.member.api.address.dto.MemberAddressRespDTO;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.metast.tuoke.module.trade.api.order.dto.ProductPropertyValueDetailRespDTO;
import cn.metast.tuoke.module.trade.api.order.dto.TradeOrderItemRespDTO;
import cn.metast.tuoke.module.trade.api.order.dto.TradeOrderRespDTO;
import cn.metast.tuoke.module.trade.controller.admin.order.vo.TradeOrderPageReqVO;
import cn.metast.tuoke.module.trade.controller.admin.order.vo.TradeOrderUpdateAddressReqVO;
import cn.metast.tuoke.module.trade.controller.app.base.property.AppProductPropertyValueDetailRespVO;
import cn.metast.tuoke.module.trade.controller.app.order.vo.AppTradeOrderPageItemRespVO;
import cn.metast.tuoke.module.trade.controller.app.order.vo.item.AppTradeOrderItemRespVO;
import cn.metast.tuoke.module.trade.convert.order.TradeOrderConvert;
import cn.metast.tuoke.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.metast.tuoke.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.metast.tuoke.module.trade.enums.order.TradeOrderStatusEnum;
import cn.metast.tuoke.module.trade.service.aftersale.AfterSaleService;
import cn.metast.tuoke.module.trade.service.order.TradeOrderQueryService;
import cn.metast.tuoke.module.trade.service.order.TradeOrderUpdateService;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 订单 API 接口实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class TradeOrderApiImpl implements TradeOrderApi {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;
    @Resource
    private TradeOrderQueryService tradeOrderQueryService;
    @Resource
    private AfterSaleService afterSaleService;

    @Resource
    private MemberAddressApi memberAddressApi;

    @Override
    public List<TradeOrderRespDTO> getOrderList(Collection<Long> ids) {
        return TradeOrderConvert.INSTANCE.convertList04(tradeOrderQueryService.getOrderList(ids));
    }

    @Override
    public List<TradeOrderRespDTO> getOrderList_live(int pageIndex, int pageSize, int status, Long userId) {
        TradeOrderPageReqVO reqVO = new TradeOrderPageReqVO();
        reqVO.setPageNo(pageIndex);
        reqVO.setPageSize(pageSize);
        reqVO.setUserId(userId);

        // 0:全部 1:待付款 2:待发货 3:待收货 4:订单完成
        if(status == 1){
            reqVO.setStatus(0);
        } else if(status == 2){
            reqVO.setStatus(10);
        } else if(status == 3){
            reqVO.setStatus(20);
        } else if(status == 4){
            reqVO.setStatus(30);
        }
        PageResult<TradeOrderDO> orderPage = tradeOrderQueryService.getOrderPage(reqVO);

        // 查询订单项
        List<TradeOrderItemDO> orderItems = tradeOrderQueryService.getOrderItemListByOrderId(
                convertSet(orderPage.getList(), TradeOrderDO::getId));

        PageResult<AppTradeOrderPageItemRespVO> appTradeOrderPageItemRespVOPageResult = TradeOrderConvert.INSTANCE.convertPage02(orderPage, orderItems);

        List<AppTradeOrderPageItemRespVO> list = appTradeOrderPageItemRespVOPageResult.getList();
        List<TradeOrderRespDTO> tradeOrderRespDTOS = BeanUtils.toBean(list, TradeOrderRespDTO.class);

        for (int i = 0; i < tradeOrderRespDTOS.size(); i++) {
            List<AppTradeOrderItemRespVO> items = list.get(i).getItems();

            tradeOrderRespDTOS.get(i).setItems(BeanUtils.toBean(items, TradeOrderItemRespDTO.class));

            for(int j = 0; j < items.size(); j++){
                List<AppProductPropertyValueDetailRespVO> properties = items.get(j).getProperties();
                tradeOrderRespDTOS.get(i).getItems().get(j).setProperties(BeanUtils.toBean(properties, ProductPropertyValueDetailRespDTO.class));
            }

        }

        return tradeOrderRespDTOS;
    }

    @Override
    public TradeOrderRespDTO getOrder(Long id) {
        return TradeOrderConvert.INSTANCE.convert(tradeOrderQueryService.getOrder(id));
    }

    @Override
    public Map<String, Long> getOrderNum(int type) {

        Map<String, Long> orderCount = Maps.newLinkedHashMapWithExpectedSize(5);

        if(type == 1){

            // 全部
            orderCount.put("allCount", tradeOrderQueryService.getOrderCount(getLoginUserId(), null, null));
            // 待付款（未支付）
            orderCount.put("unpaidCount", tradeOrderQueryService.getOrderCount(getLoginUserId(),
                    TradeOrderStatusEnum.UNPAID.getStatus(), null));
            // 待发货
            orderCount.put("undeliveredCount", tradeOrderQueryService.getOrderCount(getLoginUserId(),
                    TradeOrderStatusEnum.UNDELIVERED.getStatus(), null));
            // 待收货
            orderCount.put("deliveredCount", tradeOrderQueryService.getOrderCount(getLoginUserId(),
                    TradeOrderStatusEnum.DELIVERED.getStatus(), null));
            // 待评价
            orderCount.put("uncommentedCount", tradeOrderQueryService.getOrderCount(getLoginUserId(),
                    TradeOrderStatusEnum.COMPLETED.getStatus(), false));
            // 售后数量
            orderCount.put("afterSaleCount", afterSaleService.getApplyingAfterSaleCount(getLoginUserId()));

        }else {

            // 全部
            orderCount.put("allCount", tradeOrderQueryService.getOrderCountBySj(null, null));
            // 待付款（未支付）
            orderCount.put("unpaidCount", tradeOrderQueryService.getOrderCountBySj(
                    TradeOrderStatusEnum.UNPAID.getStatus(), null));
            // 待发货
            orderCount.put("undeliveredCount", tradeOrderQueryService.getOrderCountBySj(
                    TradeOrderStatusEnum.UNDELIVERED.getStatus(), null));
            // 待收货
            orderCount.put("deliveredCount", tradeOrderQueryService.getOrderCountBySj(
                    TradeOrderStatusEnum.DELIVERED.getStatus(), null));
            // 待评价
            orderCount.put("uncommentedCount", tradeOrderQueryService.getOrderCountBySj(
                    TradeOrderStatusEnum.COMPLETED.getStatus(), false));
            // 售后数量
            orderCount.put("afterSaleCount", afterSaleService.getApplyingAfterSaleCountBySj());
        }

        return orderCount;
    }

    @Override
    public int confirmReceipt(long businessOrderId){
        tradeOrderUpdateService.receiveOrderByMember(getLoginUserId(), businessOrderId);
        return 1;
    }

    @Override
    public int updateOrderAddress(long businessOrderId, long addressId){
        int i = 0;
        MemberAddressRespDTO address = memberAddressApi.getAddress(addressId, getLoginUserId());
        TradeOrderDO order = tradeOrderQueryService.getOrder(businessOrderId);
        if(address != null && order != null){
            TradeOrderUpdateAddressReqVO reqVO = new TradeOrderUpdateAddressReqVO();
            reqVO.setId(order.getId());
            reqVO.setReceiverName(address.getName());
            reqVO.setReceiverMobile(address.getMobile());
            reqVO.setReceiverAreaId(address.getAreaId());
            reqVO.setReceiverDetailAddress(address.getDetailAddress());
            i = tradeOrderUpdateService.updateOrderAddress(reqVO);
        }
        return i;
    }

    @Override
    public int updateOrder(Long id,Integer status) {
        return tradeOrderUpdateService.updateOrderStatus(id,status);
    }

    @Override
    public void cancelPaidOrder(Long userId, Long orderId, Integer cancelType) {
        tradeOrderUpdateService.cancelPaidOrder(userId, orderId, cancelType);
    }

    @Override
    public void cancelOrder(Long userId, Long orderId) {
        tradeOrderUpdateService.cancelOrderByMember(userId, orderId);
    }

    @Override
    public TradeOrderRespDTO selectByNo(String no) {
        TradeOrderDO sku = tradeOrderUpdateService.selectByNo(no);
        return BeanUtils.toBean(sku, TradeOrderRespDTO.class);
    }

}
