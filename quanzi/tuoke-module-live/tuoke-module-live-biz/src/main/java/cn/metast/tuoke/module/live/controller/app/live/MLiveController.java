package cn.metast.tuoke.module.live.controller.app.live;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.framework.tenant.core.context.TenantContextHolder;
import cn.metast.tuoke.module.live.service.live.LiveService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import okhttp3.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/live")
public class MLiveController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private LiveService liveService;
    @Resource
    private MemberUserApi memberUserApi;

    public static final String visitorUserToken = "3c71f46eeaba42c8b16b1ea45678f3";

    public static final String reqUrl="https://fitnesslive.metast.cn";
    //public static final String reqUrl="http://localhost:1801";
    //直播列表
    @GetMapping("/getHomeDataList")
    public JSONObject getHomeDataList() {
        JSONObject json=appUserId();
        if(json==null){
            return null;
        }
        //判断token是否失效
        JSONObject jsonObject=appUserTokenId(json);
        Integer userId=jsonObject.getInteger("userid");
        String token=jsonObject.getString("userToken");
            int pageIndex=0;
            int pageSize=20;
            int liveType=1;
            long hotSortId=-1L;
            long channelId=-1L;
            int sex=-1;
            String tabIds="";
            String address="";
            int isRecommend=-1;
            int liveFunction=-1;
            int isLive=-1;
            long twoClassifyId=-1L;
            String url=reqUrl + "/api/home/getHomeDataList?_uid_="+userId+"&_token_="+token+
                    "&pageIndex="+pageIndex+"&pageSize="+pageSize+"&liveType="+liveType+"&hotSortId="+hotSortId+"&channelId="+channelId+
                    "&sex="+sex+"&tabIds="+tabIds+"&address="+address+"&isRecommend="+isRecommend+"&liveFunction="+liveFunction+
                    "&isLive="+isLive+"&twoClassifyId="+twoClassifyId;
            RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("_uid_", String.valueOf(userId))
                    .addHeader("_token_",visitorUserToken)
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response != null && response.code() == HttpStatus.HTTP_OK) {
                    String resultHtml = response.body().string();
                    JSONObject retData = JSON.parseObject(resultHtml);
                    if(retData.getInteger("code")==1){
                        retData.put("userToken", token);
                        retData.put("userId", userId);
                        return retData;
                    }
                    log.info("获取数据成功：{}", retData);
                }
            } catch (Exception e) {
                // 使用日志框架记录异常，而非直接打印堆栈
                log.error("HTTP request failed: {}", e.getMessage(), e);
            } finally {
                // 释放资源
                client.connectionPool().evictAll();
                client.dispatcher().executorService().shutdown();
            }
        return null;
    }
    /**
     * 动态列表
     * @return
     */
    @PermitAll
    @GetMapping("/getDynamicList")
    public JSONObject getDynamicList(HttpServletRequest req) {
        JSONObject json=appUserId();
        if(json==null){
            return null;
        }
        //判断token是否失效
        JSONObject jsonObject=appUserTokenId(json);
        Integer userId=jsonObject.getInteger("userid");
        String token=jsonObject.getString("userToken");
            int pageIndex=0;
            int pageSize=20;
            int type=0;
            if(req.getParameter("pageSize")!=null){
                pageSize = Integer.parseInt(req.getParameter("pageSize"));
            }
            if(req.getParameter("page")!=null){
                pageIndex = Integer.parseInt(req.getParameter("page"));
            }
            if(req.getParameter("type")!=null){
                type = Integer.parseInt(req.getParameter("type"));
            }
            long hotId=0L;
            long touid=0L;
            String url=reqUrl + "/api/dynamic/getDynamicList?_uid_="+userId+"&_token_="+token+
                    "&page="+pageIndex+"&pageSize="+pageSize+"&type="+type+"&hotId="+hotId+"&touid="+touid;
            RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response != null && response.code() == HttpStatus.HTTP_OK) {
                    String resultHtml = response.body().string();
                    JSONObject retData = JSON.parseObject(resultHtml);
                    if(retData.getInteger("code")==1){
                        retData.put("userToken", token);
                        retData.put("userId", userId);
                        return retData;
                    }
                    log.info("获取数据成功：{}", retData);
                    return null;
                }
            } catch (Exception e) {
                // 使用日志框架记录异常，而非直接打印堆栈
                log.error("HTTP request failed: {}", e.getMessage(), e);
            } finally {
                // 释放资源
                client.connectionPool().evictAll();
                client.dispatcher().executorService().shutdown();
            }
        return null;
    }
    /**
     * 添加评论
     * @return
     */
    @GetMapping("/addComment")
    public JSONObject addComment(HttpServletRequest req) {
        String objId = req.getParameter("objId");
        String content = req.getParameter("content");
        String commentType = req.getParameter("commentType");
        JSONObject json=appUserId();
        if(json==null){
            return null;
        }
        //判断token是否失效
        JSONObject jsonObject=appUserTokenId(json);
        Integer userId=jsonObject.getInteger("userid");
        String token=jsonObject.getString("userToken");
            String url=reqUrl + "/api/dynamic/addComment?_uid_="+userId+"&_token_="+token+
                    "&objId="+Long.parseLong(objId)+"&content="+content+"&commentType="+Integer.parseInt(commentType);
            RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response != null && response.code() == HttpStatus.HTTP_OK) {
                    String resultHtml = response.body().string();
                    JSONObject retData = JSON.parseObject(resultHtml);
                    if(retData.getInteger("code")==1){
                        retData.put("userToken", token);
                        retData.put("userId", userId);
                        return retData;
                    }
                    log.info("获取数据成功：{}", retData);
                }
            } catch (Exception e) {
                // 使用日志框架记录异常，而非直接打印堆栈
                log.error("HTTP request failed: {}", e.getMessage(), e);
            } finally {
                // 释放资源
                client.connectionPool().evictAll();
                client.dispatcher().executorService().shutdown();
            }
        return null;
    }

    /**
     * 获得直播token
     * @return
     */
    @GetMapping("/getRtcToken")
    public JSONObject getRtcToken(HttpServletRequest req) {
        String channelName = req.getParameter("channelName");
        String uid = req.getParameter("uid");
        JSONObject json=appUserId();
        if(json==null){
            return null;
        }
        //判断token是否失效
        JSONObject jsonObject=appUserTokenId(json);
        Integer userId=jsonObject.getInteger("userid");
        String token=jsonObject.getString("userToken");
        String url=reqUrl + "/api/live/getRtcToken?_uid_="+userId+"&_token_="+token+
                "&uid="+uid+"&channelName="+channelName;
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.code() == HttpStatus.HTTP_OK) {
                String resultHtml = response.body().string();
                JSONObject retData = JSON.parseObject(resultHtml);
                if(retData.getInteger("code")==1){
                    retData.put("uid", uid);
                    retData.put("msg", "成功");
                    retData.put("userToken", token);
                    retData.put("userId", userId);
                    return retData;
                }
                log.info("获取数据成功：{}", retData);
            }
        } catch (Exception e) {
            // 使用日志框架记录异常，而非直接打印堆栈
            log.error("HTTP request failed: {}", e.getMessage(), e);
        } finally {
            // 释放资源
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
        }
        return null;
    }


    /**
     * 点赞
     * @return
     */
    @GetMapping("/dynamicZan")
    public JSONObject dynamicZan(HttpServletRequest req) {
        String dynamicId = req.getParameter("dynamicId");
        String uid = req.getParameter("uid");
        JSONObject json=appUserId();
        if(json==null){
            return null;
        }
        //判断token是否失效
        JSONObject jsonObject=appUserTokenId(json);
        Integer userId=jsonObject.getInteger("userid");
        String token=jsonObject.getString("userToken");
            String url=reqUrl + "/api/dynamic/dynamicZan?_uid_="+userId+"&_token_="+token+
                    "&dynamicId="+Long.parseLong(dynamicId);
            RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response != null && response.code() == HttpStatus.HTTP_OK) {
                    String resultHtml = response.body().string();
                    JSONObject retData = JSON.parseObject(resultHtml);
                    if(retData.getInteger("code")==1){
                        retData.put("userToken", token);
                        retData.put("userId", userId);
                        return retData;
                    }
                    log.info("获取数据成功：{}", retData);
                }
            } catch (Exception e) {
                // 使用日志框架记录异常，而非直接打印堆栈
                log.error("HTTP request failed: {}", e.getMessage(), e);
            } finally {
                // 释放资源
                client.connectionPool().evictAll();
                client.dispatcher().executorService().shutdown();
            }
        return null;
    }
    /**
     * 详情
     * @return
     */
    @GetMapping("/getDynamicInfo")
    public JSONObject getDynamicInfo(HttpServletRequest req) {
        String dynamicId = req.getParameter("dynamicId");
        String type = req.getParameter("type");
        String commentId = req.getParameter("commentId");
        JSONObject json=appUserId();
        if(json==null){
            return null;
        }
        //判断token是否失效
        JSONObject jsonObject=appUserTokenId(json);
        Integer userId=jsonObject.getInteger("userid");
        String token=jsonObject.getString("userToken");
            String url=reqUrl + "/api/dynamic/getDynamicInfo?_uid_="+userId+"&_token_="+token+
                    "&dynamicId="+Long.parseLong(dynamicId)+"&type="+Integer.parseInt(type)+"&commentId="+Integer.parseInt(commentId);
            RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response != null && response.code() == HttpStatus.HTTP_OK) {
                    String resultHtml = response.body().string();
                    JSONObject retData = JSON.parseObject(resultHtml);
                    if(retData.getInteger("code")==1){
                        retData.put("userToken", token);
                        retData.put("userId", userId);
                        return retData;
                    }
                    log.info("获取数据成功：{}", retData);
                }
            } catch (Exception e) {
                // 使用日志框架记录异常，而非直接打印堆栈
                log.error("HTTP request failed: {}", e.getMessage(), e);
            } finally {
                // 释放资源
                client.connectionPool().evictAll();
                client.dispatcher().executorService().shutdown();
            }
        return null;
    }

    /**
     * 动态评论列表
     * @return
     */
    @GetMapping("/getCommentBasicInfo")
    public JSONObject getCommentBasicInfo(HttpServletRequest req) {
        String dynamicId = req.getParameter("dynamicId");
        int pageIndex=0;
        int pageSize=20;
        if(req.getParameter("pageSize")!=null){
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        }
        if(req.getParameter("page")!=null){
            pageIndex = Integer.parseInt(req.getParameter("page"));
        }
        JSONObject json=appUserId();
        if(json==null){
            return null;
        }
        //判断token是否失效
        JSONObject jsonObject=appUserTokenId(json);
        Integer userId=jsonObject.getInteger("userid");
        String token=jsonObject.getString("userToken");
        String url=reqUrl + "/api/dynamic/getCommentBasicInfo?_uid_="+userId+"&_token_="+token+
                "&dynamicId="+Long.parseLong(dynamicId)+"&page="+pageIndex+"&pageSize="+pageSize;
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.code() == HttpStatus.HTTP_OK) {
                String resultHtml = response.body().string();
                JSONObject retData = JSON.parseObject(resultHtml);
                if(retData.getInteger("code")==1){
                    retData.put("userToken", token);
                    retData.put("userId", userId);
                    return retData;
                }
                log.info("获取数据成功：{}", retData);
            }
        } catch (Exception e) {
            // 使用日志框架记录异常，而非直接打印堆栈
            log.error("HTTP request failed: {}", e.getMessage(), e);
        } finally {
            // 释放资源
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
        }
        return null;
    }
    public JSONObject appUserId() {
        Long userId=SecurityFrameworkUtils.getLoginUserId()!=null?SecurityFrameworkUtils.getLoginUserId():303L;
        MemberUserRespDTO memberUserRespDTO=memberUserApi.getUser(userId);
        if(memberUserRespDTO!=null){
            String redisKey=memberUserRespDTO.getMobile()+":live:appUserIds";
            String tokenInfo = stringRedisTemplate.opsForValue().get(redisKey);
            if(StringUtils.isNotEmpty(tokenInfo)) {
                JSONObject obj = JSONObject.parseObject(tokenInfo);
                return obj;
            }

            /*Map<String, Object> mp=liveService.appUserId(memberUserRespDTO.getMobile());
            if(mp!=null){
                Long userid= (Long) mp.get("userid");
                JSONObject token=new JSONObject();
                token.put("userid",userid);
                stringRedisTemplate.opsForValue().set(redisKey,token.toJSONString());
                return userid;
            }*/
            //用户不存在-需要注册
            return syncZbUserById(memberUserRespDTO.getMobile());
        }
        return null;
    }
    //token 失效
    public JSONObject appUserTokenId(JSONObject jsonObject) {
        Integer userId=jsonObject.getInteger("userid");
        String token=jsonObject.getString("userToken");
        String url=reqUrl + "/api/user/info?_uid_="+userId+"&_token_="+token;
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.code() == HttpStatus.HTTP_OK) {
                String resultHtml = response.body().string();
                JSONObject retData = JSON.parseObject(resultHtml);
                if(retData.getInteger("code")==1){
                    retData.put("msg", "成功");
                    retData.put("userToken", token);
                    retData.put("userid", userId);
                    return retData;
                }else if(retData.getInteger("code")==44003){
                    Long uId=SecurityFrameworkUtils.getLoginUserId();
                    MemberUserRespDTO memberUserRespDTO=memberUserApi.getUser(uId);
                    if(memberUserRespDTO!=null){
                        //用户不存在-需要注册
                        return syncZbUserById(memberUserRespDTO.getMobile());
                    }
                }
                log.info("获取数据成功：{}", retData);
            }
        } catch (Exception e) {
            // 使用日志框架记录异常，而非直接打印堆栈
            log.error("HTTP request failed: {}", e.getMessage(), e);
        } finally {
            // 释放资源
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
        }
        return null;
    }

    public JSONObject syncZbUserById(String mobile){
        JSONObject authInfo = getZbAuthCode(mobile);
        if(ObjectUtil.isEmpty(authInfo) || authInfo.getInteger("code") != 1){
            return null;
        }
        JSONObject paramObject = new  JSONObject();
        paramObject.put("code", authInfo.getString("msg"));
        paramObject.put("phone", mobile);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)  // 设置连接超时: 10秒
                .writeTimeout(60, TimeUnit.SECONDS)  // 设置写入超时: 10秒
                .readTimeout(60, TimeUnit.SECONDS)  // 设置读取超时: 30秒
                .build();

        JSONObject resultObject = null;
        RequestBody body = RequestBody.create(paramObject.toJSONString(), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(reqUrl + "/api/login/lxRegister").method("POST", body)
                .addHeader("Content-Type", "application/json").build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response != null && response.code() == HttpStatus.HTTP_OK) {
                resultObject = JSONObject.parseObject(response.body().string());
                if(resultObject.getInteger("code")==1){
                    JSONObject retObj=resultObject.getJSONObject("retObj");
                    JSONObject userInfo=retObj.getJSONObject("userInfo");
                    String userid=userInfo.getString("userId");
                    String userToken=retObj.getString("user_token");
                    JSONObject token=new JSONObject();
                    token.put("userid",userid);
                    token.put("userToken",userToken);
                    stringRedisTemplate.opsForValue().set(mobile+":live:appUserIds",token.toJSONString());
                    return token;
                }
                log.info("syncZbUserById:{}",resultObject.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
    public JSONObject getZbAuthCode(String mobile){
        JSONObject paramObject = new JSONObject();
        paramObject.put("tenantId", TenantContextHolder.getTenantId());
        paramObject.put("username", mobile);
        RequestBody body = RequestBody.create(paramObject.toJSONString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(reqUrl + "/api/login/get-auth-code")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        return executeHttpRequest(request);
    }
    private JSONObject executeHttpRequest(Request request) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.code() == HttpStatus.HTTP_OK) {
                return JSONObject.parseObject(response.body().string());
            }
        } catch (Exception e) {
            // 使用日志框架记录异常，而非直接打印堆栈
            log.error("HTTP request failed: {}", e.getMessage(), e);
        } finally {
            // 释放资源
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
        }
        return null;
    }
}
