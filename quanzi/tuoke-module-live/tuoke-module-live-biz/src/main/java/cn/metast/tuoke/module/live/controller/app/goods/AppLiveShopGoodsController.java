package cn.metast.tuoke.module.live.controller.app.goods;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.live.HttpNone;
import cn.metast.tuoke.framework.common.live.HttpRet;
import cn.metast.tuoke.framework.common.live.HttpRetArr;
import cn.metast.tuoke.framework.common.util.number.MoneyUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.live.controller.app.cart.vo.ShopCarAskDTO;
import cn.metast.tuoke.module.live.controller.app.goods.vo.*;
import cn.metast.tuoke.module.member.api.address.MemberAddressApi;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.product.api.property.dto.ProductPropertyValueDetailRespDTO;
import cn.metast.tuoke.module.product.api.sku.ProductSkuApi;
import cn.metast.tuoke.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.metast.tuoke.module.product.api.spu.ProductSpuApi;
import cn.metast.tuoke.module.product.api.spu.dto.LiveShopGoodsRespDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "移动端 直播 - 商品")
@RestController
@RequestMapping("/api/goods")
@Slf4j
public class AppLiveShopGoodsController {

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


    @PostMapping("/getGoodsDetail")
    @Operation(summary = "直播-获取商品详情")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", dataType = "long", required = true),
    })
    public HttpRet<ShopGoodsDetailDTO> getGoodsDetail(long goodsId) {

        ProductSpuRespDTO spu = prproductSpuApi.getSpu(goodsId);

        if(spu == null){
            return HttpRet.fail("获取失败");
        }

        List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
        Map<String, String> merchantMap = dictDataList.stream()
                .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));

        ShopGoodsDetailDTO info = new ShopGoodsDetailDTO();
        info.setAnchorId(getLoginUserId());
        info.setBusinessName(merchantMap.get("businessName"));
        info.setBusinessLogo(merchantMap.get("businessLogo"));
        info.setEffectiveGoodsNum(100);
        info.setShopCarNum(0);
        info.setTotalSoldNum(100);

        List<ShopAttrCompose> composeList = new ArrayList<>();
        List<ProductSkuRespDTO> skuList = prproductSkuApi.getSkuListBySpuId(goodsId);
        for(ProductSkuRespDTO sku : skuList){
            ShopAttrCompose shopAttrCompose = skuToAttrCompose(sku);
            composeList.add(shopAttrCompose);
        }
        info.setComposeList(composeList);

        ShopGoods goods = spuToGoods(spu, merchantMap);
        info.setShopGoods(goods);

        return HttpRet.success("获取成功", info);
    }


    @PostMapping("/getLiveGoods")
    @Operation(summary = "直播-直播间商品列表")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "anchorId", value = "主播Id", dataType = "long", required = true),
    })
    public HttpRet<ShopLiveGoodsDTO> getLiveGoods(long anchorId) {

        if (anchorId <= 0) {
            return HttpRet.fail("主播ID不能小于等于0");
        }

        MemberUserRespDTO user = memberUserApi.getUserByAnchorId(anchorId);

//        List<ProductSpuRespDTO> spuPage = prproductSpuApi.getSpuPage(-1, 10);
        List<LiveShopGoodsRespDTO> liveShopGoodsPage = prproductSpuApi.getLiveShopGoodsPage(-1, 10, user != null ? user.getId():anchorId);

        List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
        Map<String, String> merchantMap = dictDataList.stream()
                .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));

        ShopLiveGoodsDTO shopLiveGoodsDTO = new ShopLiveGoodsDTO();
        shopLiveGoodsDTO.setBusinessId(Long.valueOf(merchantMap.get("businessId")));
        shopLiveGoodsDTO.setBusinessName(merchantMap.get("businessName"));
        shopLiveGoodsDTO.setBusinessLogo(merchantMap.get("businessLogo"));

        List<ShopLiveGoods> list = new ArrayList<>();
        for(LiveShopGoodsRespDTO spu : liveShopGoodsPage){
            ShopLiveGoods liveGoods = spuToLiveGoods(spu, merchantMap);
            list.add(liveGoods);
        }
        shopLiveGoodsDTO.setLiveGoodsList(list);

        return HttpRet.success("查询成功", shopLiveGoodsDTO);
    }


    @PostMapping("/getLiveGoodsList")
    @Operation(summary = "直播-直播开播商品列表")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataType = "int", required = false),
            @ApiImplicitParam(name = "status", value = "商品状态 0：全部 1：待上架 2：已上架 3：冻结中", dataType = "int", required = true),
    })
    public HttpRetArr<ShopGoodsDTO> getLiveGoodsList(int status, int pageIndex, int pageSize) {

        if (status < 0 && status > 3) {
            return HttpRetArr.fail("操作类型只能是0~3");
        }

        List<ProductSpuRespDTO> spuPage = prproductSpuApi.getSpuPage(pageIndex, pageSize);

        List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
        Map<String, String> merchantMap = dictDataList.stream()
                .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));

        List<ShopGoodsDTO> list = new ArrayList<>();
        for(ProductSpuRespDTO spu : spuPage){
            ShopGoodsDTO shopGoodsDTO = spuToGoodsDto(spu, merchantMap);
            list.add(shopGoodsDTO);
        }

        return HttpRetArr.success("查询成功", list);
    }


    @PostMapping("/getShopGoods")
    @Operation(summary = "直播-商品信息")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", dataType = "long", value = "商品id", required = true)
    })
    public HttpRet<ShopGoods> getShopGoods(long productId) {

        if (productId <= 0) {
            return HttpRet.fail("商品ID不能小于等于0");
        }

        ProductSpuRespDTO spuInfo = prproductSpuApi.getSpu(productId);

        List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
        Map<String, String> merchantMap = dictDataList.stream()
                .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));

        ShopGoods goods = spuToGoods(spuInfo, merchantMap);

        return HttpRet.success("查询成功", goods);
    }


    @PostMapping("/getAttrCompose")
    @Operation(summary = "直播-商品信息")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", dataType = "long", required = true),
    })
    public HttpRet<ShopAttrAndComposeDTO> getAttrCompose(long goodsId) {

//        ProductSkuRespDTO sku = prproductSkuApi.getSku(goodsId);
        List<Long> ids = new ArrayList();
        ids.add(goodsId);
        List<ProductSkuRespDTO> skuList = prproductSkuApi.getSkuListBySpuId(ids);
        for(ProductSkuRespDTO sku : skuList){

        }

        return HttpRet.success("查询成功");
    }


    @PostMapping("/saveLiveGoods")
    @Operation(summary = "直播-添加直播商品")
//    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", dataType = "long", required = true),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "int", required = true),
            @ApiImplicitParam(name = "optType", value = "操作类型 1：增加 2：删除", dataType = "int", required = true),
    })
    public HttpRet<HttpNone> saveLiveGoods(long goodsId, int sort, int optType) {

        if (goodsId <= 0) {
            return HttpRet.fail("商品ID不能小于等于0");
        }
        if (optType != 1 && optType != 2) {
            return HttpRet.fail("操作类型只能是1和2");
        }

        int i = prproductSpuApi.saveLiveGoods(goodsId, sort, optType);

        return HttpRet.success("操作完成");
    }


    @PostMapping("/setExplainStatus")
    @Operation(summary = "直播-设置讲解中状态")
    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "liveGoodsId", value = "直播商品id", dataType = "long", required = true),
//            @ApiImplicitParam(name = "roomId", value = "房间号", dataType = "long", required = true),
    })
    public HttpRet<JSONObject> setExplainStatus(long liveGoodsId, Long roomId) {

        int i = prproductSpuApi.setExplainStatus(liveGoodsId);
        if (i != 1) {
            return HttpRet.fail("设置失败");
        }

        LiveShopGoodsRespDTO liveShopGoods = prproductSpuApi.getLiveGoodsByGoodsId(liveGoodsId);
        JSONObject spuData = new JSONObject();
        spuData.put("id", liveShopGoods.getId());
        spuData.put("goodsId", liveShopGoods.getGoodsId());
        spuData.put("goodsPicture", liveShopGoods.getGoodsPicture());
        spuData.put("goodsPrice", liveShopGoods.getGoodsPrice());
        spuData.put("idExplain", liveShopGoods.getIfExplain());
        spuData.put("name", liveShopGoods.getName());
        spuData.put("sort", liveShopGoods.getSort());
        spuData.put("productLinks", liveShopGoods.getProductLinks());
        spuData.put("channelId", liveShopGoods.getChannelId());

        return HttpRet.success("设置成功", spuData);
    }


    @PostMapping("/updateLiveGoodsSort")
    @Operation(summary = "直播-修改直播间商品排序")
    @PermitAll
    @ApiImplicitParams({
            @ApiImplicitParam(name = "liveGoodsId", value = "直播商品id", dataType = "long", required = true),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "int", required = true),
    })
    public HttpRet<HttpNone> updateLiveGoodsSort(long liveGoodsId, int sort) {

        int i = prproductSpuApi.updateLiveGoodsSort(liveGoodsId, sort);

        if (i < 0) {
            return HttpRet.fail("设置失败");
        }
        if (i == 0) {
            return HttpRet.fail("请先添加商品到直播列表再设置");
        }

        return HttpRet.success("设置成功");
    }



    public ShopGoods spuToGoods(ProductSpuRespDTO spu, Map<String, String> merchantMap) {

        if(merchantMap == null){
            List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
            merchantMap = dictDataList.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));
        }

        ShopGoods goods = new ShopGoods();
        goods.setId(spu.getId());
        goods.setBusinessId(Long.valueOf(merchantMap.get("businessId")));
        goods.setAnchorId(getLoginUserId());
        goods.setChannelId(1L);
        goods.setCategoryId(spu.getCategoryId());
        goods.setGoodsName(spu.getName());
        goods.setPresent(spu.getDescription());
        goods.setDetailPicture(spu.getPicUrl());
        goods.setGoodsPicture(spu.getPicUrl());
        goods.setSort(0);
        goods.setNum(spu.getId().toString());
        goods.setStatus(spu.getStatus() == 1 ? 2 : 1);
        goods.setPrice(MoneyUtils.fenToYuan(spu.getPrice()).doubleValue());
        goods.setMyGoods(1);
        goods.setSoldNum(spu.getSalesCount());

        return goods;
    }
    public ShopGoodsDTO spuToGoodsDto(ProductSpuRespDTO spu, Map<String, String> merchantMap) {

        if(merchantMap == null){
            List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
            merchantMap = dictDataList.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));
        }

        ShopGoodsDTO goods = new ShopGoodsDTO();
        goods.setGoodsId(spu.getId());
        goods.setChannelId(1L);
        goods.setCategoryName("平台");
        goods.setCategoryId(spu.getCategoryId());
        goods.setGoodsName(spu.getName());
        goods.setPresent(spu.getDescription());
        goods.setSort(0);
        goods.setLiveSort(0);
        goods.setStatus(spu.getStatus() == 1 ? 2 : 1);
        goods.setDetailPicture(spu.getPicUrl());
        goods.setGoodsPicture(spu.getPicUrl());
        goods.setPrice(MoneyUtils.fenToYuan(spu.getPrice()).doubleValue());
        goods.setSoldNum(spu.getSalesCount());

        return goods;
    }
    public ShopLiveGoods spuToLiveGoods(LiveShopGoodsRespDTO spu, Map<String, String> merchantMap) {

        if(merchantMap == null){
            List<DictDataRespDTO> dictDataList = dictDataApi.getDictDataList("live_merchant");
            merchantMap = dictDataList.stream()
                    .collect(Collectors.toMap(DictDataRespDTO::getLabel, DictDataRespDTO::getValue));
        }

        ShopLiveGoods goods = new ShopLiveGoods();
        goods.setId(spu.getId());
        goods.setAnchorId(spu.getAnchorId());
        goods.setGoodsId(spu.getGoodsId());
        goods.setName(spu.getName());
        goods.setGoodsPicture(spu.getGoodsPicture());
        goods.setGoodsPrice(spu.getGoodsPrice());
        goods.setChannelId(spu.getChannelId());
        goods.setFavorablePrice(spu.getFavorablePrice());
        goods.setProductLinks(spu.getProductLinks());
        goods.setIdExplain(spu.getIfExplain());
        goods.setSort(spu.getSort());

        return goods;
    }
    public ShopAttrCompose skuToAttrCompose(ProductSkuRespDTO sku){
        if(sku == null){
            return null;
        }

        ShopAttrCompose compose = new ShopAttrCompose();
        compose.setId(sku.getId());
        compose.setGoodsId(sku.getSpuId());
        compose.setStock(sku.getStock());
        compose.setFavorablePrice(0);
        compose.setFrozenStock(0);
        compose.setPrice(MoneyUtils.fenToYuan(sku.getPrice()).doubleValue());

        List<ProductPropertyValueDetailRespDTO> properties = sku.getProperties();
        if(properties.size() >= 1){
            compose.setAttribute1Id(properties.get(0).getPropertyId());
            compose.setName1(properties.get(0).getValueName());
        }
        if(properties.size() >= 2){
            compose.setAttribute2Id(properties.get(1).getPropertyId());
            compose.setName2(properties.get(1).getValueName());
        }

        return compose;
    }

}
