package com.geekalliance.taurus.base.oauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Description
 * @Date 2019/12/22
 * @Author maxuqiang
 **/
@Data
@Component
@ConfigurationProperties(OauthClientProperties.PREFIX)
public class OauthClientProperties {
    public static final String PREFIX = "spring.security.oauth2.client";

    private String browserClientId;

    private String browserClientSecret;

    private Set<String> browserScope;

}
