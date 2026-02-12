package cn.metast.tuoke.module.trade.job.order;

import cn.metast.tuoke.framework.quartz.core.handler.JobHandler;
import cn.metast.tuoke.framework.tenant.core.job.TenantJob;
import cn.metast.tuoke.module.trade.service.order.TradeOrderUpdateService;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 交易订单的自动评论 Job
 *
 * @author metast.cn
 */
@Component
public class TradeOrderAutoCommentJob implements JobHandler {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = tradeOrderUpdateService.createOrderItemCommentBySystem();
        return String.format("评论订单 %s 个", count);
    }

}
