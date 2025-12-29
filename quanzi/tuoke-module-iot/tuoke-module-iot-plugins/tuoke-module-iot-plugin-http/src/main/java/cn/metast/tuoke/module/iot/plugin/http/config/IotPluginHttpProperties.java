package cn.metast.tuoke.module.iot.plugin.http.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "tuoke.iot.plugin.http")
@Validated
@Data
public class IotPluginHttpProperties {

    /**
     * HTTP 服务端口
     */
    private Integer serverPort;

}
