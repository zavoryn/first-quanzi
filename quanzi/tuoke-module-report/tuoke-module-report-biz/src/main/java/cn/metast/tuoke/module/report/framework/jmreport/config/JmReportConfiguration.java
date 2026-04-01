package cn.metast.tuoke.module.report.framework.jmreport.config;

import cn.metast.tuoke.framework.security.config.SecurityProperties;
import cn.metast.tuoke.module.system.api.oauth2.OAuth2TokenApi;
import cn.metast.tuoke.module.report.framework.jmreport.core.service.JmReportTokenServiceImpl;
import cn.metast.tuoke.module.system.api.permission.PermissionApi;
import org.jeecg.modules.jmreport.api.JmReportTokenServiceI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 积木报表的配置类
 *
 * @author metast.cn
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "org.jeecg.modules.jmreport") // 扫描积木报表的包
public class JmReportConfiguration {

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public JmReportTokenServiceI jmReportTokenService(OAuth2TokenApi oAuth2TokenApi,
                                                      PermissionApi permissionApi,
                                                      SecurityProperties securityProperties) {
        return new JmReportTokenServiceImpl(oAuth2TokenApi, permissionApi, securityProperties);
    }

}
