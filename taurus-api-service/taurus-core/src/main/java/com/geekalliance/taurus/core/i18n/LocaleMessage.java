package com.geekalliance.taurus.core.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("localeMessage")
public class LocaleMessage{
    @Resource
    private MessageSource messageSource;

    public String getMessage(String msgKey){
        return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
    }
}
