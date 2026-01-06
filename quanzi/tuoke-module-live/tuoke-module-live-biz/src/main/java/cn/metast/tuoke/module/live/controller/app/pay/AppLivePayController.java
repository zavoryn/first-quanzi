package cn.metast.tuoke.module.live.controller.app.pay;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.live.HttpNone;
import cn.metast.tuoke.framework.common.live.HttpRet;
import cn.metast.tuoke.framework.common.live.HttpRetArr;
import cn.metast.tuoke.framework.common.util.number.MoneyUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.live.controller.app.cart.vo.ShopCarAskDTO;
import cn.metast.tuoke.module.live.controller.app.pay.vo.StartPayRet;
import cn.metast.tuoke.module.member.api.address.MemberAddressApi;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.pay.api.order.PayOrderApi;
import cn.metast.tuoke.module.pay.api.order.dto.PayOrderSubmitRespDTO;
import cn.metast.tuoke.module.product.api.property.dto.ProductPropertyValueDetailRespDTO;
import cn.metast.tuoke.module.product.api.sku.ProductSkuApi;
import cn.metast.tuoke.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.metast.tuoke.module.product.api.spu.ProductSpuApi;
import cn.metast.tuoke.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.metast.tuoke.module.system.api.dict.DictDataApi;
import cn.metast.tuoke.module.system.api.dict.dto.DictDataRespDTO;
import cn.metast.tuoke.module.trade.api.cart.TradeCartApi;
import cn.metast.tuoke.module.trade.api.cart.dto.TradeCartRespDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "移动端 直播 - 购物车")
@RestController
@RequestMapping("/api/pay")
@Slf4j
public class AppLivePayController {

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private MemberAddressApi memberAddressApi;

    @Resource
    private TradeCartApi catrApi;
    @Resource
    private PayOrderApi payOrderApi;

    @Resource
    private ProductSpuApi prproductSpuApi;
    @Resource
    private ProductSkuApi prproductSkuApi;

    @Resource
    private DictDataApi dictDataApi;


    @PostMapping("/startPay")
    @Operation(summary = "直播-发起支付")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelId", value = "支付渠道ID 1:支付宝 2:微信 3:ios内购 11：钱方支付宝 12：钱方微信 21：汇潮支付宝 22：汇潮微信 31：首易信支付宝 32：首易信微信", dataType = "long", required = true),
            @ApiImplicitParam(name = "type", value = "业务类型 1：金币充值  2：购物支付 3.待定 4.购买贵族 5.购买SVIP 6.开通守护", dataType = "int", required = true),
            @ApiImplicitParam(name = "productId", value = "商品id", dataType = "long", required = true),
            @ApiImplicitParam(name = "userId", value = "受益人id", dataType = "long", required = false),
            @ApiImplicitParam(name = "payOrderId", value = "支付订单id", dataType = "long", required = false),
    })
    public HttpRet<StartPayRet> startPay(long channelId, int type, long productId, long userId, long payOrderId) {
        String channelCode = "alipay_app";
        if(channelId == 1){
            channelCode = "alipay_app";
        }
        else if(channelId == 2){
            channelCode = "wx_app";
        }
        PayOrderSubmitRespDTO payOrderSubmitResp= payOrderApi.submitPayOrder(payOrderId, channelCode);

        StartPayRet payRet = new StartPayRet();
        payRet.setOrderId(payOrderSubmitResp.getMerchantOrderId());
        payRet.setAliPayInfo(payOrderSubmitResp.getDisplayContent());
        payRet.setWXPayInfo(payOrderSubmitResp.getDisplayContent());

        return HttpRet.success("发起支付成功", payRet);
    }

}
