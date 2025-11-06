package cn.metast.tuoke.module.community.api.cmBuyLog;

import java.util.List;

/**
 * 购买记录 API 接口
 *
 * @author adminXq
 */
public interface BuyLogApi {

    /**
     * 更新状态
     */
    void updateOrderPaid(Long orderId, Long payOrderId);

    /**
     * 根据部门ID获取支付订单ID列表
     * 用于部门数据隔离
     *
     * @param deptId 部门ID
     * @return 支付订单ID列表，null表示不过滤（超级管理员）
     */
    List<Long> getPayOrderIdsByDeptId(Long deptId);
}
