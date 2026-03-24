package cn.metast.tuoke.module.pay.api.order;

import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.community.api.cmBuyLog.BuyLogApi;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderRespDTO;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderSubmitRespDTO;
import cn.metast.tuoke.module.pay.controller.admin.order.vo.PayOrderSubmitRespVO;
import cn.metast.tuoke.module.pay.controller.app.order.vo.AppPayOrderSubmitReqVO;
import cn.metast.tuoke.module.pay.convert.order.PayOrderConvert;
import cn.metast.tuoke.module.pay.dal.dataobject.order.PayOrderDO;
import cn.metast.tuoke.module.pay.enums.order.PayOrderStatusEnum;
import cn.metast.tuoke.module.pay.service.order.PayOrderService;
import cn.metast.tuoke.module.trade.api.order.TradeOrderApi;
import cn.metast.tuoke.module.trade.api.order.dto.TradeOrderRespDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import jakarta.annotation.Resource;

import java.util.List;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.util.servlet.ServletUtils.getClientIP;

/**
 * 支付单 API 实现类
 *
 * @author metast.cn
 */
@Service
public class PayOrderApiImpl implements PayOrderApi {

    @Resource
    private PayOrderService payOrderService;
    @Lazy
    @Resource
    private BuyLogApi buyLogApi;
    @Resource
    private TradeOrderApi tradeOrderApi;

    @Override
    public Long createOrder(PayOrderCreateReqDTO reqDTO) {
        return payOrderService.createOrder(reqDTO);
    }

    @Override
    public PayOrderRespDTO getOrder(Long id) {
        PayOrderDO order = payOrderService.getOrder(id);
        return PayOrderConvert.INSTANCE.convert2(order);
    }

    @Override
    public List<PayOrderRespDTO> getOrderIds(List<Long> ids) {
        List<PayOrderDO> orderDOList = payOrderService.getOrderList(ids);
        return BeanUtils.toBean(orderDOList, PayOrderRespDTO.class);
    }

    @Override
    public void updatePayOrderPrice(Long id, Integer payPrice) {
        payOrderService.updatePayOrderPrice(id, payPrice);
    }

    @Override
    public PayOrderSubmitRespDTO submitPayOrder(Long id, String channelCode) {

        AppPayOrderSubmitReqVO reqVO = new AppPayOrderSubmitReqVO();
        reqVO.setId(id);
        reqVO.setChannelCode(channelCode);

        PayOrderSubmitRespVO respVO = payOrderService.submitOrder(reqVO, getClientIP());

        PayOrderSubmitRespDTO payOrderSubmitRespDTO = BeanUtils.toBean(respVO, PayOrderSubmitRespDTO.class);


        return payOrderSubmitRespDTO;
    }

    @Override
    public void syncOrder(Long id) {
        PayOrderDO order = payOrderService.getOrder(id);
        if (order == null) {
           new RuntimeException("订单不存在！");
        }
        if (PayOrderStatusEnum.isWaiting(order.getStatus())) {
            payOrderService.syncOrderQuietly(order.getId());
            // 重新查询，因为同步后，可能会有变化
            order = payOrderService.getOrder(id);
            if(PayOrderStatusEnum.isSuccess(order.getStatus())) {
                //支付成功-同步支付日志
                buyLogApi.updateOrderPaid(
                        Long.valueOf(order.getMerchantOrderId()),
                        order.getId()
                );
            }
        }
        //更新主订单状态
        if(PayOrderStatusEnum.isSuccess(order.getStatus())) {
            if (StringUtils.isNotBlank(order.getMerchantOrderId())) {
                Long orderId = Long.parseLong(order.getMerchantOrderId());
                TradeOrderRespDTO tradeOrderRespDTO = tradeOrderApi.getOrder(orderId);
                if (tradeOrderRespDTO != null) {
                    if (tradeOrderRespDTO.getStatus() == 0) {
                        tradeOrderApi.updateOrder(tradeOrderRespDTO.getId(), PayOrderStatusEnum.SUCCESS.getStatus());
                    }
                }
            }
        }
    }
}
