package com.geekalliance.taurus.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import javax.crypto.SecretKey;

/**
 * @Description
 * @Date 2020/1/2
 * @Author maxuqiang
 **/
@Slf4j
public class JwtUtils {
    public static String AUTHORIZATION_PREFIX = "bearer ";

    public static boolean invalidJwtAccessToken(String authentication, String algorithm, SecretKey signingKey) {
        return invalidJwtAccessToken(authentication, new MacSigner(algorithm,signingKey));
    }

    public static boolean invalidJwtAccessToken(String authentication, SecretKey signingKey) {
        return invalidJwtAccessToken(authentication, new MacSigner(signingKey));
    }

    public static boolean invalidJwtAccessToken(String authentication, String signingKey) {
        return invalidJwtAccessToken(authentication, new MacSigner(signingKey));
    }

    public static Jwt getJwt(String authentication) {
        return JwtHelper.decode(authentication);
    }

    private static boolean invalidJwtAccessToken(String authentication, MacSigner verifier) {
        //是否无效true表示无效
        boolean invalid = Boolean.TRUE;
        try {
            Jwt jwt = getJwt(authentication);
            jwt.verifySignature(verifier);
            invalid = Boolean.FALSE;
        } catch (InvalidSignatureException | IllegalArgumentException ex) {
            log.warn("user token has expired or signature error ");
        }
        return invalid;
    }
}
