package cn.metast.tuoke.module.trade.job.order;

import cn.metast.tuoke.framework.quartz.core.handler.JobHandler;
import cn.metast.tuoke.framework.tenant.core.job.TenantJob;
import cn.metast.tuoke.module.trade.service.order.TradeOrderUpdateService;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 交易订单的自动过期 Job
 *
 * @author metast.cn
 */
@Component
public class TradeOrderAutoCancelJob implements JobHandler {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = tradeOrderUpdateService.cancelOrderBySystem();
        return String.format("过期订单 %s 个", count);
    }

}
