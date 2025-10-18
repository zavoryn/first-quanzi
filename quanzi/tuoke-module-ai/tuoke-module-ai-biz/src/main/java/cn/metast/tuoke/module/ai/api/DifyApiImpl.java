package cn.metast.tuoke.module.ai.api;

import cn.metast.tuoke.module.ai.util.dify.DifyClient;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 商品分类 API 接口实现类
 *
 * @author owen
 */
@Service
@Validated
public class DifyApiImpl implements DifyApi {

    @Resource
//    @Autowired
    private DifyClient difyClient;

    @Override
    public JSONObject generateWorkByXc(String imgUrl) {
        JSONObject jsonObject = difyClient.generateWorkByXc(imgUrl);
        return jsonObject;
    }

}
