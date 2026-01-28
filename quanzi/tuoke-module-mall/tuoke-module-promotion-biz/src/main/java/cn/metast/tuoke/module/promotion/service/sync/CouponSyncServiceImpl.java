package cn.metast.tuoke.module.promotion.service.sync;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.product.api.sku.ProductSkuApi;
import cn.metast.tuoke.module.product.api.spu.ProductSpuApi;
import cn.metast.tuoke.module.product.api.spu.ProductSyncApi;
import cn.metast.tuoke.module.promotion.dal.dataobject.coupon.CouponDO;
import cn.metast.tuoke.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import cn.metast.tuoke.module.promotion.dal.mysql.coupon.CouponMapper;
import cn.metast.tuoke.module.promotion.dal.mysql.coupon.CouponTemplateMapper;
import cn.metast.tuoke.module.system.api.social.SocialClientApi;
import cn.metast.tuoke.module.trade.api.order.TradeOrderApi;
import cn.metast.tuoke.module.trade.api.order.dto.TradeOrderRespDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 同步商品 Service 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
@Slf4j
public class CouponSyncServiceImpl implements CouponSyncService {
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private ProductSyncApi productSyncApi;
    @Resource
    private CouponTemplateMapper couponTemplateMapper;
    @Resource
    private CouponMapper couponMapper;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private TradeOrderApi tradeOrderApi;
    @Override
    public void syncTechCoupon() {
        String access_token = productSyncApi.getTechAccess_token();
        //查询小鹅通商品
        String url = "https://api.xiaoe-tech.com/xe.coupon.list/2.0.0";
        //首次传空
        Integer page =1;
        while (true) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("page", page);
                jsonObject.put("page_size", 50);
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
                        page = page+1;
                        JSONObject data = rs.getJSONObject("data");
                        if(data!=null) {
                            JSONArray list = data.getJSONArray("data");
                            if (list.isEmpty()) {
                                // 如果列表为空，说明没有更多数据，结束循环
                                break;
                            }
                            for (int i = 0; i < list.size(); i++) {
                                JSONObject duct = list.getJSONObject(i);
                                syncTechCouponDetail(duct, access_token);
                            }
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
    public void syncTechCouponDetail(JSONObject duct, String access_token) {
        String coupon_id = duct.getString("id");//优惠券id
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("coupon_id", coupon_id);
        jsonObject.put("access_token", access_token);
        String json = JSON.toJSONString(jsonObject);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity("https://api.xiaoe-tech.com/xe.coupon.detail/2.0.0", entity, JSONObject.class);
        if (responseEntity != null) {
            String jsn = JSONObject.toJSONString(responseEntity.getBody());
            JSONObject rs = JSONObject.parseObject(jsn);
            int errorCode = (int) rs.get("code");
            if (errorCode == 0) {
                JSONObject data = rs.getJSONObject("data");
                if (data != null) {
                    String title = data.getString("title");//优惠券标题
                    Integer price = data.getInteger("price");//满减券抵扣金额，单位：分
                    Integer count = data.getInteger("count");//发行总量
                    Integer receive_rule = data.getInteger("receive_rule");//每人限领
                    Integer require_price = data.getInteger("require_price");//最低消费金额
                    Integer spread_type = data.getInteger("spread_type");//推广方式 0-用户领取 1-商家发放 2-兑换码兑换 3-不限方式
                    Integer coupon_type = data.getInteger("coupon_type");//优惠券类型 1-通用优惠券,2-专属优惠券,3-员工优惠券
                    Integer valid_after = data.getInteger("valid_after");//领取生效字段
                    Integer valid_day = data.getInteger("valid_day");//领取后有效天数
                    String valid_at = data.getString("valid_at");//有效开始时间
                    String invalid_at = data.getString("invalid_at");//有效结束时间
                    Integer has_used= data.getInteger("has_used");//已使用总数
                    Integer has_received= data.getInteger("has_received");//已领取总数
                    CouponTemplateDO couponTemplateDO=new CouponTemplateDO();
                    couponTemplateDO.setCouponId(coupon_id);
                    couponTemplateDO.setName(title);
                    couponTemplateDO.setDescription("全部商品可用");
                    couponTemplateDO.setStatus(0);
                    if(count!=null) {
                        couponTemplateDO.setTotalCount(count);
                    }else{
                        couponTemplateDO.setTotalCount(-1);
                    }
                    if(receive_rule!=null) {//等与0,后面再看
                        if(receive_rule!=0) {
                            couponTemplateDO.setTakeLimitCount(receive_rule);
                        }else{
                            couponTemplateDO.setTakeLimitCount(-1);
                        }
                    }else{
                        couponTemplateDO.setTakeLimitCount(-1);
                    }
                    couponTemplateDO.setTakeType(2);//指定发放
                    couponTemplateDO.setUsePrice(require_price);
                    couponTemplateDO.setProductScope(1);//通用卷
                    couponTemplateDO.setValidityType(2);//领取之后可用
                    couponTemplateDO.setValidStartTime(LocalDateTime.parse(valid_at, formatter));
                    couponTemplateDO.setValidEndTime(LocalDateTime.parse(invalid_at, formatter));
                    couponTemplateDO.setFixedStartTerm(1);//默认
                    Long daysBetween = ChronoUnit.DAYS.between(LocalDateTime.parse(valid_at, formatter), LocalDateTime.parse(invalid_at, formatter));
                    couponTemplateDO.setFixedEndTerm(daysBetween.intValue());
                    couponTemplateDO.setDiscountType(1);
                    couponTemplateDO.setDiscountPrice(price);
                    couponTemplateDO.setTakeCount(has_received);
                    couponTemplateDO.setUseCount(has_used);
                    CouponTemplateDO couponTemplateDO1=couponTemplateMapper.selectByCouponId(coupon_id);
                    if(couponTemplateDO1==null) {
                        couponTemplateMapper.insert(couponTemplateDO);
                    }else{
                        couponTemplateDO.setId(couponTemplateDO1.getId());
                        couponTemplateMapper.updateById(couponTemplateDO);
                    }
                    log.info("优惠卷模板单同步成功--------{}"+coupon_id);
                }
            }
        }
    }

    @Override
    public void syncCouponUserRecord() {
        String access_token = productSyncApi.getTechAccess_token();
        couponTemplateMapper.selectList().forEach(couponTemplateDO -> {
            String coupon_id = couponTemplateDO.getCouponId();
            if(StringUtils.isNotBlank(coupon_id)) {
                Integer page = 1;
                while (true) {
                    // try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("page", page);
                    jsonObject.put("page_size", 500);
                    jsonObject.put("access_token", access_token);
                    jsonObject.put("cou_id", coupon_id);
                    String json = JSON.toJSONString(jsonObject);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> entity = new HttpEntity<>(json, headers);
                    ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity("https://api.xiaoe-tech.com/xe.coupon.user_list/2.0.0", entity, JSONObject.class);
                    if (responseEntity != null) {
                        String jsn = JSONObject.toJSONString(responseEntity.getBody());
                        JSONObject rs = JSONObject.parseObject(jsn);
                        log.error("收到响应1{}", rs.get("code"));
                        int errorCode = (int) rs.get("code");
                        if (errorCode == 0) {
                            page = page + 1;
                            JSONObject data = rs.getJSONObject("data");
                            if (data != null) {
                                JSONArray list = data.getJSONArray("list");
                                if (list.isEmpty()) {
                                    // 如果列表为空，说明没有更多数据，结束循环
                                    break;
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    JSONObject duct = list.getJSONObject(i);
                                    syncCouponDetail(couponTemplateDO, duct, access_token);
                                }
                            } else {
                                log.error("未收到响应1{}", JSONObject.toJSONString(rs));
                                break; // 如果没有收到响应，结束循环
                            }
                        } else {
                            log.error("未收到响应2{}", JSONObject.toJSONString(rs));
                            break; // 如果没有收到响应，结束循环
                        }
                    } else {
                        log.error("未收到响应3{}", access_token);
                        break; // 如果没有收到响应，结束循环
                    }
               /* } catch (Exception e) {
                    log.error("处理第 " + page + " 页时发生错误: " + e.getMessage());
                    page++; // 错误时，继续下一页
                }*/
                }
            }
        });

    }
    public void syncCouponDetail(CouponTemplateDO couponTemplateDO,JSONObject data, String access_token) {
            Long id = couponTemplateDO.getId();
            String name = couponTemplateDO.getName();
            Integer use_price = couponTemplateDO.getUsePrice();
            Integer product_scope =couponTemplateDO.getProductScope();
            Integer discount_type =couponTemplateDO.getDiscountType();
            Integer discount_price=couponTemplateDO.getDiscountPrice();
            Integer discount_limit_price=couponTemplateDO.getDiscountLimitPrice();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Integer is_use = data.getInteger("is_use");//是否已经使用 0未使用，1:已经使用, 3:已失效
            String user_id = data.getString("user_id");//用户ID
            String receive_at = data.getString("receive_at");//有效开始时间
            String valid_at = data.getString("valid_at");//有效开始时间
            String invalid_at = data.getString("invalid_at");//有效结束时间
            String order_id = data.getString("order_id");//订单号
            String userRecordId = data.getString("id");//用户领取的优惠券ID
            CouponDO couponDO=new CouponDO();
            couponDO.setTemplateId(id);
            couponDO.setName(name);
            couponDO.setTakeType(2);//默认主动领取
            couponDO.setUsePrice(use_price);
            couponDO.setValidStartTime(LocalDateTime.parse(valid_at, formatter));
            couponDO.setValidEndTime(LocalDateTime.parse(invalid_at, formatter));
            couponDO.setProductScope(product_scope);
            couponDO.setDiscountType(discount_type);
            couponDO.setDiscountPrice(discount_price);
            couponDO.setDiscountLimitPrice(discount_limit_price);
            couponDO.setUserCouponId(userRecordId);
            MemberUserRespDTO memberUserRespDTO=memberUserApi.selectByTechUserId(user_id);
            if(memberUserRespDTO!=null){
                couponDO.setUserId(memberUserRespDTO.getId());
            }else{
                return;
            }
           TradeOrderRespDTO tradeOrderDO=tradeOrderApi.selectByNo(order_id);
            if(tradeOrderDO!=null){
                couponDO.setUseOrderId(tradeOrderDO.getId());
            }
            if(StringUtils.isNotBlank(receive_at)){
                couponDO.setCreateTime(LocalDateTime.parse(receive_at, formatter));
            }
            if(is_use==1){
                String use_at = data.getString("use_at");//有效开始时间
                if(StringUtils.isNotBlank(use_at)){
                    couponDO.setUseTime(LocalDateTime.parse(use_at, formatter));
                }
                couponDO.setStatus(2);
            }else if(is_use==0){
                couponDO.setStatus(1);
            }else if(is_use==3){
                couponDO.setStatus(3);
            }else{
                couponDO.setStatus(0);
            }
            CouponDO couponDO1=couponMapper.selectByUserCouponId(couponTemplateDO.getCouponId());
            if(couponDO1==null) {
                couponMapper.insert(couponDO);
            }else{
                couponDO.setId(couponDO1.getId());
                couponMapper.updateById(couponDO);
            }
            log.info("同步优惠券成功{}",userRecordId);
    }
}
