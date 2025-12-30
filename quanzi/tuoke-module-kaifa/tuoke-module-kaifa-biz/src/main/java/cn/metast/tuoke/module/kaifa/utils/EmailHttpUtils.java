package cn.metast.tuoke.module.kaifa.utils;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Slf4j
@Component
public class EmailHttpUtils {

    private String URL_HOME = "/facebook/doOkProxyQuery";
    private String MAIL_HOME = "/mail/sendMail";
    @Autowired
    private Environment environment;
    private static Environment env;
    @PostConstruct
    public void setUrl(){
        //String baseUrl = "http://122.96.142.170:9077";
        String baseUrl = "http://47.114.114.234:9077";
//        String baseUrl = "http://127.0.0.1:9077";
        //本地测试 发布需关闭
        URL_HOME = baseUrl + URL_HOME;
        MAIL_HOME = baseUrl + MAIL_HOME;
    }
    public JSONObject doOkQuery(String method,String condition,String param,Map<String,String> header){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)  // 设置连接超时: 10秒
                .writeTimeout(60, TimeUnit.SECONDS)  // 设置写入超时: 10秒
                .readTimeout(60, TimeUnit.SECONDS)  // 设置读取超时: 30秒
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, StringUtils.isNotEmpty(param) ? param : "");
        try {
            Request.Builder builder = new Request.Builder();
            builder.url(condition)
                    .method(method, "POST".equals(method) ? body : null).addHeader("Accept", "*/*");
//                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
//                    .addHeader("Pragma", "no-cache")
//                    .addHeader("Cache-Control", "no-cache")
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Connection", "keep-alive")
//                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
            if(ObjectUtil.isNotNull(header)){
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            int statusCode = response.code();
            ResponseBody bodyRes = response.body();
            String res = bodyRes.string();
            if(StringUtils.isNotEmpty(res)){
                return JSONObject.parseObject(res);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 转二进制
     * @param urls 远端文件Url
     * @return File
     */
    public static byte[] urlToBytes(String urls) {
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                return baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
