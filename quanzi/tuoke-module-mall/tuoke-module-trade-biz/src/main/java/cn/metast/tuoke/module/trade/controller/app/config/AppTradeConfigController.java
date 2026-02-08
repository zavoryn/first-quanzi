package cn.metast.tuoke.module.trade.controller.app.config;

import cn.hutool.core.util.ObjUtil;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.module.member.api.config.MemberConfigApi;
import cn.metast.tuoke.module.member.api.config.dto.MemberConfigRespDTO;
import cn.metast.tuoke.module.trade.controller.app.config.vo.AppTradeConfigRespVO;
import cn.metast.tuoke.module.trade.convert.config.TradeConfigConvert;
import cn.metast.tuoke.module.trade.dal.dataobject.config.TradeConfigDO;
import cn.metast.tuoke.module.trade.service.config.TradeConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 交易配置")
@RestController
@RequestMapping("/trade/config")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AppTradeConfigController {

    @Resource
    private TradeConfigService tradeConfigService;

    @Resource
    private MemberConfigApi memberConfigApi;

    @Value("${tuoke.tencent-lbs-key}")
    private String tencentLbsKey;

    @GetMapping("/get")
    @Operation(summary = "获得交易配置")
    @PermitAll
    public CommonResult<AppTradeConfigRespVO> getTradeConfig() {
        TradeConfigDO config = ObjUtil.defaultIfNull(tradeConfigService.getTradeConfig(), new TradeConfigDO());
        AppTradeConfigRespVO appTradeConfigRespVO = TradeConfigConvert.INSTANCE.convert02(config).setTencentLbsKey(tencentLbsKey);
        MemberConfigRespDTO memberConfig = memberConfigApi.getConfig();
        if(memberConfig != null){
            appTradeConfigRespVO.setPointTradeDeductEnable(memberConfig.getPointTradeDeductEnable());
        }
        return success(appTradeConfigRespVO);
    }

}
