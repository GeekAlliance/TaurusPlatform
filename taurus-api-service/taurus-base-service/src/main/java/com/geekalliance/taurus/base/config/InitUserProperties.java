package com.geekalliance.taurus.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author maxuqiang
 */
@Data
@ConfigurationProperties(InitUserProperties.PREFIX)
public class InitUserProperties {
    public static final String PREFIX = "spring.geekalliance.init";

    private String userName;

    private String password;
}
