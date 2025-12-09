package cn.metast.tuoke.module.infra.framework.file.config;

import cn.metast.tuoke.module.infra.framework.file.core.client.FileClientFactory;
import cn.metast.tuoke.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author metast.cn
 */
@Configuration(proxyBeanMethods = false)
public class TuokeFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
