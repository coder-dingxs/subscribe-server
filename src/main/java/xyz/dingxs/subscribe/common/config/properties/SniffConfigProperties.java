package xyz.dingxs.subscribe.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
public class SniffConfigProperties {

    private Integer count;

    private Integer successCount;

    private Integer timeout;

    @NestedConfigurationProperty
    private TaskConfigProperties task;

    @Data
    public static class TaskConfigProperties {

        private String cron;

    }
}
