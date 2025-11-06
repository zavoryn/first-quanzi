package cn.metast.tuoke.module.community.api.cmBuyLog;
import cn.metast.tuoke.module.community.service.cmBuylog.CmBuyLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 会员用户的 API 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
public class BuyLogApiImpl implements BuyLogApi {
    @Resource
    private CmBuyLogService cmBuyLogService;

    @Override
    public void updateOrderPaid(Long orderId, Long payOrderId) {
        cmBuyLogService.updateOrderPaid(orderId, payOrderId);
    }

    @Override
    public List<Long> getPayOrderIdsByDeptId(Long deptId) {
        return cmBuyLogService.getPayOrderIdsByDeptId(deptId);
    }
}
