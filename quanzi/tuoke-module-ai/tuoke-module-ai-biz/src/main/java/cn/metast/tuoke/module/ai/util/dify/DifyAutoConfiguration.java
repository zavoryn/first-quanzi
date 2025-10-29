package cn.metast.tuoke.module.ai.util.dify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DifyProperties.class)
public class DifyAutoConfiguration {
    @Autowired
    private DifyProperties difyProperties;

    @Bean
    public DifyClient getDifyClient() {
        return new DifyClient(difyProperties.getApiUrl(), difyProperties.getApiKeyChat(), difyProperties.getApiKeyImg());
    }
}
