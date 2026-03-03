package cn.metast.tuoke.module.member.controller.admin.jg;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import cn.metast.tuoke.framework.common.exception.ServiceException;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jg")
public class JgController{

    public OkHttpClient getDeaultClient() {
        return JcClientContainer.getInstance().getClient();
    }

    protected MediaType getJsonMediaType() {
        return MediaType.parse("application/json");
    }

    private final String HOST = "https://zjky.cnzgc.com";
    private final String jgApp = "jiantou2025";

    @PostMapping("/searchManList")
    public JSONObject searchManList(@org.springframework.web.bind.annotation.RequestBody(required = false) JgVO vo) {
        if (vo == null) {
            vo = new JgVO();
            vo.setPage(1);
            vo.setPageLimit(10);
        }
        if(vo.getPageLimit() > 10000){
            throw new ServiceException(500,"参数pageLimit不能大于10000");
        }
        JSONObject paramObject = JSONObject.parseObject(JSONObject.toJSONString(vo));
        return sendRequest(getDeaultClient(), "POST", "/magic/api/jiantou/searchManList", paramObject);
    }
    @PostMapping("/searchOrderList")
    public JSONObject searchOrderList(@org.springframework.web.bind.annotation.RequestBody(required = false) JgVO vo) {
        if (vo == null) {
            vo = new JgVO();
            vo.setPage(1);
            vo.setPageLimit(10);
        }
        if(vo.getPageLimit() > 10000){
            throw new ServiceException(500,"参数pageLimit不能大于10000");
        }
        JSONObject paramObject = JSONObject.parseObject(JSONObject.toJSONString(vo));
        return sendRequest(getDeaultClient(), "POST", "/magic/api/jiantou/searchOrderList", paramObject);
    }


    public JSONObject sendRequest(OkHttpClient client, String method, String url, JSONObject paramObject) {
        if (client == null) {
            client = getDeaultClient();
        }
        if (StringUtils.isEmpty(method)) {
            method = "POST";
        }
        if (paramObject == null) {
            paramObject = new JSONObject();
        }
        if (url == null) {
            url = "";
        }

        //setParam
        if(ObjectUtil.isNull(paramObject)){
            paramObject = new JSONObject();
        }
        paramObject.put("jgApp", jgApp);
        paramObject.put("token", getMd5Token());

        JSONObject resultObject = null;

        RequestBody body = RequestBody.create(paramObject.toString(), getJsonMediaType());
        Request request = new Request.Builder().url(HOST + url).method(method, body)
                .addHeader("Content-Type", "application/json").build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response != null && response.code() == HttpStatus.HTTP_OK) {
                resultObject = JSONObject.parseObject(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return resultObject;
    }

    private String getMd5Token() {
//        所有接口都需要在body里面传入token参数，token参数加密方式为当天日期加jgApp值进行md5加密，jgApp值请联系管理员获取
//        例:jgApp: a_fjnpjgpt
//        加密前字符串: 2025-04-24a_fjnpjgpt
//        加密后字符串: b0f0f1a9be19ec8e7f47620089d87265
        // 获取当前日期，格式为 yyyy-MM-dd
        String date = java.time.LocalDate.now().toString();
        // 拼接日期和 jgApp
        String rawToken = date + jgApp;
        // 使用 MD5 加密
        return cn.hutool.crypto.digest.DigestUtil.md5Hex(rawToken);
    }
}
