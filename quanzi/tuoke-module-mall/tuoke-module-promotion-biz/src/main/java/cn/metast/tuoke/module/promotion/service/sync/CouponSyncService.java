package cn.metast.tuoke.module.promotion.service.sync;
/**
 * 同步商品 SPU Service 接口
 *
 * @author metast.cn
 */
public interface CouponSyncService {
    //小鹅通同步优惠卷
    void syncTechCoupon();
    //小鹅通优惠卷领取记录
    void syncCouponUserRecord();
}
