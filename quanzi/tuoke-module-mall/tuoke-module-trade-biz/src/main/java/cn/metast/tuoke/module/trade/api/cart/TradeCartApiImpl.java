package cn.metast.tuoke.module.trade.api.cart;

import cn.metast.tuoke.framework.common.util.number.MoneyUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.pay.api.order.PayOrderApi;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderRespDTO;
import cn.metast.tuoke.module.trade.api.cart.dto.TradeCartRespDTO;
import cn.metast.tuoke.module.trade.controller.app.cart.vo.AppCartAddReqVO;
import cn.metast.tuoke.module.trade.controller.app.cart.vo.AppCartUpdateCountReqVO;
import cn.metast.tuoke.module.trade.controller.app.order.vo.AppTradeOrderCreateReqVO;
import cn.metast.tuoke.module.trade.controller.app.order.vo.AppTradeOrderSettlementReqVO;
import cn.metast.tuoke.module.trade.dal.dataobject.cart.CartDO;
import cn.metast.tuoke.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.metast.tuoke.module.trade.service.cart.CartService;
import cn.metast.tuoke.module.trade.service.order.TradeOrderUpdateService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xingfudeshi.knife4j.core.util.CommonUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单 API 接口实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class TradeCartApiImpl implements TradeCartApi {

    @Resource
    private CartService cartService;
    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;
    @Resource
    private PayOrderApi payOrderApi;

    @Override
    public List<TradeCartRespDTO> getCartList(Long userId) {
        List<CartDO> cartDoList = cartService.getCartDoList(userId);
        List<TradeCartRespDTO> tradeCartRespDTOS = BeanUtils.toBean(cartDoList, TradeCartRespDTO.class);
        return tradeCartRespDTOS;
    }

    @Override
    public int saveShopCar(long goodsId, int goodsNum, long composeId, Long userId){
        AppCartAddReqVO addReqVO = new AppCartAddReqVO();
        addReqVO.setSkuId(composeId);
        addReqVO.setCount(goodsNum);
        int i = cartService.addCart_live(userId, addReqVO);
        return i;
    }

    @Override
    public int delShopCar(long id, Long userId){
        return cartService.deleteCart_live(userId, id);
    }

    @Override
    public int updateShopCar(long shopCarId, int goodsNum, long composeId, Long userId){
        AppCartUpdateCountReqVO updateReqVO = new AppCartUpdateCountReqVO();
        updateReqVO.setId(shopCarId);
        updateReqVO.setCount(goodsNum);
        int i = cartService.updateCartCount_live(userId, updateReqVO);
        return i;
    }

    @Override
    public JSONObject purchaseGoods(long addressId, JSONArray list, Long userId){
        AppTradeOrderCreateReqVO createReqVO = new AppTradeOrderCreateReqVO();
        createReqVO.setAddressId(addressId);
        createReqVO.setDeliveryType(1);
        createReqVO.setPointStatus(false);
        List<AppTradeOrderSettlementReqVO.Item> items = new ArrayList<>();
        for(int n=0; n<list.size(); n++){
            JSONObject jsonObject = list.getJSONObject(n);
            AppTradeOrderSettlementReqVO.Item item = new AppTradeOrderSettlementReqVO.Item();
            item.setCartId(jsonObject.getLong("carId"));
            item.setCount(jsonObject.getInteger("goodsNum"));
            item.setSkuId(jsonObject.getLong("skuId"));
            items.add(item);
        }
        createReqVO.setItems(items);
        TradeOrderDO order = tradeOrderUpdateService.createOrder(userId, createReqVO);
        PayOrderRespDTO payOrder = payOrderApi.getOrder(order.getPayOrderId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", order.getId());
        jsonObject.put("uid", userId);
        jsonObject.put("orderNum", order.getNo());
        jsonObject.put("amount", MoneyUtils.fenToYuan(order.getTotalPrice()));
        jsonObject.put("nhrAmount", MoneyUtils.fenToYuan(order.getPayPrice()));
        jsonObject.put("channelId", payOrder != null ? payOrder.getChannelCode() : "");
        // 订单状态 1.待付款 2.付款成功 3.付款失败
        String status = "1";
        if(payOrder.getStatus() == 10){
            status = "2";
        }else if(payOrder.getStatus() == 30){
            status = "3";
        }
        jsonObject.put("status", status);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        jsonObject.put("addTime", order.getCreateTime().format(formatter));
        jsonObject.put("payTime", order.getPayTime() != null ? order.getPayTime().format(formatter):"");
        jsonObject.put("remake", order.getRemark());
        jsonObject.put("payOrderId", order.getPayOrderId());
        return jsonObject;
    }

}
