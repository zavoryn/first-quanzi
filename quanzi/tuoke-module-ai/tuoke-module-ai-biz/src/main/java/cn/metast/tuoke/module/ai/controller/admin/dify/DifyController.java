package cn.metast.tuoke.module.ai.controller.admin.dify;

import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.module.ai.controller.admin.dify.vo.AiPresetsRespVO;
import cn.metast.tuoke.module.ai.dal.dataobject.dify.AiApiKeyDifyDO;
import cn.metast.tuoke.module.ai.dal.dataobject.dify.AiPresetsDO;
import cn.metast.tuoke.module.ai.service.dify.AiApiKeyDifyService;
import cn.metast.tuoke.module.ai.service.dify.AiPresetsService;
import cn.metast.tuoke.module.ai.util.dify.DifyClient;
import cn.metast.tuoke.module.ai.util.dify.entity.DifyRequest;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.error;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - AI DIFY")
@RestController
@RequestMapping("/ai/dify")
@Slf4j
public class DifyController {

    @Resource
    private DifyClient difyClient;

    @Resource
    private AiPresetsService aiPresetsService;

    @Resource
    private AiApiKeyDifyService aiApiKeyDifyService;

    @GetMapping("/generateWorkByXc")
    @Operation(summary = "根据图片 URL 获取描述")
    public CommonResult<JSONObject> getImagePageMy(@RequestParam("imgUrl") String imgUrl) {
        JSONObject jsonObject = difyClient.generateWorkByXc(imgUrl);
        return success(jsonObject);
    }

    @GetMapping("/getPresets")
    @Operation(summary = "获取话题预设列表")
    public CommonResult<List<AiPresetsDO>> getPresets(@Validated AiPresetsRespVO respVO) {
        List<AiPresetsDO> PresetsList = aiPresetsService.getPresetsList(respVO);
        return success(PresetsList);
    }

    @PermitAll
    @GetMapping("/generateContent")
    @Operation(summary = "根据类型和文本生成文案")
    public CommonResult<JSONObject> generateContent(@Validated DifyRequest query) {

        List<AiApiKeyDifyDO> apiKeyList = aiApiKeyDifyService.getApiKeyList();
        Map<String, String> apiKeyMap = apiKeyList.stream()
                .collect(Collectors.toMap(AiApiKeyDifyDO::getPlatform, AiApiKeyDifyDO::getApiKey));

        JSONObject jsonObject = new JSONObject();
        if("xiaohongshu".equals(query.getPlatform())){
            // 小红书
            jsonObject = difyClient.generateXhs(apiKeyMap.get("xiaohongshu"), query.getBasicInstruction(), query.getBackgroundDetail(), query.getStyle());
            return success(jsonObject);
        }
        else if("douyin".equals(query.getPlatform())){
            // 抖音
            jsonObject = difyClient.generateCopywriting(apiKeyMap.get("douyin"), query.getBasicInstruction(), query.getBackgroundDetail(), query.getStyle(), "dy");
            if(jsonObject != null && jsonObject.get("content") != null){
                jsonObject.put("text", jsonObject.getString("content"));
            }
            return success(jsonObject);
        }
        else if("zhihu".equals(query.getPlatform())){
            // 知乎
            jsonObject = difyClient.generateCopywriting(apiKeyMap.get("zhihu"), query.getBasicInstruction(), query.getBackgroundDetail(), query.getStyle(), "dy");
            if(jsonObject != null && jsonObject.get("content") != null){
                jsonObject.put("text", jsonObject.getString("content"));
            }
            return success(jsonObject);
        }
        else if("gongzhonghao".equals(query.getPlatform())){
            // 公众号
            jsonObject = difyClient.generateCopywriting(apiKeyMap.get("gongzhonghao"), query.getBasicInstruction(), query.getBackgroundDetail(), query.getStyle(), "wxgzh");
            if(jsonObject != null && jsonObject.get("content") != null){
                jsonObject.put("text", jsonObject.getString("content"));
            }
            return success(jsonObject);
        }
        else if("wenan".equals(query.getPlatform())){
            // 文案生成
            jsonObject = difyClient.generateCopywriting(apiKeyMap.get("wenan"), query.getBasicInstruction(), query.getBackgroundDetail(), query.getStyle(), "tongyong");
            if(jsonObject != null && jsonObject.get("content") != null){
                jsonObject.put("text", jsonObject.getString("content"));
            }
            return success(jsonObject);
        }
        else if("huati".equals(query.getPlatform())){
            // 话题生成
            jsonObject = difyClient.generateTopic(apiKeyMap.get("huati"), query.getBasicInstruction(), query.getBackgroundDetail(), query.getStyle());
            return success(jsonObject);
        }
        else if("fanyi".equals(query.getPlatform())){
            // 翻译
            jsonObject = difyClient.generateWorkTranslate(apiKeyMap.get("fanyi"), query.getBasicInstruction(), query.getLanguage());
            if(jsonObject != null && jsonObject.get("翻译结果") != null){
                jsonObject.put("text", jsonObject.getString("翻译结果"));
            }
            return success(jsonObject);
        }
        else if("huiyi".equals(query.getPlatform())){
            // 会议纪要
            jsonObject = difyClient.generateXhs(apiKeyMap.get("huiyi"), query.getBasicInstruction(), "会议纪要", "会议纪要");
            return success(jsonObject);
        }
        else if("xitongzhushou".equals(query.getPlatform())){
            // 系统助手
            jsonObject = difyClient.generateCopywriting(apiKeyMap.get("xitongzhushou"), query.getBasicInstruction(), query.getBackgroundDetail(), query.getStyle(), "tongyong");
            if(jsonObject != null && jsonObject.get("content") != null){
                jsonObject.put("text", jsonObject.getString("content"));
            }
            return success(jsonObject);
        }
        else if("zhinengkefu".equals(query.getPlatform())){
            // 智能客服
            jsonObject = difyClient.generateCopywriting(apiKeyMap.get("zhinengkefu"), query.getBasicInstruction(), query.getBackgroundDetail(), query.getStyle(), "tongyong");
            if(jsonObject != null && jsonObject.get("content") != null){
                jsonObject.put("text", jsonObject.getString("content"));
            }
            return success(jsonObject);
        }
        else{
            return success(jsonObject);
        }

    }

    @PermitAll
    @GetMapping("/chat")
    @Operation(summary = "dify-工作流对话")
    public CommonResult<JSONObject> chatMessages(@Validated DifyRequest query) {
        if(StringUtils.isBlank(query.getQuery())){
            return error(500, "请输入对话内容!");
        }

        JSONObject jsonObject = new JSONObject();
        AiApiKeyDifyDO apiKeyMap = aiApiKeyDifyService.getApiKeyByPlatform("aichat");
        if(apiKeyMap == null || StringUtils.isBlank(apiKeyMap.getApiKey())){
            jsonObject.put("content", "忙碌中, 请稍后再试!");
            jsonObject.put("conversationId", null);
        }else{
            jsonObject = difyClient.aiChat(apiKeyMap.getApiKey(), query.getQuery(), query.getFileUrl(), query.getConversationId());
            if(jsonObject ==  null){
                jsonObject.put("content", "忙碌中, 请稍后再试!");
                jsonObject.put("conversationId", null);
            }
        }
        return success(jsonObject);
    }

}
