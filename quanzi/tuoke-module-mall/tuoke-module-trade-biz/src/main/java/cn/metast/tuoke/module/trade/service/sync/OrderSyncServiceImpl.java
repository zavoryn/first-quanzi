package cn.metast.tuoke.module.trade.service.sync;
import cn.metast.tuoke.framework.common.enums.CommonStatusEnum;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.product.api.property.dto.ProductPropertyValueDetailRespDTO;
import cn.metast.tuoke.module.product.api.sku.ProductSkuApi;
import cn.metast.tuoke.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.metast.tuoke.module.product.api.spu.ProductSpuApi;
import cn.metast.tuoke.module.product.api.spu.ProductSyncApi;
import cn.metast.tuoke.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.metast.tuoke.module.system.api.social.SocialClientApi;
import cn.metast.tuoke.module.trade.controller.app.order.vo.AppTradeOrderCreateReqVO;
import cn.metast.tuoke.module.trade.dal.dataobject.aftersale.AfterSaleDO;
import cn.metast.tuoke.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import cn.metast.tuoke.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.metast.tuoke.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.metast.tuoke.module.trade.dal.mysql.aftersale.AfterSaleLogMapper;
import cn.metast.tuoke.module.trade.dal.mysql.aftersale.AfterSaleMapper;
import cn.metast.tuoke.module.trade.dal.mysql.delivery.DeliveryExpressMapper;
import cn.metast.tuoke.module.trade.dal.mysql.order.TradeOrderItemMapper;
import cn.metast.tuoke.module.trade.dal.mysql.order.TradeOrderMapper;
import cn.metast.tuoke.module.trade.enums.order.TradeOrderItemAfterSaleStatusEnum;
import cn.metast.tuoke.module.trade.service.aftersale.AfterSaleLogService;
import cn.metast.tuoke.module.trade.service.delivery.DeliveryExpressService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 同步商品 Service 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
@Slf4j
public class OrderSyncServiceImpl implements OrderSyncService {
    @Resource
    private SocialClientApi socialClientApi;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ProductSyncApi productSyncApi;
    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private TradeOrderMapper tradeOrderMapper;
    @Resource
    private TradeOrderItemMapper tradeOrderItemMapper;
    @Resource
    private DeliveryExpressMapper deliveryExpressMapper;
    @Resource
    private AfterSaleMapper afterSaleMapper;
    @Override
    public void syncTechProductOrder() {
        String access_token = productSyncApi.getTechAccess_token();
        String url = "https://api.xiaoe-tech.com/xe.ecommerce.order.list/1.0.0";
        // 设置查询时间范围
        LocalDate startDate = LocalDate.of(2023, 12, 1);
        //LocalDate startDate = LocalDate.of(2025, 10, 1);
        LocalDate endDate = LocalDate.now();

        // 按31天分段查询
        LocalDate currentStart = startDate;

        while (currentStart.isBefore(endDate) || currentStart.isEqual(endDate)) {
            LocalDate currentEnd = currentStart.plusDays(31);
            if (currentEnd.isAfter(endDate)) {
                currentEnd = endDate;
            }

            // 查询当前时间段的所有订单
            boolean hasMoreData = queryOrdersByTimeRange(
                    access_token, url,
                    currentStart.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    currentEnd.atTime(23, 59, 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );

            if (!hasMoreData) {
                break;
            }

            // 移动到下一个时间段
            currentStart = currentEnd.plusDays(1);

            // 添加延迟避免频率限制
            try {
                Thread.sleep(100); // 每秒10次，留有余量
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    private boolean queryOrdersByTimeRange(String accessToken, String url, String startTime, String endTime) {
        int page = 1;
        int maxRetry = 3;
        int retryCount = 0;
        int maxPages = 1000; // 防止死循环的最大页数

        while (page <= maxPages) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("access_token", accessToken);
                jsonObject.put("page", page);
                jsonObject.put("page_size", 100);
                jsonObject.put("order_type", "1");
                jsonObject.put("created_time_start", startTime);
                jsonObject.put("created_time_end", endTime);
                String json = JSON.toJSONString(jsonObject);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(json, headers);

                ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, entity, JSONObject.class);

                if (responseEntity == null || responseEntity.getBody() == null) {
                    log.error("未收到响应，时间范围: {} - {}, 页码: {}", startTime, endTime, page);
                    retryCount++;
                    if (retryCount >= maxRetry) {
                        return false;
                    }
                    continue;
                }

                JSONObject rs = responseEntity.getBody();
                int errorCode = rs.getIntValue("code");

                if (errorCode != 0) {
                    log.error("API返回错误: {}, 消息: {}, 时间范围: {} - {}", errorCode, rs.getString("msg"), startTime, endTime);
                    return false;
                }

                JSONObject data = rs.getJSONObject("data");
                if (data == null) {
                    log.info("没有数据返回，时间范围: {} - {}", startTime, endTime);
                    return true;
                }

                JSONArray list = data.getJSONArray("list");
                if (list == null || list.isEmpty()) {
                    log.info("时间范围 {} - {} 的订单数据已全部获取完成", startTime, endTime);
                    return true;
                }

                // 处理当前页数据
                for (int i = 0; i < list.size(); i++) {
                    JSONObject order = list.getJSONObject(i);
                    syncTechProductSpuDetail(order, accessToken);
                }

                // 获取分页信息
                int currentPage = data.getIntValue("page"); // 当前页码
                int pageSize = data.getIntValue("page_size"); // 每页大小
                int total = data.getIntValue("total"); // 总记录数

                log.info("时间范围 {} - {} 第 {} 页，本页 {} 条，总计 {} 条",
                        startTime, endTime, currentPage, list.size(), total);

                // 计算总页数
                int totalPages = (int) Math.ceil((double) total / pageSize);

                // 检查是否还有下一页
                if (currentPage >= totalPages) {
                    log.info("时间范围 {} - {} 的所有分页数据已获取完成，共{}页", startTime, endTime, totalPages);
                    return true;
                }

                // 下一页
                page = currentPage + 1;

                // 重置重试计数
                retryCount = 0;

                // 频率控制
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }

            } catch (Exception e) {
                log.error("处理时间范围 {} - {} 第 {} 页时发生错误: {}", startTime, endTime, page, e.getMessage());
                retryCount++;
                if (retryCount >= maxRetry) {
                    log.error("重试次数超过限制，停止当前时间段查询");
                    return false;
                }

                try {
                    Thread.sleep(1000 * retryCount);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }

        log.error("达到最大页数限制，可能陷入死循环，时间范围: {} - {}", startTime, endTime);
        return false;
    }
    public void syncTechProductSpuDetail(JSONObject duct, String access_token){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject order_info=duct.getJSONObject("order_info");
        String order_id=order_info.getString("order_id");//订单流水号
        String user_id=order_info.getString("user_id");//下单用户ID
        Integer wx_app_type=order_info.getInteger("wx_app_type");//终端类型
        String order_remarks=order_info.getString("order_remarks");//商家备注内容
        Integer order_state=order_info.getInteger("order_state");//订单状态
        Integer goods_buy_num=order_info.getInteger("goods_buy_num");//购买数量
        Integer pay_state=order_info.getInteger("pay_state");//支付状态：0-未支付 1-支付成功 2-支付关闭
        Integer ship_way_choose_type=order_info.getInteger("ship_way_choose_type");//
        String goods_name=order_info.getString("goods_name");
        String created_time=order_info.getString("created_time");
        //先判断商品是否存在
        ProductSpuRespDTO spuRespDTO=productSpuApi.getSpuByName(goods_name);
        if(spuRespDTO==null){
            return;
        }

        log.info("开始处理订单-----------------：{}",order_id);

        if("E2024010218523163629709".equals(order_id)){
            log.info("开始处理订单-----------------：{}",order_id);
        }

        TradeOrderDO createReqVO=new TradeOrderDO();
        if(StringUtils.isNotBlank(created_time)) {
            if (created_time.matches("0000-00-00 00:00:00")) {
                createReqVO.setCancelTime(null);
            }else{
                createReqVO.setCreateTime(LocalDateTime.parse(created_time, formatter));
            }
        }
        createReqVO.setNo(order_id);//先用这着
        createReqVO.setType(0);//默认0：普通订单
        if(ship_way_choose_type!=null){
            if("1".equals(ship_way_choose_type)){
                createReqVO.setDeliveryType(1);//自提
            }else if("2".equals(ship_way_choose_type)){
                createReqVO.setDeliveryType(2);//到店自提
            }else{
                createReqVO.setDeliveryType(1);//自提
            }
        }else{
            createReqVO.setDeliveryType(1);//快递发货
        }
        if(wx_app_type==0){
            //小程序
            createReqVO.setTerminal(10);
        }else if(wx_app_type==1){
           //微信公众号
            createReqVO.setTerminal(11);
        }else if(wx_app_type==2){
            //QQ
            createReqVO.setTerminal(50);
        }else if(wx_app_type==3){
            //支付宝
            createReqVO.setTerminal(20);
        }else if(wx_app_type==4 || wx_app_type==6){
            //安卓app-iOS Android App工具
            createReqVO.setTerminal(31);
        }else if(wx_app_type==5){
            //浏览器/手机号
            createReqVO.setTerminal(20);
        }else{
            //没值就默认未知
            createReqVO.setTerminal(0);
        }

        JSONObject buyer_info=duct.getJSONObject("buyer_info");
        String nickname=buyer_info.getString("nickname");//昵称
        String phone_number=buyer_info.getString("phone_number");//手机号
        String comment=buyer_info.getString("comment");
        MemberUserRespDTO existUser=null;
        if(StringUtils.isNotBlank(user_id)) {
            existUser = memberUserApi.selectByTechUserId(user_id);
        }
        if(existUser!=null){
            createReqVO.setUserId(existUser.getId());
            createReqVO.setUserIp("127.0.0.1");
        }
        createReqVO.setUserRemark(comment);
        if(order_state==0 || order_state==1){
            //待付款//待成交
            createReqVO.setStatus(0);
        }else if(order_state==2){
            //待发货
            createReqVO.setStatus(10);
        }else if(order_state==3){
            //已发货
            createReqVO.setStatus(20);
        }else if(order_state==4){
            //已完成
            createReqVO.setStatus(30);
        }else if(order_state==5){
            //已关闭
            createReqVO.setStatus(40);
        }else{
            createReqVO.setStatus(40);//默认关闭
        }
        createReqVO.setProductCount(goods_buy_num);
       /*PAY_TIMEOUT(10, "超时未支付"),
                AFTER_SALE_CLOSE(20, "退款关闭"),
                MEMBER_CANCEL(30, "买家取消"),
                COMBINATION_CLOSE(40, "拼团关闭");*/
        //createReqVO.setCancelType(10);//默认付款完成
        createReqVO.setRemark(order_remarks);
        createReqVO.setCommentStatus(false);//默认未评价
        String pay_state_time=order_info.getString("pay_state_time");//支付时间
        if(pay_state==0){
            //未支付
            createReqVO.setPayStatus(false);
            if(StringUtils.isNotBlank(pay_state_time)) {
                if (pay_state_time.matches("0000-00-00 00:00:00")) {
                    createReqVO.setCancelTime(null);
                }else{
                    createReqVO.setCancelTime(LocalDateTime.parse(pay_state_time, formatter));
                }
            }
        }else if(pay_state==1){
            //支付成功
            createReqVO.setPayStatus(true);
            if(StringUtils.isNotBlank(pay_state_time)) {
                if (pay_state_time.matches("0000-00-00 00:00:00")) {
                    createReqVO.setPayTime(null);
                }else{
                    createReqVO.setPayTime(LocalDateTime.parse(pay_state_time, formatter));
                }
            }
        }else{
            //支付关闭
            createReqVO.setPayStatus(false);
            if(StringUtils.isNotBlank(pay_state_time)) {
                if (pay_state_time.matches("0000-00-00 00:00:00")) {
                    createReqVO.setCancelTime(null);
                }else{
                    createReqVO.setCancelTime(LocalDateTime.parse(pay_state_time, formatter));
                }
            }
        }
        Integer pay_type=order_info.getInteger("pay_type");//支付渠道，-1-无(0元订单) 1-线上微信 2-线上支付宝 3-线下支付 4-百度收银台 8-虚拟币 9-支付宝花
        //其他的先不算
        if(pay_type==1){
            //线上微信
            createReqVO.setPayChannelCode("wx_app");
        }else if(pay_type==2){
            //线上支付宝
            createReqVO.setPayChannelCode("alipay_app");
        }else{
            createReqVO.setPayChannelCode("unknown");
        }
        //`pay_order_id` bigint DEFAULT NULL COMMENT '支付订单编号',--待定关联pay_order
        Integer settle_state=order_info.getInteger("settle_state");//结算状态 0-待结算 1-结算中 2-结算完成
          if(settle_state==2){
              String settle_state_time=order_info.getString("settle_state_time");//结算状态变化时间，例0000-00-00 00:00:00
              if(StringUtils.isNotBlank(settle_state_time)) {
                  if (settle_state_time.matches("0000-00-00 00:00:00")) {
                      createReqVO.setFinishTime(null);
                  }else{
                      createReqVO.setFinishTime(LocalDateTime.parse(settle_state_time, formatter));
                  }
              }
          }
        Integer refund_fee=order_info.getInteger("refund_fee");//已退款金额（分）
        Integer aftersale_show_state=order_info.getInteger("aftersale_show_state");//售后状态 0-无 1-售后中 2-部分退款 3-全额退款
        /*Integer actual_fee=order_info.getInteger("actual_fee");//订单实付总金额（分）
        Integer goods_original_total_price=order_info.getInteger("goods_original_total_price");//商品总金额（分）
        Integer discount_amount=order_info.getInteger("discount_amount");//活动折扣总金额（分）
        Integer deduct_amount=order_info.getInteger("deduct_amount");//资产抵扣总金额（分）
        Integer freight_original_price=order_info.getInteger("freight_original_price");//原始运费（分）
        Integer freight_actual_price=order_info.getInteger("freight_actual_price");//实际运费（分）
        Integer modified_amount=order_info.getInteger("modified_amount");//修改金额(优惠减免)（分）
        Integer refund_fee=order_info.getInteger("refund_fee");//已退款金额（分）
        createReqVO.setTotalPrice(goods_original_total_price);
        createReqVO.setDiscountPrice(discount_amount);
        createReqVO.setDeliveryPrice(freight_actual_price);
        createReqVO.setAdjustPrice(modified_amount);
        createReqVO.setPayPrice(actual_fee);*/

        //商品价格
        JSONObject price_info=duct.getJSONObject("price_info");
        Integer total_price=price_info.getInteger("total_price");//商品原价总价（分）
        Integer freight_price=price_info.getInteger("freight_price");//运费（分）
        Integer total_modified_amount=price_info.getInteger("total_modified_amount");//总改价金额（分）
        Integer goods_modified_amount=price_info.getInteger("goods_modified_amount");//商品改价金额（分）
        Integer freight_modified_price=price_info.getInteger("freight_modified_price");//运费改价金额（分）
        Integer origin_price=price_info.getInteger("origin_price");//订单原价（分）
        Integer actual_price=price_info.getInteger("actual_price");//订单实收金额（分）
        createReqVO.setTotalPrice(total_price);
        createReqVO.setDiscountPrice(goods_modified_amount);
        createReqVO.setDeliveryPrice(freight_price);
        createReqVO.setAdjustPrice(origin_price);
        createReqVO.setPayPrice(actual_price);
        createReqVO.setRefundPrice(refund_fee);//退款金额
        if(aftersale_show_state==1){
            //售后中
            createReqVO.setRefundStatus(10);
        }else if(aftersale_show_state==2 || aftersale_show_state==3){
            //已退款
            createReqVO.setRefundStatus(20);
        }
        //物流状态
        JSONObject ship_info=duct.getJSONObject("ship_info");
        String confirm_time=ship_info.getString("confirm_time");//确认收货时间
        if(StringUtils.isNotBlank(confirm_time)) {
            if (confirm_time.matches("0000-00-00 00:00:00")) {
                createReqVO.setReceiveTime(null);
            }else{
                createReqVO.setReceiveTime(LocalDateTime.parse(confirm_time, formatter));
            }
        }
        String ship_time=ship_info.getString("ship_time");//发货时间
        if(StringUtils.isNotBlank(ship_time)) {
            if (ship_time.matches("0000-00-00 00:00:00")) {
                createReqVO.setDeliveryTime(null);
            }else{
                createReqVO.setDeliveryTime(LocalDateTime.parse(ship_time, formatter));
            }
        }
        String express_id=ship_info.getString("express_id");//运单号
        String receiver=ship_info.getString("receiver");//收货人
        String phone=ship_info.getString("phone");//联系电话
        String detail=ship_info.getString("detail");//详细地址
        createReqVO.setLogisticsNo(express_id);
        createReqVO.setReceiverName(receiver);
        createReqVO.setReceiverMobile(phone);
        createReqVO.setReceiverDetailAddress(detail);
        TradeOrderDO tradeOrderDO=tradeOrderMapper.selectByNo(order_id);
        if(tradeOrderDO!=null){
            createReqVO.setId(tradeOrderDO.getId());
            tradeOrderMapper.updateById(createReqVO);
        }else {
            tradeOrderMapper.insert(createReqVO);
        }
        //---------------交易订单明细表---------------------
        List<TradeOrderItemDO> orderItems=new ArrayList<>();
        List<TradeOrderItemDO> updateOrderItems=new ArrayList<>();
        JSONArray good_list=duct.getJSONArray("good_list");
        for(int i=0;i<good_list.size();i++){
            JSONObject good=good_list.getJSONObject(i);
            String sku_goods_name=good.getString("goods_name");
            String resource_id=good.getString("resource_id");
            String sku_id=good.getString("sku_id");
            String goods_image=good.getString("goods_image");
            Integer num=good.getInteger("buy_num");
            Integer unit_price=good.getInteger("unit_price");
            Integer sku_total_price=good.getInteger("total_price");
            TradeOrderItemDO orderItem=new TradeOrderItemDO();
            orderItem.setOrderId(createReqVO.getId());
            orderItem.setUserId(createReqVO.getUserId());
            orderItem.setSpuName(sku_goods_name);
            orderItem.setPicUrl(goods_image);
            orderItem.setCount(num);
            orderItem.setAfterSaleStatus(createReqVO.getRefundStatus());
            orderItem.setCommentStatus(false);
            orderItem.setPrice(unit_price);
            orderItem.setDeliveryPrice(freight_price);
            orderItem.setDiscountPrice(total_price);
            orderItem.setPayPrice(sku_total_price);
            orderItem.setOrderSkuId(sku_id);//小鹅通的sku
            //优惠卷
            JSONObject discounts_info=good.getJSONObject("discounts_info");
            Integer discount_amount_total=discounts_info.getInteger("discount_amount_total");
            orderItem.setCouponPrice(discount_amount_total);//优惠卷总金额
            //查询spuId
            ProductSpuRespDTO productSpuRespDTO=productSpuApi.getResourceId(resource_id);
            if (productSpuRespDTO!=null) {
                orderItem.setSpuId(productSpuRespDTO.getId());
            }else{
                ProductSpuRespDTO spuResp=productSpuApi.getSpuByName(sku_goods_name);
                if(spuResp!=null){
                    orderItem.setSpuId(spuResp.getId());
                }
            }
            if(orderItem.getSpuId()==null){
                log.info("商品spu不存在："+resource_id);
                return;
            }
            //查询skuId
            ProductSkuRespDTO productSkuRespDTO=productSkuApi.getOrderSkuId(sku_id);
            if(productSkuRespDTO!=null){
                orderItem.setSkuId(productSkuRespDTO.getId());
                List<ProductPropertyValueDetailRespDTO> properties=productSkuRespDTO.getProperties();
                List<TradeOrderItemDO.Property> proper1=new ArrayList<>();
                proper1.addAll(properties.stream()
                        .map(productProperty -> {
                            TradeOrderItemDO.Property tradeProperty = new TradeOrderItemDO.Property();
                            tradeProperty.setPropertyId(productProperty.getPropertyId());
                            tradeProperty.setPropertyName(productProperty.getPropertyName());
                            tradeProperty.setValueId(productProperty.getValueId());
                            tradeProperty.setValueName(productProperty.getValueName());
                            return tradeProperty;
                        })
                        .collect(Collectors.toList()));
                orderItem.setProperties(proper1);
            }else{
                log.info("商品sku不存在："+sku_id);
                return;
            }
            TradeOrderItemDO tradeOrderItemDO=tradeOrderItemMapper.selectOrderSkuId(createReqVO.getId(),productSkuRespDTO.getId(),sku_id);
            if(tradeOrderItemDO!=null){
                orderItem.setId(tradeOrderItemDO.getId());
                updateOrderItems.add(orderItem);
            }else {
                orderItems.add(orderItem);
            }
        }
        if(CollectionUtils.isNotEmpty(orderItems)) {
            tradeOrderItemMapper.insertBatch(orderItems);
        }
        if(CollectionUtils.isNotEmpty(updateOrderItems)) {
            tradeOrderItemMapper.updateBatch(updateOrderItems);
        }
        log.info("订单同步成功："+order_id);
    }

    /**
     *
     */
    @Override
    public void syncTechCategory() {
    }

    @Override
    public void syncTechAfterOrder() {
        String access_token = productSyncApi.getTechAccess_token();
        //查询小鹅通商品
        String url = "https://api.xiaoe-tech.com/xe.ecommerce.after_sale.list/1.0.0";
        //首次传空
        Integer page =1;
        while (true) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("page", page);
                jsonObject.put("page_size", 50);
                jsonObject.put("date_type", "created_at");
                jsonObject.put("created_at", "2023-12-01 || 2028-12-01");//直接默认
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
                            page = page+1;
                            JSONArray list = data.getJSONArray("list");
                            if (list.isEmpty()) {
                                // 如果列表为空，说明没有更多数据，结束循环
                                break;
                            }
                            for (int i = 0; i < list.size(); i++) {
                                JSONObject duct = list.getJSONObject(i);
                                syncTechAfterOrderDetail(duct, access_token);
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
    public void syncTechAfterOrderDetail(JSONObject duct, String access_token) {
        String aftersale_id = duct.getString("aftersale_id");//售后单号
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aftersale_id", aftersale_id);
        jsonObject.put("access_token", access_token);
        String json = JSON.toJSONString(jsonObject);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity("https://api.xiaoe-tech.com/xe.ecommerce.after_sale.detail/1.0.0", entity, JSONObject.class);
        if (responseEntity != null) {
            String jsn = JSONObject.toJSONString(responseEntity.getBody());
            JSONObject rs = JSONObject.parseObject(jsn);
            int errorCode = (int) rs.get("code");
            if (errorCode == 0) {
                JSONObject data = rs.getJSONObject("data");
                if (data != null) {
                   Integer state = data.getInteger("state");//售后状态售后状态 [0:未受理,1:用户取消,2:商家受理中,3:商家逾期未处理,4:商家拒绝退款,5:商家拒绝退货退款,6:待买家退货,7:退货退款关闭,8:待商家收货,11:商家退款中,12:商家逾期未退款,13:退款完成,14:退货退款完成,15:换货完成,16:待商家发货,17:待用户确认收货,18:商家拒绝换货,19:商家已收到货
                   String sale_type = data.getString("sale_type");//售后方式：1-仅退款 2-退款退货 3-换货
                   String user_id = data.getString("user_id");//售后方式：1-仅退款 2-退款退货 3-换货
                   String reason = data.getString("reason");//售后原因
                   String remark = data.getString("remark");//买家售后备注
                   String img_url = data.getString("img_url");//商品图片url
                   String order_id = data.getString("order_id");//售后关联
                   String sku_id = data.getString("sku_id");//商品规格id
                   String goods_name = data.getString("goods_name");//商品名称
                   Integer refund_money = data.getInteger("refund_money");//申请退款金额
                    String return_logistics_company = data.getString("return_logistics_company");//退货承运物流公司
                    String return_logistics_no = data.getString("return_logistics_no");//退货物流单号
                   AfterSaleDO tradeAfterSaleDO=new AfterSaleDO();
                   tradeAfterSaleDO.setNo(aftersale_id);
                    //10	售中退款
                    //20	售后退款
                    tradeAfterSaleDO.setType(10);
                    //售后状态
                    if(state==0 || state==2 || state==3){
                        state=10;//申请售后
                    }else if(state==1 || state==7){
                        state=61;//用户取消
                    }else if(state==4 || state==5){
                        state=62;//商家拒绝
                    }else if(state==6){
                        state=20;//商家拒绝退货
                    }else if(state==8){
                        state=30;//商家待收货
                    }else if(state==11 || state==12){
                        state=40;//等待退款
                    }else if(state==13 || state==14 || state==15){
                        state=50;//退款完成
                    }else if(state==18){
                        state=63;//商家拒收货
                    }else{
                        state=10;//申请售后--默认
                    }
                    tradeAfterSaleDO.setStatus(state);
                    //售后方式
                    //10	仅退款
                    //20	退货退款
                    if("1".equals(sale_type)){
                        tradeAfterSaleDO.setWay(10);//仅退款
                    }else if("2".equals(sale_type)){
                        tradeAfterSaleDO.setWay(20);//默认退货退款
                    }else{
                        tradeAfterSaleDO.setWay(20);//默认退货退款
                    }
                    MemberUserRespDTO memberUserRespDTO=memberUserApi.selectByTechUserId(user_id);
                    if(memberUserRespDTO!=null){
                        tradeAfterSaleDO.setUserId(memberUserRespDTO.getId());
                    }else{
                        return;
                    }
                    tradeAfterSaleDO.setApplyReason(reason);
                    tradeAfterSaleDO.setApplyDescription(remark);
                    TradeOrderDO tradeOrderDO=tradeOrderMapper.selectByNo(order_id);
                    if(tradeOrderDO!=null){
                        tradeAfterSaleDO.setOrderId(tradeOrderDO.getId());
                        tradeAfterSaleDO.setOrderNo(tradeOrderDO.getNo());
                        //查询sku
                        ProductSkuRespDTO productSkuRespDTO=productSkuApi.getOrderSkuId(sku_id);
                        if(productSkuRespDTO!=null) {
                            tradeAfterSaleDO.setSkuId(productSkuRespDTO.getId());
                            List<ProductPropertyValueDetailRespDTO> properties=productSkuRespDTO.getProperties();
                            List<TradeOrderItemDO.Property> proper1=new ArrayList<>();
                            proper1.addAll(properties.stream()
                                    .map(productProperty -> {
                                        TradeOrderItemDO.Property tradeProperty = new TradeOrderItemDO.Property();
                                        tradeProperty.setPropertyId(productProperty.getPropertyId());
                                        tradeProperty.setPropertyName(productProperty.getPropertyName());
                                        tradeProperty.setValueId(productProperty.getValueId());
                                        tradeProperty.setValueName(productProperty.getValueName());
                                        return tradeProperty;
                                    })
                                    .collect(Collectors.toList()));
                            tradeAfterSaleDO.setProperties(proper1);
                            TradeOrderItemDO tradeOrderItemDO = tradeOrderItemMapper.selectOrderSkuId(tradeOrderDO.getId(), productSkuRespDTO.getId(), sku_id);
                            if(tradeOrderItemDO!=null){
                                tradeAfterSaleDO.setOrderItemId(tradeOrderItemDO.getId());
                                tradeAfterSaleDO.setSpuId(tradeOrderItemDO.getSpuId());
                            }
                        }
                    }else{
                        return;
                    }
                    tradeAfterSaleDO.setSpuName(goods_name);
                    JSONArray goods_list = data.getJSONArray("goods_list");
                    Integer count=0;
                    for (int i = 0; i < goods_list.size(); i++){
                        JSONObject goods = goods_list.getJSONObject(i);
                        Integer buy_num = goods.getInteger("buy_num");
                        count+=buy_num;

                    }
                    tradeAfterSaleDO.setPicUrl(img_url);
                    tradeAfterSaleDO.setCount(count);
                    tradeAfterSaleDO.setRefundPrice(refund_money);
                    DeliveryExpressDO deliveryExpressDO=deliveryExpressMapper.getDeliveryExpressName(return_logistics_company);
                    if(deliveryExpressDO!=null){
                        tradeAfterSaleDO.setLogisticsId(deliveryExpressDO.getId());
                    }
                    tradeAfterSaleDO.setLogisticsNo(return_logistics_no);
                    AfterSaleDO afterSaleDO=afterSaleMapper.selectByNo(aftersale_id);
                    if(afterSaleDO==null) {
                        afterSaleMapper.insert(tradeAfterSaleDO);
                    }else{
                        tradeAfterSaleDO.setId(afterSaleDO.getId());
                        afterSaleMapper.updateById(tradeAfterSaleDO);
                    }
                    log.info("售后单同步成功--------{}"+aftersale_id);
                }
            }
        }
    }
}
