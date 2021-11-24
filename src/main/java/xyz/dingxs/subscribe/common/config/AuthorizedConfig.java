package xyz.dingxs.subscribe.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 权限yml配置
 *
 * @author dingxs
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "authorized")
public class AuthorizedConfig {

    private String token;

}
