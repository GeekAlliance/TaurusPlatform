package com.geekalliance.taurus.web.rest;

import com.geekalliance.taurus.core.holder.entity.TokenUser;
import com.geekalliance.taurus.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "${spring.geekalliance.auth.service.name}", fallback = CommonAuthRestProvider.CommonAuthRestFallback.class)
public interface CommonAuthRestProvider {
    @GetMapping(value = "${spring.geekalliance.auth.token-user.url}")
    Result<TokenUser> tokenUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication);

    @GetMapping(value = "${spring.geekalliance.auth.permission-valid.url}")
    Result<Boolean> hasPermission(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @PathVariable(value = "permitFlag") String permitFlag);

    @Component
    class CommonAuthRestFallback implements CommonAuthRestProvider {
        @Override
        public Result tokenUser(String authentication) {
            return Result.success(new TokenUser());
        }

        @Override
        public Result<Boolean> hasPermission(String authentication, String permitFlag) {
            return Result.success(false);
        }
    }
}
