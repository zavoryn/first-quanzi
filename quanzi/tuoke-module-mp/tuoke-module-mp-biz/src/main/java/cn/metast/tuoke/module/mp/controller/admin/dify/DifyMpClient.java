package cn.metast.tuoke.module.mp.controller.admin.dify;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class DifyMpClient {
//    @Value("${dify.workUrl}")
//    private String workUrl;
//    @Value("${dify.workKey}")
//    private String workKey;

    public DifyMpClient() {

    }


    public OkHttpClient getDeaultClient() {
        return ClientContainer.getInstance().getClient();
    }

    protected okhttp3.MediaType getJsonMediaType() {
        return okhttp3.MediaType.parse("application/json");
    }

//    protected JSONObject sendRequest(OkHttpClient client, String method, String url, JSONObject paramObject) {
//        if (client == null) {
//            client = getDeaultClient();
//        }
//        if (StringUtils.isEmpty(method)) {
//            method = "POST";
//        }
//        if (paramObject == null) {
//            paramObject = new JSONObject();
//        }
//        if (url == null) {
//            url = "";
//        }
//
//        JSONObject resultObject = null;
//
//        RequestBody body = RequestBody.create(paramObject.toString(), getJsonMediaType());
//        Request request = new Request.Builder().url(workUrl + url).method(method, body)
//                .addHeader("Authorization", "Bearer "+ workKey)
//                .addHeader("Content-Type", "application/json").build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            if (response != null && response.code() == HttpStatus.HTTP_OK) {
//                resultObject = JSONObject.parseObject(response.body().string());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//        }
//
//        return resultObject;
//    }


    protected JSONObject sendRequest(OkHttpClient client, String method, String url, String apiKey, JSONObject paramObject) {
        if (client == null) {
            client = getDeaultClient();
        }
        if (StringUtils.isEmpty(method)) {
            method = "POST";
        }
        if (paramObject == null) {
            paramObject = new JSONObject();
        }

        JSONObject resultObject = new JSONObject();

        RequestBody body = RequestBody.create(paramObject.toString(), getJsonMediaType());
        Request request = new Request.Builder().url(url).method(method, body)
                .addHeader("Authorization", "Bearer "+ apiKey)
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

    public JSONObject sendWxRequest(OkHttpClient client, String method, String url, JSONObject paramObject, String appKey) {
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

        JSONObject resultObject = null;

        RequestBody body = RequestBody.create(paramObject.toString(), getJsonMediaType());
        Request request = new Request.Builder().url("https://metax.metast.cn/v1" + url).method(method, body)
                .addHeader("Authorization", "Bearer "+appKey)
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
}
