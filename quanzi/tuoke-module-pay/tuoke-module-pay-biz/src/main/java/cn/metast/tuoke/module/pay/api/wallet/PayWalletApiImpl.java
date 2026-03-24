package cn.metast.tuoke.module.pay.api.wallet;

import cn.hutool.core.lang.Assert;
import cn.metast.tuoke.module.pay.api.wallet.dto.PayWalletAddBalanceReqDTO;
import cn.metast.tuoke.module.pay.dal.dataobject.wallet.PayWalletDO;
import cn.metast.tuoke.module.pay.enums.wallet.PayWalletBizTypeEnum;
import cn.metast.tuoke.module.pay.service.wallet.PayWalletService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 钱包 API 实现类
 *
 * @author metast.cn
 */
@Service
public class PayWalletApiImpl implements PayWalletApi {

    @Resource
    private PayWalletService payWalletService;

    @Override
    public void addWalletBalance(PayWalletAddBalanceReqDTO reqDTO) {
        // 创建或获取钱包
        PayWalletDO wallet = payWalletService.getOrCreateWallet(reqDTO.getUserId(), reqDTO.getUserType());
        Assert.notNull(wallet, "钱包({}/{})不存在", reqDTO.getUserId(), reqDTO.getUserType());

        // 增加余额
        PayWalletBizTypeEnum bizType = PayWalletBizTypeEnum.valueOf(reqDTO.getBizType());
        payWalletService.addWalletBalance(wallet.getId(), reqDTO.getBizId(), bizType, reqDTO.getPrice());
    }

}
