package cn.metast.tuoke.module.ai.util.dify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dify")
@Data
public class DifyProperties {
    private String apiUrl;
    private String apiKeyChat;
    private String apiKeyImg;
}
