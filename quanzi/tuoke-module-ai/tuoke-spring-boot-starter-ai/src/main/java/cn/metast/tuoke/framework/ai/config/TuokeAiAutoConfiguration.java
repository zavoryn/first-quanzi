package cn.metast.tuoke.framework.ai.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.metast.tuoke.framework.ai.core.factory.AiModelFactory;
import cn.metast.tuoke.framework.ai.core.factory.AiModelFactoryImpl;
import cn.metast.tuoke.framework.ai.core.model.baichuan.BaiChuanChatModel;
import cn.metast.tuoke.framework.ai.core.model.deepseek.DeepSeekChatModel;
import cn.metast.tuoke.framework.ai.core.model.doubao.DouBaoChatModel;
import cn.metast.tuoke.framework.ai.core.model.hunyuan.HunYuanChatModel;
import cn.metast.tuoke.framework.ai.core.model.midjourney.api.MidjourneyApi;
import cn.metast.tuoke.framework.ai.core.model.siliconflow.SiliconFlowApiConstants;
import cn.metast.tuoke.framework.ai.core.model.siliconflow.SiliconFlowChatModel;
import cn.metast.tuoke.framework.ai.core.model.suno.api.SunoApi;
import cn.metast.tuoke.framework.ai.core.model.xinghuo.XingHuoChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.autoconfigure.vectorstore.milvus.MilvusServiceClientProperties;
import org.springframework.ai.autoconfigure.vectorstore.milvus.MilvusVectorStoreProperties;
import org.springframework.ai.autoconfigure.vectorstore.qdrant.QdrantVectorStoreProperties;
import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreProperties;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.tokenizer.TokenCountEstimator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 元圈 AI 自动配置
 *
 * @author fansili
 */
@AutoConfiguration
@EnableConfigurationProperties({ TuokeAiProperties.class,
        QdrantVectorStoreProperties.class, // 解析 Qdrant 配置
        RedisVectorStoreProperties.class, // 解析 Redis 配置
        MilvusVectorStoreProperties.class, MilvusServiceClientProperties.class // 解析 Milvus 配置
})
@Slf4j
public class TuokeAiAutoConfiguration {

    @Bean
    public AiModelFactory aiModelFactory() {
        return new AiModelFactoryImpl();
    }

    // ========== 各种 AI Client 创建 ==========

    @Bean
    @ConditionalOnProperty(value = "tuoke.ai.deepseek.enable", havingValue = "true")
    public DeepSeekChatModel deepSeekChatModel(TuokeAiProperties tuokeAiProperties) {
        TuokeAiProperties.DeepSeekProperties properties = tuokeAiProperties.getDeepseek();
        return buildDeepSeekChatModel(properties);
    }

    public DeepSeekChatModel buildDeepSeekChatModel(TuokeAiProperties.DeepSeekProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(DeepSeekChatModel.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(DeepSeekChatModel.BASE_URL)
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new DeepSeekChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "tuoke.ai.doubao.enable", havingValue = "true")
    public DouBaoChatModel douBaoChatClient(TuokeAiProperties tuokeAiProperties) {
        TuokeAiProperties.DouBaoProperties properties = tuokeAiProperties.getDoubao();
        return buildDouBaoChatClient(properties);
    }

    public DouBaoChatModel buildDouBaoChatClient(TuokeAiProperties.DouBaoProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(DouBaoChatModel.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(DouBaoChatModel.BASE_URL)
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new DouBaoChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "tuoke.ai.siliconflow.enable", havingValue = "true")
    public SiliconFlowChatModel siliconFlowChatClient(TuokeAiProperties tuokeAiProperties) {
        TuokeAiProperties.SiliconFlowProperties properties = tuokeAiProperties.getSiliconflow();
        return buildSiliconFlowChatClient(properties);
    }

    public SiliconFlowChatModel buildSiliconFlowChatClient(TuokeAiProperties.SiliconFlowProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(SiliconFlowApiConstants.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(SiliconFlowApiConstants.DEFAULT_BASE_URL)
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new SiliconFlowChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "tuoke.ai.hunyuan.enable", havingValue = "true")
    public HunYuanChatModel hunYuanChatClient(TuokeAiProperties tuokeAiProperties) {
        TuokeAiProperties.HunYuanProperties properties = tuokeAiProperties.getHunyuan();
        return buildHunYuanChatClient(properties);
    }

    public HunYuanChatModel buildHunYuanChatClient(TuokeAiProperties.HunYuanProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(HunYuanChatModel.MODEL_DEFAULT);
        }
        // 特殊：由于混元大模型不提供 deepseek，而是通过知识引擎，所以需要区分下 URL
        if (StrUtil.isEmpty(properties.getBaseUrl())) {
            properties.setBaseUrl(
                    StrUtil.startWithIgnoreCase(properties.getModel(), "deepseek") ? HunYuanChatModel.DEEP_SEEK_BASE_URL
                            : HunYuanChatModel.BASE_URL);
        }
        // 创建 OpenAiChatModel、HunYuanChatModel 对象
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(properties.getBaseUrl())
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new HunYuanChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "tuoke.ai.xinghuo.enable", havingValue = "true")
    public XingHuoChatModel xingHuoChatClient(TuokeAiProperties tuokeAiProperties) {
        TuokeAiProperties.XingHuoProperties properties = tuokeAiProperties.getXinghuo();
        return buildXingHuoChatClient(properties);
    }

    public XingHuoChatModel buildXingHuoChatClient(TuokeAiProperties.XingHuoProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(XingHuoChatModel.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(XingHuoChatModel.BASE_URL)
                        .apiKey(properties.getAppKey() + ":" + properties.getSecretKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new XingHuoChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "tuoke.ai.baichuan.enable", havingValue = "true")
    public BaiChuanChatModel baiChuanChatClient(TuokeAiProperties tuokeAiProperties) {
        TuokeAiProperties.BaiChuanProperties properties = tuokeAiProperties.getBaichuan();
        return buildBaiChuanChatClient(properties);
    }

    public BaiChuanChatModel buildBaiChuanChatClient(TuokeAiProperties.BaiChuanProperties properties) {
        if (StrUtil.isEmpty(properties.getModel())) {
            properties.setModel(BaiChuanChatModel.MODEL_DEFAULT);
        }
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl(BaiChuanChatModel.BASE_URL)
                        .apiKey(properties.getApiKey())
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(properties.getModel())
                        .temperature(properties.getTemperature())
                        .maxTokens(properties.getMaxTokens())
                        .topP(properties.getTopP())
                        .build())
                .toolCallingManager(getToolCallingManager())
                .build();
        return new BaiChuanChatModel(openAiChatModel);
    }

    @Bean
    @ConditionalOnProperty(value = "tuoke.ai.midjourney.enable", havingValue = "true")
    public MidjourneyApi midjourneyApi(TuokeAiProperties tuokeAiProperties) {
        TuokeAiProperties.MidjourneyProperties config = tuokeAiProperties.getMidjourney();
        return new MidjourneyApi(config.getBaseUrl(), config.getApiKey(), config.getNotifyUrl());
    }

    @Bean
    @ConditionalOnProperty(value = "tuoke.ai.suno.enable", havingValue = "true")
    public SunoApi sunoApi(TuokeAiProperties tuokeAiProperties) {
        return new SunoApi(tuokeAiProperties.getSuno().getBaseUrl());
    }

    // ========== RAG 相关 ==========

    @Bean
    public TokenCountEstimator tokenCountEstimator() {
        return new JTokkitTokenCountEstimator();
    }

    @Bean
    public BatchingStrategy batchingStrategy() {
        return new TokenCountBatchingStrategy();
    }

    private static ToolCallingManager getToolCallingManager() {
        return SpringUtil.getBean(ToolCallingManager.class);
    }

}