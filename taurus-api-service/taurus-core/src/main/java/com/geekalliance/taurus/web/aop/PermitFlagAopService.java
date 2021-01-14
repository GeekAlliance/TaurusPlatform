package com.geekalliance.taurus.web.aop;

import com.geekalliance.taurus.core.exception.ApiException;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.core.result.Result;
import com.geekalliance.taurus.web.annotation.PermitFlag;
import com.geekalliance.taurus.web.interceptor.HeaderMapRequestWrapper;
import com.geekalliance.taurus.web.interceptor.UserInterceptor;
import com.geekalliance.taurus.web.rest.CommonAuthRestProvider;
import com.geekalliance.taurus.web.utils.TokenUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author maxuqiang
 * @description
 * @date 2019/12/31
 **/
@Slf4j
@Component
@Aspect
public class PermitFlagAopService {
    @Autowired
    private UserInterceptor userInterceptor;

    @Autowired
    private CommonAuthRestProvider commonAuthRestProvider;

    @Before(value = "@annotation(com.geekalliance.taurus.web.annotation.PermitFlag)")
    public void doBefore(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        PermitFlag permitFlag = method.getDeclaredAnnotation(PermitFlag.class);
        if (!Objects.isNull(permitFlag)) {
            Result<Boolean> result = commonAuthRestProvider.hasPermission(userInterceptor.getAuthorizationToken(),permitFlag.value());
            if(Objects.isNull(result.getData())){
                throw new ApiException(SystemErrorType.ACCESS_DENIED);
            }
            if(!result.getData()){
                throw new ApiException(SystemErrorType.ACCESS_DENIED);
            }
        }
    }
}
