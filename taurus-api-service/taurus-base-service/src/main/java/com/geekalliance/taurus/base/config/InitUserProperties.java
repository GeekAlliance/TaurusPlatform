package com.geekalliance.taurus.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author maxuqiang
 */
@Data
@Component
@ConfigurationProperties(InitUserProperties.PREFIX)
public class InitUserProperties {
    public static final String PREFIX = "spring.geekalliance.init";

    private String username;

    private String password;
}
