package com.geekalliance.taurus.web.interceptor;


import com.geekalliance.taurus.core.holder.UserContextHolder;
import com.geekalliance.taurus.core.holder.entity.TokenUser;
import com.geekalliance.taurus.web.utils.TokenUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当前用户拦截
 *
 * @author maxuqiang
 */
@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    @Value("${spring.geekalliance.auth.resource.interceptor.enable}")
    private Boolean resourceInterceptorEnable;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
        requestWrapper.addHeader(TokenUserUtils.HTTP_HEADERS_SIGNING_KEY, signingKey);
        String authorization = TokenUserUtils.getAuthorization(requestWrapper);
        if (StringUtils.isNotBlank(authorization)) {
            TokenUser tokenUser = getTokenUser(authorization);
            UserContextHolder.getInstance().setContext(tokenUser);
            validAuthResource(request);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContextHolder.getInstance().clear();
    }

    private TokenUser getTokenUser(String authorization) {
        TokenUser tokenUser = new TokenUser();
        return tokenUser;
    }

    /**
     * 验证是否有权限访问资源
     *
     * @param request
     */
    private void validAuthResource(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getContextPath());
        String servletPath = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        url.append(servletPath);

    }

}
