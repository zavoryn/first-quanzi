package cn.metast.tuoke.module.member.controller.admin.sync;
import cn.metast.tuoke.module.product.api.spu.ProductSyncApi;
import cn.metast.tuoke.module.system.api.social.SocialClientApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
/**
 * 同步用户 Service 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
@Slf4j
public class MemberUserSyncServiceImpl implements MemberUserSyncService {
    @Resource
    private SocialClientApi socialClientApi;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ProductSyncApi productSyncApi;
    @Override
    public void syncTechMemberUser() {
        String access_token = productSyncApi.getTechAccess_token();
        //查询微信小店商品
        String url = "https://api.xiaoe-tech.com/xe.user.batch.get/2.0.0";
        //首次传空
        JSONObject object = null;
        while (true) {
            try {
                JSONObject jsonObject = new JSONObject();
                if(object!=null) {
                    jsonObject.put("es_skip", object);
                }else{
                    jsonObject.put("es_skip", "");
                }
                jsonObject.put("page_size", 50);
                jsonObject.put("from", "-1");
                jsonObject.put("user_type", "0");
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
                            JSONArray list = data.getJSONArray("list");
                            if (list.isEmpty()) {
                                // 如果列表为空，说明没有更多数据，结束循环
                                break;
                            }
                            for (int i = 0; i < list.size(); i++) {
                                JSONObject duct = list.getJSONObject(i);
                                syncTechMemberUserDetail(duct, access_token);
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
            }
        }
        return;
    }
    public void syncTechMemberUserDetail(JSONObject duct, String access_token){

    }
}
