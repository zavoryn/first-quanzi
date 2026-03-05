package cn.metast.tuoke.module.member.framework.web.config;

import cn.metast.tuoke.framework.swagger.config.TuokeSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * member 模块的 web 组件的 Configuration
 *
 * @author metast.cn
 */
@Configuration(proxyBeanMethods = false)
public class MemberWebConfiguration {

    /**
     * member 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi memberGroupedOpenApi() {
        return TuokeSwaggerAutoConfiguration.buildGroupedOpenApi("member");
    }

}
