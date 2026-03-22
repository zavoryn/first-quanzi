package cn.metast.tuoke.module.pay.api.order;

import cn.metast.tuoke.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderRespDTO;

import cn.metast.tuoke.module.pay.api.order.dto.PayOrderSubmitRespDTO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 支付单 API 接口
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
public interface PayOrderApi {

    /**
     * 创建支付单
     *
     * @param reqDTO 创建请求
     * @return 支付单编号
     */
    Long createOrder(@Valid PayOrderCreateReqDTO reqDTO);

    /**
     * 获得支付单
     *
     * @param id 支付单编号
     * @return 支付单
     */
    PayOrderRespDTO getOrder(Long id);

    /**
     * 获得支付单多个
     *
     * @param ids 支付单编号
     * @return 支付单
     */
    List<PayOrderRespDTO> getOrderIds(List<Long> ids);
    /**
     * 更新支付订单价格
     *
     * @param id 支付单编号
     * @param payPrice   支付单价格
     */
    void updatePayOrderPrice(Long id, Integer payPrice);

    PayOrderSubmitRespDTO submitPayOrder(Long id, String channelCode);

    //同步订单状态
    void syncOrder(Long id);

}
