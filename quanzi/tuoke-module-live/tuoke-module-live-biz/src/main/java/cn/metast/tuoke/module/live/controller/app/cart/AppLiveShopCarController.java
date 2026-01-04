package cn.metast.tuoke.module.live.controller.app.cart;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.live.HttpNone;
import cn.metast.tuoke.framework.common.live.HttpRet;
import cn.metast.tuoke.framework.common.live.HttpRetArr;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.number.MoneyUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.live.controller.app.cart.vo.ShopCarAskDTO;
import cn.metast.tuoke.module.member.api.address.MemberAddressApi;
import cn.metast.tuoke.module.member.api.address.dto.MemberAddressRespDTO;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
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
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "移动端 直播 - 购物车")
@RestController
@RequestMapping("/api/car")
@Slf4j
public class AppLiveShopCarController {

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private MemberAddressApi memberAddressApi;

    @Resource
    private TradeCartApi catrApi;

    @Resource
    private ProductSpuApi prproductSpuApi;
    @Resource
    private ProductSkuApi prproductSkuApi;

    @Resource
    private DictDataApi dictDataApi;


    @PostMapping("/saveAddress")
    @Operation(summary = "直播-添加地址")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "收货人姓名", dataType = "String", required = true),
            @ApiImplicitParam(name = "phoneNum", value = "收货人手机号", dataType = "String", required = true),
            @ApiImplicitParam(name = "pro", value = "省份", dataType = "String", required = true),
            @ApiImplicitParam(name = "city", value = "城市", dataType = "String", required = true),
            @ApiImplicitParam(name = "area", value = "区", dataType = "String", required = true),
            @ApiImplicitParam(name = "areaId", value = "区", dataType = "String", required = true),
            @ApiImplicitParam(name = "address", value = "详细地址", dataType = "String", required = true),
            @ApiImplicitParam(name = "isDefault", value = "是否默认 1：默认 0：非默认", dataType = "int", required = true),
    })
    public HttpRet<HttpNone> saveAddress(String userName, String phoneNum, String pro, String city, String area,  Long areaId, String address, int isDefault) {

        if (StrUtil.isBlank(userName)) {
            return HttpRet.fail("收货人姓名不能为空");
        }
        if (StrUtil.isBlank(phoneNum)) {
            return HttpRet.fail("收货人手机号不能为空");
        }
        if (StrUtil.isBlank(pro)) {
            return HttpRet.fail("省份不能为空");
        }
        if (StrUtil.isBlank(city)) {
            return HttpRet.fail("城市不能为空");
        }
        if (StrUtil.isBlank(area) || areaId == null) {
            return HttpRet.fail("地区不能为空");
        }
        if (StrUtil.isBlank(address)) {
            return HttpRet.fail("详细地址不能为空");
        }

        int i = memberAddressApi.saveAddress(userName, phoneNum, areaId, address, isDefault, getLoginUserId());
        if(i != 1){
            return HttpRet.fail("添加失败");
        }
        return HttpRet.success("添加成功");
    }


    @PostMapping("/getShopAddrList")
    @Operation(summary = "直播-收货地址列表")
//    @PermitAll
    @ApiImplicitParams({ })
    public HttpRetArr<Object> getShopAddrList() {

        JSONArray shopAddrList = memberAddressApi.getShopAddrList(getLoginUserId());

        return HttpRetArr.success("查询成功", shopAddrList);
    }


    @PostMapping("/delShopAddress")
    @Operation(summary = "直播-删除收货地址")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressId", value = "收货地址id", dataType = "long", required = true),
    })
    public HttpRet<HttpNone> delShopAddress(long addressId) {

        if (addressId <= 0) {
            return HttpRet.fail("收货地址ID不能小于等于0");
        }

        int i = memberAddressApi.delAddress(addressId, getLoginUserId());
        if(i == -1){
            return HttpRet.fail("用户收件地址不存在");
        } else if(i != 1){
            return HttpRet.fail("删除失败");
        }

        return HttpRet.success("删除成功");
    }


    @PostMapping("/updateShopAddress")
    @Operation(summary = "直播-修改收货地址")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressId", value = "收货地址id", dataType = "long", required = true),
            @ApiImplicitParam(name = "userName", value = "收货人姓名", dataType = "String", required = false),
            @ApiImplicitParam(name = "phoneNum", value = "收货人手机号", dataType = "String", required = false),
            @ApiImplicitParam(name = "pro", value = "省份", dataType = "String", required = false),
            @ApiImplicitParam(name = "city", value = "城市", dataType = "String", required = false),
            @ApiImplicitParam(name = "area", value = "区", dataType = "String", required = false),
            @ApiImplicitParam(name = "areaId", value = "区", dataType = "String", required = true),
            @ApiImplicitParam(name = "address", value = "收货人地址", dataType = "String", required = false),
            @ApiImplicitParam(name = "isDefault", value = "是否默认 1：默认 0：非默认", dataType = "int", required = false),
    })
    public HttpRet<HttpNone> updateShopAddress(long addressId, String userName, String phoneNum, String pro, String city, String area, Long areaId, String address, int isDefault) {

        if (StrUtil.isBlank(userName)) {
            return HttpRet.fail("收货人姓名不能为空");
        }
        if (StrUtil.isBlank(phoneNum)) {
            return HttpRet.fail("收货人手机号不能为空");
        }
        if (StrUtil.isBlank(pro)) {
            return HttpRet.fail("省份不能为空");
        }
        if (StrUtil.isBlank(city)) {
            return HttpRet.fail("城市不能为空");
        }
        if (StrUtil.isBlank(area) || areaId == null) {
            return HttpRet.fail("地区不能为空");
        }
        if (StrUtil.isBlank(address)) {
            return HttpRet.fail("详细地址不能为空");
        }

        int i = memberAddressApi.updateShopAddress_live(addressId, userName, phoneNum, areaId, address, isDefault, getLoginUserId());
        if(i == -1){
            return HttpRet.fail("用户收件地址不存在");
        } else if(i != 1){
            return HttpRet.fail("修改失败");
        }

        return HttpRet.success("修改成功");
    }


    @PostMapping("/getShopCarList")
    @Operation(summary = "直播-购物车商品列表")
//    @PermitAll
    @ApiImplicitParams({})
    public HttpRetArr<Object> getShopCarList() {

        Long loginUserId = getLoginUserId();
//        Long loginUserId = 247L;

        List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
        Map<String, String> merchantMap = dictDataList.stream()
                .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));

        JSONArray jsonArray = new JSONArray();

        JSONArray carList = new JSONArray();
        List<TradeCartRespDTO> cartList = catrApi.getCartList(loginUserId);

        Map<Long, ProductSpuRespDTO> spuMap = new HashMap<>();
        Map<Long, ProductSkuRespDTO> skuMap = new HashMap<>();
        if(cartList != null && cartList.size() > 0){
            List<Long> spuIds = cartList.stream()
                    .map(TradeCartRespDTO::getSpuId)
                    .collect(Collectors.toList());
            List<ProductSpuRespDTO> spuList = prproductSpuApi.getSpuList(spuIds);
            spuMap = spuList.stream()
                    .collect(Collectors.toMap(ProductSpuRespDTO::getId, item -> item, (existing, replacement) -> replacement));

            List<Long> skuIds = cartList.stream()
                    .map(TradeCartRespDTO::getSkuId)
                    .collect(Collectors.toList());
            List<ProductSkuRespDTO> skuList = prproductSkuApi.getSkuList(skuIds);
            skuMap = skuList.stream()
                    .collect(Collectors.toMap(ProductSkuRespDTO::getId, item -> item, (existing, replacement) -> replacement));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for(TradeCartRespDTO item : cartList){
            ProductSpuRespDTO spu = spuMap.get(item.getSpuId());
            ProductSkuRespDTO sku = skuMap.get(item.getSkuId());
            if(spu == null || sku == null){
                continue;
            }

            JSONObject cart = new JSONObject();
            cart.put("id", item.getId());
            cart.put("uid", item.getUserId());
            cart.put("businessId", merchantMap.get("businessId"));
            cart.put("goodsNum", item.getCount());
            cart.put("goodsId", item.getSpuId());
            cart.put("goodsName", spu.getName());
            cart.put("goodsPicture", spu.getPicUrl());
            cart.put("goodsPrice", MoneyUtils.fenToYuan(sku.getPrice()).doubleValue());
            cart.put("composeId", item.getSkuId());

            List<ProductPropertyValueDetailRespDTO> properties = sku.getProperties();
            String skuName = "";
            for(ProductPropertyValueDetailRespDTO gg : properties){
                if(gg.getPropertyId().equals(0L)){
                    continue;
                }
                skuName += gg.getPropertyName() + ":" + gg.getValueName() + " ";
            }

            cart.put("skuName", skuName);
            cart.put("addTime", item.getCreateTime().format(formatter));
            carList.add(cart);
        }

        JSONObject ShopCarDTO = new JSONObject();
        ShopCarDTO.put("businessId", merchantMap.get("businessId"));
        ShopCarDTO.put("businessLogo", merchantMap.get("businessLogo"));
        ShopCarDTO.put("businessName", merchantMap.get("businessName"));
        ShopCarDTO.put("shopCarList", carList);
        jsonArray.add(ShopCarDTO);

//        CommonResult<JSONArray> res = success(jsonArray);
//        res.setRetArr(res.getData());
//        return res;
        return HttpRetArr.success("获取成功", jsonArray);
    }


    @PostMapping("/saveShopCar")
    @Operation(summary = "直播-添加商品到购物车")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", dataType = "long", required = true),
            @ApiImplicitParam(name = "goodsNum", value = "商品数量", dataType = "int", required = true),
            @ApiImplicitParam(name = "composeId", value = "商品属性组合id(skuId)", dataType = "long", required = false),
    })
    public HttpRet<HttpNone> saveShopCar(long goodsId, int goodsNum, long composeId) {

        int i = catrApi.saveShopCar(goodsId, goodsNum, composeId, getLoginUserId());

        if(i == -1){
            return HttpRet.fail("商品不存在");
        } else if(i == -2){
            return HttpRet.fail("库存不足");
        }

        return HttpRet.success("添加成功");
    }


    @PostMapping("/delShopCar")
    @Operation(summary = "直播-删除购物车中商品")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopCarId", value = "购物车id", dataType = "long", required = true),
    })
    public HttpRet<HttpNone> delShopCar(long shopCarId) {
        if (shopCarId <= 0) {
            return HttpRet.fail("购物车ID不能小于等于0");
        }

        int i = catrApi.delShopCar(shopCarId, getLoginUserId());

        if(i != 1){
            return HttpRet.fail("删除失败");
        }

        return HttpRet.success("删除成功");
    }


    @PostMapping("/updateShopCar")
    @Operation(summary = "直播-修改购物车")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopCarId", value = "购物车id", dataType = "long", required = true),
            @ApiImplicitParam(name = "composeId", value = "商品属性组合id(skuId)", dataType = "long", required = false),
            @ApiImplicitParam(name = "goodsNum", value = "商品数量", dataType = "int", required = true),
    })
    public HttpRet<HttpNone> updateShopCar(long shopCarId, int goodsNum, long composeId) {

        int i = catrApi.updateShopCar(shopCarId, goodsNum, composeId, getLoginUserId());

        if(i == -1){
            return HttpRet.fail("商品不存在");
        } else if(i == -2){
            return HttpRet.fail("库存不足");
        } else if(i == -3){
            return HttpRet.fail("购物车不存在");
        } else if(i != 1){
            return HttpRet.fail("操作失败");
        }

        return HttpRet.success("操作成功");
    }


    @PostMapping("/purchaseGoods")
    @Operation(summary = "直播-购物车确认订单接口")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressId", value = "收货地址id", dataType = "long", required = true),
    })
    public HttpRet<Object> purchaseGoods(long addressId, @RequestBody List<ShopCarAskDTO> shopCarDTOS) {

        if (addressId <= 0) {
            return HttpRet.fail("收货地址不能为空");
        }
        if (CollUtil.isEmpty(shopCarDTOS)) {
            return HttpRet.fail("商品异常");
        }

        List<JSONObject> jsonObjects = BeanUtils.toBean(shopCarDTOS, JSONObject.class);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(jsonObjects);
        JSONObject jsonObject = catrApi.purchaseGoods(addressId, jsonArray, getLoginUserId());

        return HttpRet.success("生成订单成功", jsonObject);
    }

}
