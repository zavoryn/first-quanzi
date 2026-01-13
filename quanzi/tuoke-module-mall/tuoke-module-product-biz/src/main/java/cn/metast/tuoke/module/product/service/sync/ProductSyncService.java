package cn.metast.tuoke.module.product.service.sync;
/**
 * 同步商品 SPU Service 接口
 *
 * @author metast.cn
 */
public interface ProductSyncService {
    //微信小店获取token
    String getAccess_token();
    //微信小店同步商品
    void syncProductSpu();

    String getCategory();
    //小鹅通获取token
    String  getTechAccess_token();
    //小鹅通同步商品
    void syncTechProductSpu();
}
