package cn.metast.tuoke.module.product.api.spu;
import cn.metast.tuoke.module.system.api.social.SocialClientApi;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * token获取
 *
 * @author metast.cn
 */
@Service
@Validated
@Slf4j
public class ProductSyncApiImpl implements ProductSyncApi {
    @Resource
    private SocialClientApi socialClientApi;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
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
        String redisKey = app_id+"_"+client_id+"_"+secret_key+"key";
        String token = stringRedisTemplate.opsForValue().get(redisKey);
        log.error("redisKey{}---{}",redisKey, token);
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

}
