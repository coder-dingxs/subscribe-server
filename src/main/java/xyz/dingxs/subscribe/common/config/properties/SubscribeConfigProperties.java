package xyz.dingxs.subscribe.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "subscribe")
public class SubscribeConfigProperties {
    /**
     * 权限配置
     */
    @NestedConfigurationProperty
    private AuthorizedConfigProperties authorized;


    /**
     * 端口范围配置
     */
    @NestedConfigurationProperty
    private PortRangeConfigProperties portRange;

    /**
     * 嗅探配置
     */
    @NestedConfigurationProperty
    private SniffConfigProperties sniff;


}
