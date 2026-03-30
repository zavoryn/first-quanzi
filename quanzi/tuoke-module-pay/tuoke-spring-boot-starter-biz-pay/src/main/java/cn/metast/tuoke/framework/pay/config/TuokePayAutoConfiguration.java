package cn.metast.tuoke.framework.pay.config;

import cn.metast.tuoke.framework.pay.core.client.PayClientFactory;
import cn.metast.tuoke.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author metast.cn
 */
@AutoConfiguration
public class TuokePayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
