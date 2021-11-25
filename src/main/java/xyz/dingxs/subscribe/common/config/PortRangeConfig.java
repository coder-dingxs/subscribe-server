package xyz.dingxs.subscribe.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "port-range")
public class PortRangeConfig {

    private Integer min;

    private Integer max;

}
