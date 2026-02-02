package cn.metast.tuoke.module.trade.api.order;

import cn.metast.tuoke.module.trade.api.order.dto.TradeOrderRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 订单 API 接口
 *
 * @author HUIHUI
 */
public interface TradeOrderApi {

    /**
     * 获得订单列表
     *
     * @param ids 订单编号数组
     * @return 订单列表
     */
    List<TradeOrderRespDTO> getOrderList(Collection<Long> ids);
    List<TradeOrderRespDTO> getOrderList_live(int pageIndex, int pageSize, int status, Long userId);

    /**
     * 获得订单
     *
     * @param id 订单编号
     * @return 订单
     */
    TradeOrderRespDTO getOrder(Long id);

    Map<String, Long> getOrderNum(int type);

    int confirmReceipt(long businessOrderId);
    int updateOrderAddress(long businessOrderId, long addressId);
    int updateOrder(Long id,Integer status);

    /**
     * 取消支付订单
     *
     * @param userId 用户编号
     * @param orderId 订单编号
     * @param cancelType 取消类型
     */
    void cancelPaidOrder(Long userId, Long orderId, Integer cancelType);
    void cancelOrder(Long userId, Long orderId);

    TradeOrderRespDTO selectByNo(String no);
}
