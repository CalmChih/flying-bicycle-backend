package top.fallingintodreams.flying.bicycle.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

    private String appId;
    private String serverUrl;
    private String privateKey;
    private String alipayPublicKey;
    private String format;
    private String charset;
    private String signType;
    private String notifyUrl;

}
