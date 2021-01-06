package com.geekalliance.taurus.base.oauth.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Date 2019/12/23
 * @Author maxuqiang
 **/

@Component
public class ClientAuthenticationManager {
    private ProviderManager oAuth2AuthenticationManager;

    ClientAuthenticationManager(BCryptPasswordEncoder passwordEncoder,ClientDetailsService clientDetailsService){
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider(passwordEncoder,clientDetailsService));
        this.oAuth2AuthenticationManager = new ProviderManager(providers);
    }

    private DaoAuthenticationProvider daoAuthenticationProvider(BCryptPasswordEncoder passwordEncoder,ClientDetailsService clientDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(clientdetailsuserdetailsservice(clientDetailsService));
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        // 使用BCrypt进行密码的hash
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    private ClientDetailsUserDetailsService clientdetailsuserdetailsservice(ClientDetailsService clientDetailsService){
        return new ClientDetailsUserDetailsService(clientDetailsService);
    }

    public ProviderManager getOAuth2AuthenticationManager(){
        return this.oAuth2AuthenticationManager;
    }
}
