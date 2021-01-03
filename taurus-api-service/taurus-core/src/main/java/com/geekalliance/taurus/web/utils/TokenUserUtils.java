package com.geekalliance.taurus.web.utils;


import com.geekalliance.taurus.core.utils.JwtUtils;
import com.geekalliance.taurus.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Date 2019/12/23
 * @Author maxuqiang
 **/

@Slf4j
public class TokenUserUtils {
    public static String HTTP_HEADERS_SIGNING_KEY = "signingKey";

    public static String getAuthorization(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String signingKey = request.getHeader(HTTP_HEADERS_SIGNING_KEY);
        return getAuthorization(authorization, signingKey);
    }

    public static String getAuthorization(String authorization, String signingKey) {
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(JwtUtils.AUTHORIZATION_PREFIX)) {
            if (!JwtUtils.invalidJwtAccessToken(authorization.replace(JwtUtils.AUTHORIZATION_PREFIX, StringPool.EMPTY), signingKey)) {
                return authorization.replace(JwtUtils.AUTHORIZATION_PREFIX, StringPool.EMPTY);
            }
        }
        return StringPool.EMPTY;
    }
}
