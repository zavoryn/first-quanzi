package cn.metast.tuoke.module.promotion.framework.web.config;

import cn.metast.tuoke.framework.swagger.config.TuokeSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * promotion 模块的 web 组件的 Configuration
 *
 * @author metast.cn
 */
@Configuration(proxyBeanMethods = false)
public class PromotionWebConfiguration {

    /**
     * promotion 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi promotionGroupedOpenApi() {
        return TuokeSwaggerAutoConfiguration.buildGroupedOpenApi("promotion");
    }

}
