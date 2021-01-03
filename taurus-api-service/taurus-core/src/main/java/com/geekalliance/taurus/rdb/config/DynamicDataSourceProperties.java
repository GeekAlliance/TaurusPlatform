package com.geekalliance.taurus.rdb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author maxuqiang
 */
@Data
@ConfigurationProperties(DynamicDataSourceProperties.PREFIX)
public class DynamicDataSourceProperties {
    public static final String PREFIX = "spring.datasource";

    private boolean changeEnable = true;

    private Map<String, Map<String, String>> ips;
}
