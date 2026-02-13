package cn.metast.tuoke.module.trade.service.sync;
/**
 * 同步商品 SPU Service 接口
 *
 * @author metast.cn
 */
public interface OrderSyncService {
    //小鹅通同步商品
    void syncTechProductOrder();
    //小鹅通同步商品分类
    void syncTechCategory();
    //小鹅通同步售后订单
    void syncTechAfterOrder();
}
