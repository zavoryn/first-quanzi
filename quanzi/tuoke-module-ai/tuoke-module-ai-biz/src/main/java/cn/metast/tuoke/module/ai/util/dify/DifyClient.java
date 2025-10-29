package cn.metast.tuoke.module.ai.util.dify;

import cn.hutool.http.HttpStatus;
import cn.metast.tuoke.module.ai.util.dify.entity.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DifyClient {

    @Value("${dify.apiUrl}")
    private String apiUrl;

    @Value("${dify.apiKeyChat}")
    private String apiKeyChat;
    @Value("${dify.apiKeyXc}")
    private String apiKeyXc;

    private final WebClient webClient;

    public DifyClient(String apiUrl, String apiKeyChat, String apiKeyXc) {
        this.webClient = WebClient.create();
        this.apiUrl = apiUrl;
        this.apiKeyChat = apiKeyChat;
        this.apiKeyXc = apiKeyXc;
    }

    public DifyClient() {
        this.webClient = WebClient.create();
    }

    public Object sendMessageBlocking(DifyRequest request) {
        if ("streaming".equals(request.getResponse_mode())) {
            throw new IllegalArgumentException("you should call sendMessageStreaming method for streaming response");
        }
        return webClient.post()
                .uri(apiUrl + "/chat-messages")
                .header("Authorization", "Bearer " + apiKeyChat)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchangeToMono(
                        clientResponse -> {
                            if (clientResponse.statusCode().is2xxSuccessful()) {
                                return clientResponse.bodyToMono(DifyResponse.class);
                            } else {
                                // Handle non-2xx HTTP status
                                // Here, we read the error body only once
                                return clientResponse.bodyToMono(FailedResponse.class);
                            }
                        }
                ).block();
    }

    public Flux<DifyResponse> sendMessageStreaming(DifyRequest request) {
        return webClient.post()
                .uri(apiUrl + "/chat-messages")
                .header("Authorization", "Bearer " + apiKeyChat)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(DifyResponse.class)
                .doOnNext(response -> log.info("Received response: {}", response));

    }

    public FeedbackResponse sendFeedback(String messageId, FeedbackRequest request) {
        return webClient.post()
                .uri(apiUrl + "/messages/" + messageId + "/feedbacks")
                .header("Authorization", "Bearer " + apiKeyChat)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FeedbackResponse.class)
                .doOnNext(feedbackResponse -> log.info("Received response: {}", feedbackResponse))
                .block();
    }


    public MessagesResponse getMessages(String user, String conversationId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl + "/messages")
                        .queryParam("user", user)
                        .queryParam("conversation_id", conversationId)
                        .build())
                .header("Authorization", "Bearer " + apiKeyChat)
                .retrieve()
                .bodyToMono(MessagesResponse.class)
                .block();
    }

    public CreateConversationResponse createConversationWithName(String name, String user) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("user", user);

        return webClient.post()
                .uri(apiUrl + "/conversations/name")
                .header("Authorization", "Bearer " + apiKeyChat)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(CreateConversationResponse.class)
                .block();
    }

    public CreateConversationResponse deleteConversation(String conversationId, String user) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(apiUrl + "/conversations/{conversationId}")
                        .queryParam("user", user)
                        .build(conversationId))
                .header("Authorization", "Bearer " + apiKeyChat)
                .retrieve()
                .bodyToMono(CreateConversationResponse.class)
                .block();
    }

    @Deprecated
    public AudioToTextResponse audioToText(MultipartFile audioFile) throws IOException {
        String contentType = audioFile.getContentType();
        if (contentType == null || !contentType.startsWith("audio/")) {
            throw new IllegalArgumentException("Unsupported file type");
        }

        ByteArrayResource byteArrayResource = new ByteArrayResource(audioFile.getBytes()) {
            @Override
            public String getFilename() {
                return audioFile.getOriginalFilename();
            }
        };

        return webClient.post()
                .uri(apiUrl + "/audio-to-text")
                .header("Authorization", "Bearer " + apiKeyChat)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromFormData("file", String.valueOf(byteArrayResource)))
                .retrieve()
                .bodyToMono(AudioToTextResponse.class)
                .block();
    }

    @Deprecated
    public ParameterResponse fetchParameters() {
        return webClient.get()
                .uri(apiUrl + "/parameters")
                .header("Authorization", "Bearer " + apiKeyChat)
                .retrieve()
                .bodyToMono(ParameterResponse.class)
                .block();
    }


    public JSONObject generateWorkByXc(String imgUrl) {

        if (StringUtils.isEmpty(imgUrl)){
            return new JSONObject();
        }

        OkHttpClient client = getDeaultClient();

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("user", "abc-123");
        bodyObject.put("response_mode", "blocking");

        JSONObject inputs = new JSONObject();

        JSONObject inputs_pic = new JSONObject();
        inputs_pic.put("type", "image");
        inputs_pic.put("transfer_method", "remote_url");
        inputs_pic.put("remote_url", imgUrl);

        inputs.put("pic", inputs_pic);

        bodyObject.put("inputs", inputs);

        JSONObject resultObject = sendRequest(client, "POST", "/workflows/run", apiKeyXc, bodyObject);
        if(resultObject == null){
            resultObject = new JSONObject();
        }
        log.info("---------- generateWorkByXc-Result:" + resultObject.toJSONString());
        JSONObject data = resultObject.getJSONObject("data");
        if(data != null){
            data = data.getJSONObject("outputs");
        }
        return data;
    }

    // 小红书生成文案
    public JSONObject generateXhs(String apiKey, String basicInstruction, String backgroundDetail, String style) {

        if (StringUtils.isEmpty(apiKey) || StringUtils.isEmpty(basicInstruction)){
            return new JSONObject();
        }

        OkHttpClient client = getDeaultClient();

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("user", "abc-123");
        bodyObject.put("response_mode", "blocking");

        JSONObject inputs = new JSONObject();
        inputs.put("basic_instruction", basicInstruction);
        inputs.put("background_detail", backgroundDetail);
        inputs.put("style", style);

        bodyObject.put("inputs", inputs);

        JSONObject resultObject = sendRequest(client, "POST", "/workflows/run", apiKey, bodyObject);
        if(resultObject == null){
            resultObject = new JSONObject();
        }
        log.info("---------- generateWorkByXc-Result:" + resultObject.toJSONString());
        JSONObject data = resultObject.getJSONObject("data");
        if(data != null){
            data = data.getJSONObject("outputs");
        }
        return data;
    }

    // 翻译
    public JSONObject generateWorkTranslate(String apiKey, String content, String language) {

        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(language)){
            return new JSONObject();
        }

        OkHttpClient client = getDeaultClient();

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("user", "abc-123");
        bodyObject.put("response_mode", "blocking");

        JSONObject inputs = new JSONObject();
        inputs.put("con", content);
        inputs.put("target", language);

        bodyObject.put("inputs", inputs);

        JSONObject resultObject = sendRequest(client, "POST", "/workflows/run", apiKey, bodyObject);
        if(resultObject == null){
            resultObject = new JSONObject();
        }
        log.info("---------- generateWorkByXc-Result:" + resultObject.toJSONString());
        JSONObject data = resultObject.getJSONObject("data");
        if(data != null){
            data = data.getJSONObject("outputs");
        }
        return data;
    }

    /** 文案生成
     *
     * @param apiKey
     * @param content  文案描述  必填
     * @param platform  文案类型  必填  抖音-dy 公众号- 知乎-zhihu 产品文案
     * @param style  文案语气  非必填
     * @param wordCount  文案字数  必填  默认不限
     * @return
     */
    public JSONObject generateCopywriting(String apiKey, String content, String wordCount, String style, String platform) {

        if(StringUtils.isBlank(wordCount)){
            wordCount = "不限";
        }

        OkHttpClient client = getDeaultClient();

        // 基于默认client创建一个新的client，但使用自定义的超时设置
        client = client.newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("user", "abc-123");
        bodyObject.put("response_mode", "blocking");

        JSONObject inputs = new JSONObject();
        inputs.put("type", "text");
        inputs.put("content", content);
        inputs.put("platform", platform);
        inputs.put("size", wordCount);
        inputs.put("style", style);

        bodyObject.put("inputs", inputs);

        // 记录开始时间
        long startTime = System.currentTimeMillis();
        log.info("------------- ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ -----------");
        log.info("---------- generateCopywriting- bodyObject:" + bodyObject.toJSONString());
        log.info("---------- generateCopywriting- START:" + startTime);

        JSONObject resultObject = sendRequest(client, "POST", "/workflows/run", apiKey, bodyObject);

        // 记录结束时间
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000; // 转换为秒
        log.info("---------- generateCopywriting-Result:" + resultObject.toJSONString());
        log.info("---------- generateCopywriting-END: {}, 耗时: {} 秒", endTime, duration);
        log.info("------------- ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ -----------");

        if(resultObject == null){
            resultObject = new JSONObject();
        }

        JSONObject data = resultObject.getJSONObject("data");
        if(data != null){
            data = data.getJSONObject("outputs");
            if(data.get("output") != null){
                data = data.getJSONObject("output");
                if(data.get("output") != null){
                    resultObject = data.getJSONObject("output");
                }
            }
        }

        return resultObject;
    }

    /** 话题生成
     *
     * @param apiKey
     * @param content  文案描述  必填
     * @param style  文案语气  非必填
     * @param wordCount  文案字数  必填  默认不限
     * @return
     */
    public JSONObject generateTopic(String apiKey, String content, String wordCount, String style) {

        if(StringUtils.isBlank(wordCount)){
            wordCount = "不限";
        }

        OkHttpClient client = getDeaultClient();

        // 基于默认client创建一个新的client，但使用自定义的超时设置
        client = client.newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("user", "abc-123");
        bodyObject.put("response_mode", "blocking");

        JSONObject inputs = new JSONObject();
        inputs.put("content", content);
        inputs.put("size", wordCount);
        inputs.put("style", style);

        bodyObject.put("inputs", inputs);

        // 记录开始时间
        long startTime = System.currentTimeMillis();
        log.info("------------- ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ -----------");
        log.info("---------- generateTopic- bodyObject:" + bodyObject.toJSONString());
        log.info("---------- generateTopic- START:" + startTime);

        JSONObject resultObject = sendRequest(client, "POST", "/workflows/run", apiKey, bodyObject);

        // 记录结束时间
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000; // 转换为秒
        log.info("---------- generateTopic-Result:" + resultObject.toJSONString());
        log.info("---------- generateTopic-END: {}, 耗时: {} 秒", endTime, duration);
        log.info("------------- ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ -----------");

        if(resultObject == null){
            resultObject = new JSONObject();
        }

        JSONObject data = resultObject.getJSONObject("data");
        if(data != null){
            resultObject = data.getJSONObject("outputs");
        }

        return resultObject;
    }

    // AI CHAT
    public JSONObject aiChat(String apiKey, String query, String fileUrl, String conversationId) {

        if (StringUtils.isEmpty(apiKey) || StringUtils.isEmpty(query)){
            return new JSONObject();
        }

        OkHttpClient client = getDeaultClient();

        JSONObject bodyObject = new JSONObject();
        bodyObject.put("user", "abc-123");
        bodyObject.put("response_mode", "blocking");
        bodyObject.put("dialogue_count", 2000);

        JSONObject inputs = new JSONObject();
        bodyObject.put("inputs", inputs);

        bodyObject.put("query", query);

        if(StringUtils.isNotBlank(conversationId)){
            bodyObject.put("conversation_id", conversationId);
        }

        if(StringUtils.isNotBlank(fileUrl)){
            JSONArray files = new JSONArray();
            JSONObject fileItem = new JSONObject();
            fileItem.put("type", "image");
            fileItem.put("transfer_method", "remote_url");
            fileItem.put("url", fileUrl);
            files.add(fileItem);
            bodyObject.put("files", files);
        }

        JSONObject resultObject = sendRequest(client, "POST", "/chat-messages", apiKey, bodyObject);
        if(resultObject == null){
            resultObject = new JSONObject();
        }
        log.info("---------- generateWorkByXc-Result:" + resultObject.toJSONString());
        JSONObject data = new JSONObject();
        data.put("conversationId", resultObject.getString("conversation_id"));
        String answer = resultObject.getString("answer");
        if(answer.indexOf(".wav]") != -1){
            answer = answer.substring(0, answer.indexOf(".wav]"));
            answer = answer.substring(0, answer.lastIndexOf("["));
        }
        data.put("content", answer);
        return data;
    }

    protected okhttp3.MediaType getJsonMediaType() {
        return okhttp3.MediaType.parse("application/json");
    }

    protected OkHttpClient getDeaultClient() {
        return ClientContainer.getInstance().getClient();
    }

    protected JSONObject sendRequest(OkHttpClient client, String method, String url, String apiKey, JSONObject paramObject) {
        if (client == null) {
            client = getDeaultClient();
        }
        if (StringUtils.isEmpty(method)) {
            method = "POST";
        }
        if (StringUtils.isEmpty(apiKey)) {
            apiKey = apiKeyChat;
        }
        if (paramObject == null) {
            paramObject = new JSONObject();
        }
        if (url == null) {
            url = "";
        }

        JSONObject resultObject = new JSONObject();

        RequestBody body = RequestBody.create(paramObject.toString(), getJsonMediaType());
        Request request = new Request.Builder().url(apiUrl + url).method(method, body)
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

}
