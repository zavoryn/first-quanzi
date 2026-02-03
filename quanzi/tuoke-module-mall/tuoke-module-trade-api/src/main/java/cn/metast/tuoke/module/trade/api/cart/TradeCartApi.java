package cn.metast.tuoke.module.trade.api.cart;

import cn.metast.tuoke.module.trade.api.cart.dto.TradeCartRespDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.List;

/**
 * 订单 API 接口
 *
 * @author HUIHUI
 */
public interface TradeCartApi {

    /**
     * 获得购物车列表列表
     *
     * @return 订单列表
     */
    List<TradeCartRespDTO> getCartList(Long userId);

    int saveShopCar(long goodsId, int goodsNum, long composeId, Long userId);

    int delShopCar(long id, Long userId);

    int updateShopCar(long shopCarId, int goodsNum, long composeId, Long userId);

    JSONObject purchaseGoods(long addressId, JSONArray list, Long userId);

}
