package cn.metast.tuoke.module.product.service.sync;
import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.product.controller.admin.spu.vo.ProductSkuSaveReqVO;
import cn.metast.tuoke.module.product.controller.admin.spu.vo.ProductSpuSaveReqVO;
import cn.metast.tuoke.module.product.dal.dataobject.category.ProductCategoryDO;
import cn.metast.tuoke.module.product.dal.dataobject.property.ProductPropertyDO;
import cn.metast.tuoke.module.product.dal.dataobject.property.ProductPropertyValueDO;
import cn.metast.tuoke.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.metast.tuoke.module.product.dal.mysql.category.ProductCategoryMapper;
import cn.metast.tuoke.module.product.dal.mysql.property.ProductPropertyMapper;
import cn.metast.tuoke.module.product.dal.mysql.property.ProductPropertyValueMapper;
import cn.metast.tuoke.module.product.dal.mysql.spu.ProductSpuMapper;
import cn.metast.tuoke.module.product.enums.spu.ProductSpuStatusEnum;
import cn.metast.tuoke.module.product.service.brand.ProductBrandService;
import cn.metast.tuoke.module.product.service.category.ProductCategoryService;
import cn.metast.tuoke.module.product.service.sku.ProductSkuService;
import cn.metast.tuoke.module.system.api.social.SocialClientApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.getMinValue;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.getSumValue;
import static cn.metast.tuoke.module.product.dal.dataobject.category.ProductCategoryDO.CATEGORY_LEVEL;
import static cn.metast.tuoke.module.product.enums.ErrorCodeConstants.SPU_NOT_EXISTS;
import static cn.metast.tuoke.module.product.enums.ErrorCodeConstants.SPU_SAVE_FAIL_CATEGORY_LEVEL_ERROR;

/**
 * 同步商品 Service 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
@Slf4j
public class ProductSyncServiceImpl implements ProductSyncService {
    @Resource
    private SocialClientApi socialClientApi;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ProductSpuMapper productSpuMapper;
    @Resource
    @Lazy // 循环依赖，避免报错
    private ProductSkuService productSkuService;
    @Resource
    private ProductBrandService brandService;
    @Resource
    private ProductCategoryService categoryService;

    @Resource
    private ProductPropertyMapper productPropertyMapper;
    @Resource
    private ProductPropertyValueMapper productPropertyValueMapper;
    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public String getAccess_token() {
        //获得key和secret
        Map<String, Object> client = socialClientApi.getWxxdKey();
        String APPID = (String)client.get("client_id");
        String APPSECREY = (String)client.get("secret_key");
        if(StringUtils.isBlank(APPID)){
            throw new RuntimeException("请检查微信小店配置");
        }
        //获得token
        String redisKey = APPID+"_"+APPSECREY;
        String token = stringRedisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(token)) {
            try {
                String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                        "&appid=" + APPID + "&secret=" + APPSECREY;
                String json = restTemplate.getForObject(url, String.class);
                JSONObject myJson = JSONObject.parseObject(json);
                token=myJson.get("access_token").toString();
                stringRedisTemplate.opsForValue().set(redisKey, token, 7200, TimeUnit.SECONDS);
            }catch (Exception e){
                log.info("微信小店token 获取失败,请检查白名单获取");
            }

        }
        return token;
    }
    @Override
    public void syncProductSpu() {
        String access_token = getAccess_token();
        //查询微信小店商品
        String url = "https://api.weixin.qq.com/channels/ec/product/list/get?access_token=" + access_token;
        //首次传空
        String next_key = "";
        while (true) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", null);
            jsonObject.put("page_size", 10);
            jsonObject.put("next_key", next_key);
            String json = JSON.toJSONString(jsonObject);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, json, JSONObject.class);
            if (responseEntity != null) {
                String jsn = JSONObject.toJSONString(responseEntity.getBody());
                JSONObject rs = JSONObject.parseObject(jsn);
                int errorCode = (int) rs.get("errcode");
                if (errorCode == 0) {
                    Integer total_num = (Integer) rs.get("total_num");
                    if (total_num > 0) {
                        next_key = (String) rs.get("next_key");
                        JSONArray product_ids = rs.getJSONArray("product_ids");
                        for (int i = 0; i < product_ids.size(); i++) {
                            String product_id = product_ids.getString(i);
                            syncProductSpuDetail(product_id, access_token);
                        }
                    }
                } else {
                    log.info("获取失败:" + errorCode + "," + rs.get("errmsg"));
                }
            }
        }
    }
    public void syncProductSpuDetail(String productId,String access_token) {
        //查询商品详情
        String url = "https://api.weixin.qq.com/channels/ec/product/get?access_token=" + access_token;
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("product_id",productId);
        //1:获取线上数据 2:获取草稿数据 3:同时获取线上和草稿数据
        Integer data_type=3;
        jsonObject.put("data_type",data_type);
        String json1 = JSON.toJSONString(jsonObject);
        ResponseEntity<JSONObject > responseEntity = restTemplate.postForEntity(url, json1, JSONObject.class);
        if(responseEntity!=null){
            String jsn = JSONObject.toJSONString(responseEntity.getBody());
            JSONObject rs = JSONObject.parseObject(jsn);
            int errorCode=(int)rs.get("errcode");
            if(errorCode==0){
                //插入商品
                if(data_type==1){

                }
                /*if(data_type==3){
                   JSONObject duct = rs.getJSONObject("product");
                   if(duct!=null) {
                       Integer product_id = (Integer) duct.get("product_id");//小店内部商品ID
                       String out_product_id = (String) duct.get("out_product_id");//外部平台自定义商品ID。如果添加时没录入，回包可能不包含该字段
                       String title = (String) duct.get("title");//标题
                       String sub_title = (String) duct.get("sub_title");//副标题
                       JSONArray head_imgs = duct.getJSONArray("head_imgs");//主图,多张,列表,最多9张,每张不超过2MB
                       JSONObject desc_info=duct.getJSONObject("desc_info");//商品详情
                       if(desc_info!=null){
                           JSONArray imgs = duct.getJSONArray("imgs");//商品详情图片(最多20张)。如果添加时没录入，回包可能不包含该字段
                           String desc = (String) duct.get("desc");//商品详情文字。如果添加时没录入，回包可能不包含该字段
                       }
                       Integer deliver_method = (Integer) duct.get("deliver_method");//发货方式：0-快递发货；1-无需快递，手机号发货；3-无需快递，可选发货账号类型，默认为0，若为无需快递，则无需填写运费模版id
                       Integer deliver_acct_type = (Integer) duct.get("deliver_acct_type");//发货账号：1-微信openid；....
                        //express_info	object	运费信息	-
                        //aftersale_desc	string	售后说明	-
                        //limited_info	object	限购信息	-
                        //extra_service	object	额外服务	-
                        //status	number	商品线上状态，edit_product和product都会返回该字段	枚举值
                        //edit_status	number	商品草稿状态，以edit_product字段返回的值为准，product不返回
                       ProductSpuSaveReqVO createReqVO=new ProductSpuSaveReqVO();


                       JSONArray cats_v2 = duct.getJSONArray("cats_v2");//新类目树--商家需要先申请可使用类目
                       for (int i = 0; i < cats_v2.size(); i++) {
                           JSONObject cats = cats_v2.getJSONObject(i);
                           Integer cat_id = (Integer) cats.get("cat_id");
                       }
                       Integer brand_id = (Integer) duct.get("brand_id");//品牌id
                       // 校验分类、品牌--------先加到草稿里，品牌分类同步后完善--先默认一个
                       createReqVO.setCategoryId(4L);

                       //校验 SKU
                       JSONArray skus = duct.getJSONArray("skus");//sku信息
                       for (int i = 0; i < skus.size(); i++) {
                           JSONObject sku = skus.getJSONObject(i);
                           ProductSkuSaveReqVO skuSaveReqVO = new ProductSkuSaveReqVO();
                       }
                       List<ProductSkuSaveReqVO> skuSaveReqList = createReqVO.getSkus();
                       productSkuService.validateSkuList(skuSaveReqList, createReqVO.getSpecType());

                       ProductSpuDO spu = BeanUtils.toBean(createReqVO, ProductSpuDO.class);
                       // 初始化 SPU 中 SKU 相关属性
                       initSpuFromSkus(spu, skuSaveReqList);
                       // 插入 SPU
                       productSpuMapper.insert(spu);
                       // 插入 SKU
                       productSkuService.createSkuList(spu.getId(), skuSaveReqList);


                   }
                }*/
            }
        }
    }
    @Override
    public String getTechAccess_token() {
        //获得key和secret
        Map<String, Object> client = socialClientApi.getWxxdKey();
        String app_id = (String)client.get("app_id");
        String client_id = (String)client.get("client_id");
        String secret_key = (String)client.get("secret_key");
        if(StringUtils.isBlank(app_id)){
            throw new RuntimeException("请检查小鹅通配置");
        }
        //获得token
        String redisKey = app_id+"_"+client_id+"_"+secret_key;
        String token = stringRedisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(token)) {
            try {
                String url = "https://api.xiaoe-tech.com/token?grant_type=client_credential" +
                        "&app_id=" + app_id + "&client_id=" + client_id+ "&secret_key=" + secret_key;
                String json = restTemplate.getForObject(url, String.class);
                JSONObject myJson = JSONObject.parseObject(json);
                if(myJson.getInteger("code")==0) {
                    JSONObject data = myJson.getJSONObject("data");
                    token = data.get("access_token").toString();
                    Integer expires_in = data.getInteger("expires_in");
                    stringRedisTemplate.opsForValue().set(redisKey, token, 7100, TimeUnit.SECONDS);
                }
            }catch (Exception e){
                log.info("微信小店token 获取失败,请检查白名单获取");
            }
        }
        return token;
    }
    @Override
    public void syncTechProductSpu() {
        String access_token = getTechAccess_token();
        //查询小鹅通商品
        String url = "https://api.xiaoe-tech.com/xe.goods.list.get/4.0.0";
        //首次传空
        Integer page =1;
        while (true) {
            try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page", page);
            jsonObject.put("page_size", 10);
            jsonObject.put("resource_type", 21);
            jsonObject.put("access_token", access_token);
            String json = JSON.toJSONString(jsonObject);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, entity, JSONObject.class);
                if (responseEntity != null) {
                    String jsn = JSONObject.toJSONString(responseEntity.getBody());
                    JSONObject rs = JSONObject.parseObject(jsn);
                    int errorCode = (int) rs.get("code");
                    if (errorCode == 0) {
                        JSONObject data = rs.getJSONObject("data");
                        if (data!=null) {
                            page = data.getInteger("current_page")+1;
                            JSONArray list = data.getJSONArray("list");
                            if (list.isEmpty()) {
                                // 如果列表为空，说明没有更多数据，结束循环
                                break;
                            }
                            for (int i = 0; i < list.size(); i++) {
                                JSONObject duct = list.getJSONObject(i);
                                syncTechProductSpuDetail(duct, access_token);
                            }
                        }else {
                            log.error("获取失败:" + errorCode + "," + rs.get("msg"));
                            break; // 如果请求失败，结束循环
                        }
                    } else {
                        log.error("未收到响应");
                        break; // 如果没有收到响应，结束循环
                    }
                } else {
                    log.error("未收到响应");
                    break; // 如果没有收到响应，结束循环
                }
            } catch (Exception e) {
                log.error("处理第 " + page + " 页时发生错误: " + e.getMessage());
                page++; // 错误时，继续下一页
           }
        }
    }

    public void syncTechProductSpuDetail(JSONObject duct,String access_token) {
        String url = "https://api.xiaoe-tech.com/xe.goods.detail.get/4.0.0";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", access_token);
        jsonObject.put("body", "stock,sku,attr");
        JSONArray resources=new JSONArray();
        JSONObject json1 = new JSONObject();
        json1.put("type", 21);
        String resource_id = duct.getString("resource_id");//资源id
        JSONArray idsArray = new JSONArray();
        idsArray.add(resource_id);
        json1.put("ids", idsArray);
        resources.add(json1);
        jsonObject.put("resources", resources);
        String json = JSON.toJSONString(jsonObject);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, entity, JSONObject.class);
        if (responseEntity != null) {
            String jsn = JSONObject.toJSONString(responseEntity.getBody());
            JSONObject rs = JSONObject.parseObject(jsn);
            int errorCode = (int) rs.get("code");
            if (errorCode == 0) {
                JSONArray data = rs.getJSONArray("data");
                if (data!=null) {
                     for (int i = 0; i < data.size(); i++){
                         JSONObject product = data.getJSONObject(i);
                         String goods_category_id=product.getString("goods_category_id");//商品分类id
                         String goods_name=product.getString("goods_name");//商品名称
                         JSONArray goods_Imgs=product.getJSONArray("goods_img");//商品封面图（默认封面图）
                         String goods_brief_text=product.getString("goods_brief_text");//商品简介
                         String goods_detail_text=product.getString("goods_detail_text");//商品详情/买点
                         Integer sale_status=product.getInteger("sale_status");//0下架 1上架 2待上架
                         String goods_tag=product.getString("goods_tag");//商品标签
                         Integer has_distribute=product.getInteger("has_distribute");//是否参与推广分销
                         Integer distribution_pattern=product.getInteger("distribution_pattern")!=null?product.getInteger("distribution_pattern"):1;//配送方式-默认快递发货

                         //都在最外城
                         String sku_spec_code=product.getString("sku_spec_code");//sku规格编码
                         Integer sku_cost_price=product.getInteger("sku_cost_price");//sku成本价，单位：分
                         String sku_volume=product.getString("sku_volume");//sku体积
                         String sku_weight=product.getString("sku_weight");//sku重量

                        if("心相印金装金典100抽三层塑装纸面巾6包".equals(goods_name)){
                            log.info("商品名称:"+goods_name);
                        }


                         ProductSpuSaveReqVO createReqVO=new ProductSpuSaveReqVO();
                         createReqVO.setName(goods_name);
                         createReqVO.setKeyword(goods_tag);
                         createReqVO.setIntroduction(goods_brief_text);
                         createReqVO.setDescription(goods_detail_text);
                         List<Integer> distribution = new ArrayList<>();
                         distribution.add(distribution_pattern);
                         createReqVO.setDeliveryTypes(distribution);
                         createReqVO.setResourceId(resource_id);
                         //获取分类
                         // createReqVO.setCategoryId("");//需要先查询在添加
                         JSONArray categories = product.getJSONArray("category");
                         if(categories!=null){
                             if (categories != null && categories.size() > 0) {
                                 // 获取最后一个分类的title
                                 JSONObject lastCategory = categories.getJSONObject(categories.size() - 1);
                                 String lastCategoryTitle = lastCategory.getString("title");
                                 System.out.println("最后一个分类的title: " + lastCategoryTitle);
                                 ProductCategoryDO productCategoryDO = productCategoryMapper.selectByAndName(lastCategoryTitle);
                                 if (productCategoryDO != null) {
                                     createReqVO.setCategoryId(productCategoryDO.getId());
                                 }
                             }
                         }else{
                             log.info("没有分类");
                             break;
                         }
                         if(createReqVO.getCategoryId()==null){
                             log.info("没有分类");
                             break;
                         }
                         createReqVO.setBrandId(Long.valueOf(5000L));//先默认写死--自营
                         createReqVO.setSubCommissionType(false);//默认设置
                         if(goods_Imgs.size()>0) {
                             createReqVO.setPicUrl(goods_Imgs.getString(0));
                             List<String> imgs=new ArrayList<>();
                             for(int j = 0; j < goods_Imgs.size(); j++){
                                 String img=goods_Imgs.getString(j);
                                 imgs.add(img);
                             }
                             createReqVO.setSliderPicUrls(imgs);
                         }
                         createReqVO.setSort(1);//先默认
                         createReqVO.setStatus(String.valueOf(sale_status));
                         //sku信息
                         JSONArray sku=product.getJSONArray("sku");
                         List<ProductSkuSaveReqVO> skuSaveReqList =new ArrayList<>();
                         if(sku.size()>0){
                             for (int j = 0; j < sku.size(); j++){
                                 JSONObject sku_item = sku.getJSONObject(j);
                                 String sku_sku_id=sku_item.getString("sku_id");
                                 Integer value_count=sku_item.getInteger("value_count");//1-单规格 2-多规格--我们系统是0和1
                                 if(value_count==1){
                                     createReqVO.setSpecType(false);
                                 }else if(value_count==2){
                                     createReqVO.setSpecType(true);
                                 }else{
                                     createReqVO.setSpecType(false);
                                 }
                                 Integer sku_price=sku_item.getInteger("sku_price");//商品价格，单位使用：分
                                 String sku_img=sku_item.getString("sku_img");
                                 String sku_id=sku_item.getString("sku_id");
                                 ProductSkuSaveReqVO skuItem=new ProductSkuSaveReqVO();
                                 skuItem.setOrderSkuId(sku_id);
                                 skuItem.setPrice(sku_price);
                                 skuItem.setMarketPrice(sku_price);
                                 skuItem.setCostPrice(sku_cost_price!=null?sku_cost_price:sku_price);
                                 skuItem.setPicUrl(sku_img);
                                 skuItem.setBarCode(sku_spec_code);
                                 skuItem.setWeight(StringUtils.isNotBlank(sku_weight)?Double.parseDouble(sku_weight):null);
                                 skuItem.setVolume(StringUtils.isNotBlank(sku_volume)?Double.parseDouble(sku_volume):null);
                                 //查询库存
                                 JSONArray stock=product.getJSONArray("stock");
                                 for (int k = 0; k < stock.size(); k++){
                                     JSONObject stock_item = stock.getJSONObject(k);
                                     Integer left_num=stock_item.getInteger("left_num");
                                     Integer sell_num=stock_item.getInteger("sell_num");
                                     String stock_sku_id=stock_item.getString("sku_id");
                                     if(sku_sku_id.equals(stock_sku_id)){
                                         skuItem.setStock(left_num);//库存
                                         skuItem.setSalesCount(sell_num);//销量
                                         break;
                                     }
                                 }
                                 //attr 属性值
                                 JSONArray attr=product.getJSONArray("attr");
                                 List<ProductSkuSaveReqVO.Property> properties=new ArrayList<>();
                                 if(attr!=null && attr.size() > 0) {
                                     for (int k = 0; k < attr.size(); k++) {
                                         JSONObject attr_item = attr.getJSONObject(k);
                                         String attr_name = attr_item.getString("attr_name");
                                         String attr_value = attr_item.getString("attr_value");
                                         String attr_sku_id = attr_item.getString("sku_id");
                                         //处理属性值
                                         if (sku_sku_id.equals(attr_sku_id)) {
                                             //查询属性是否存在
                                             ProductPropertyDO productPropertyDO = productPropertyMapper.selectByName(attr_name);
                                             if (productPropertyDO != null) {
                                                 ProductPropertyValueDO productPropertyValueDO = productPropertyValueMapper.selectByName(productPropertyDO.getId(), attr_value);
                                                 if (productPropertyValueDO != null) {
                                                     //存入集合
                                                     ProductSkuSaveReqVO.Property property = new ProductSkuSaveReqVO.Property();
                                                     property.setPropertyId(productPropertyDO.getId());
                                                     property.setPropertyName(attr_name);
                                                     property.setValueId(productPropertyValueDO.getId());
                                                     property.setValueName(attr_value);
                                                     properties.add(property);
                                                 } else {
                                                     //value值不存在
                                                     ProductPropertyValueDO productPropertyValueInsert = new ProductPropertyValueDO();
                                                     productPropertyValueInsert.setPropertyId(productPropertyDO.getId());
                                                     productPropertyValueInsert.setName(attr_value);
                                                     productPropertyValueMapper.insert(productPropertyValueInsert);
                                                     //存入集合
                                                     ProductSkuSaveReqVO.Property property = new ProductSkuSaveReqVO.Property();
                                                     property.setPropertyId(productPropertyDO.getId());
                                                     property.setPropertyName(attr_name);
                                                     property.setValueId(productPropertyValueInsert.getId());
                                                     property.setValueName(attr_value);
                                                     properties.add(property);
                                                 }
                                             } else {
                                                 //插入属性
                                                 ProductPropertyDO productPropertyInsert = new ProductPropertyDO();
                                                 productPropertyInsert.setName(attr_name);
                                                 productPropertyMapper.insert(productPropertyInsert);
                                                 Long propertyId = productPropertyInsert.getId();
                                                 //插入value
                                                 ProductPropertyValueDO productPropertyValueInsert = new ProductPropertyValueDO();
                                                 productPropertyValueInsert.setPropertyId(propertyId);
                                                 productPropertyValueInsert.setName(attr_value);
                                                 productPropertyValueMapper.insert(productPropertyValueInsert);
                                                 //存入集合
                                                 ProductSkuSaveReqVO.Property property = new ProductSkuSaveReqVO.Property();
                                                 property.setPropertyId(propertyId);
                                                 property.setPropertyName(attr_name);
                                                 property.setValueId(productPropertyValueInsert.getId());
                                                 property.setValueName(attr_value);
                                                 properties.add(property);
                                             }
                                             break;
                                         }
                                     }
                                 }else{
                                     createReqVO.setSpecType(false);
                                 }
                                 skuItem.setProperties(properties);
                                 skuSaveReqList.add(skuItem);
                             }
                         }
                         //小鹅通资源id,更新用的
                         ProductSpuDO updateReqVO= productSpuMapper.getResourceId(resource_id);
                         if(updateReqVO==null) {
                             // 校验分类、品牌
                             validateCategory(createReqVO.getCategoryId());
                             brandService.validateProductBrand(createReqVO.getBrandId());
                             // 校验 SKU
                             productSkuService.validateSkuList(skuSaveReqList, createReqVO.getSpecType());

                             ProductSpuDO spu = BeanUtils.toBean(createReqVO, ProductSpuDO.class);
                             // 初始化 SPU 中 SKU 相关属性
                             initSpuFromSkus(spu, skuSaveReqList);
                             // 插入 SPU
                             productSpuMapper.insert(spu);
                             // 插入 SKU
                             productSkuService.createSkuList(spu.getId(), skuSaveReqList);
                         }else{
                             //更新数据
                             // 校验 SPU 是否存在
                             ProductSpuDO spu = validateSpuExists(updateReqVO.getId());
                             // 校验分类、品牌
                             validateCategory(updateReqVO.getCategoryId());
                             brandService.validateProductBrand(updateReqVO.getBrandId());
                             // 校验SKU
                             productSkuService.validateSkuList(skuSaveReqList, createReqVO.getSpecType());
                             //重新赋值
                             createReqVO.setId(updateReqVO.getId());
                             // 更新 SPU
                             ProductSpuDO updateObj = BeanUtils.toBean(createReqVO, ProductSpuDO.class).setStatus(spu.getStatus());
                             initSpuFromSkus(updateObj, skuSaveReqList);
                             productSpuMapper.updateById(updateObj);
                             // 批量更新 SKU
                             productSkuService.updateSkuList(updateObj.getId(), skuSaveReqList);
                         }

                     }
                }
            }
        }
    }
    /**
     * 校验商品分类是否合法
     *
     * @param id 商品分类编号
     */
    private void validateCategory(Long id) {
        categoryService.validateCategory(id);
        // 校验层级
       /* if (categoryService.getCategoryLevel(id) < CATEGORY_LEVEL) {
            throw exception(SPU_SAVE_FAIL_CATEGORY_LEVEL_ERROR);
        }*/
    }
    /**
     * 基于 SKU 的信息，初始化 SPU 的信息
     * 主要是计数相关的字段，例如说市场价、最大最小价、库存等等
     *
     * @param spu  商品 SPU
     * @param skus 商品 SKU 数组
     */
    private void initSpuFromSkus(ProductSpuDO spu, List<ProductSkuSaveReqVO> skus) {
        // sku 单价最低的商品的价格
        spu.setPrice(getMinValue(skus, ProductSkuSaveReqVO::getPrice));
        // sku 单价最低的商品的市场价格
        spu.setMarketPrice(getMinValue(skus, ProductSkuSaveReqVO::getMarketPrice));
        // sku 单价最低的商品的成本价格
        spu.setCostPrice(getMinValue(skus, ProductSkuSaveReqVO::getCostPrice));
        // skus 库存总数
        spu.setStock(getSumValue(skus, ProductSkuSaveReqVO::getStock, Integer::sum));
        // 若是 spu 已有状态则不处理
        if (spu.getStatus() == null) {
            spu.setStatus(ProductSpuStatusEnum.ENABLE.getStatus()); // 默认状态为上架
            spu.setSalesCount(0); // 默认商品销量
            spu.setBrowseCount(0); // 默认商品浏览量
        }
        //小鹅通的销量
        spu.setSalesCount(getSumValue(skus, ProductSkuSaveReqVO::getSalesCount, Integer::sum));
    }
    private ProductSpuDO validateSpuExists(Long id) {
        ProductSpuDO spuDO = productSpuMapper.selectById(id);
        if (spuDO == null) {
            throw exception(SPU_NOT_EXISTS);
        }
        return spuDO;
    }
    @Override
    public String getCategory() {
        String txtFilePath = "E:\\json\\json.txt"; // 替换为您的文本文件路径
        //String jsonFilePath = "E:\\json\\toyourfile.json"; // 输出的JSON文件路径
            // 读取文本文件内容
            String content = null;
            try {
                content = new String(Files.readAllBytes(Paths.get(txtFilePath)), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 将文本内容转换为JSONObject
             JSONObject jsonObject = JSONObject.parseObject(content);
             JSONArray jsonArray = jsonObject.getJSONArray("category_hierarchy");
             for(int i = 0; i < jsonArray.size(); i++){
                 JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                 //一级分类
                 String level1_category=jsonObject1.getString("level1_category");
                 ProductCategoryDO productCategoryDO = productCategoryMapper.selectByAndName(level1_category);
                 if (productCategoryDO == null) {
                     productCategoryDO=new ProductCategoryDO();
                     productCategoryDO.setName(level1_category);
                     productCategoryDO.setParentId(ProductCategoryDO.PARENT_ID_NULL);
                     productCategoryDO.setSort(0);
                     productCategoryDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
                     productCategoryDO.setPicUrl("https://1");
                     productCategoryMapper.insert(productCategoryDO);
                 }
                 JSONArray level2_categories = jsonObject1.getJSONArray("level2_categories");
                 for(int j = 0; j < level2_categories.size(); j++){
                     JSONObject jsonObject2 = level2_categories.getJSONObject(j);
                     //二级分类
                     String level2_category=jsonObject2.getString("level2_category");
                     ProductCategoryDO productCategoryDO2 = productCategoryMapper.selectByAndName(level2_category);
                     if (productCategoryDO2 == null) {
                         productCategoryDO2=new ProductCategoryDO();
                         productCategoryDO2.setName(level2_category);
                         productCategoryDO2.setParentId(productCategoryDO.getId());
                         productCategoryDO2.setSort(0);
                         productCategoryDO2.setStatus(CommonStatusEnum.ENABLE.getStatus());
                         productCategoryDO2.setPicUrl("https://1");
                         productCategoryMapper.insert(productCategoryDO2);
                     }
                     JSONArray level3_categories = jsonObject2.getJSONArray("level3_categories");
                     for(int k = 0; k < level3_categories.size(); k++){
                         //三级分类
                         String level3Category = level3_categories.getString(k);
                         ProductCategoryDO productCategoryDO3 = productCategoryMapper.selectByAndName(level3Category);
                         if (productCategoryDO3 == null) {
                             productCategoryDO3=new ProductCategoryDO();
                             productCategoryDO3.setName(level3Category);
                             productCategoryDO3.setParentId(productCategoryDO2.getId());
                             productCategoryDO3.setSort(0);
                             productCategoryDO3.setStatus(CommonStatusEnum.ENABLE.getStatus());
                             productCategoryDO3.setPicUrl("https://1");
                             productCategoryMapper.insert(productCategoryDO3);
                         }
                     }
                 }
             }





            // 将JSON字符串写入文件
            //Files.write(Paths.get(jsonFilePath), jsonString.getBytes(StandardCharsets.UTF_8));
            //System.out.println("转换完成，JSON文件保存在: " + jsonFilePath);
        return null;
    }
}
