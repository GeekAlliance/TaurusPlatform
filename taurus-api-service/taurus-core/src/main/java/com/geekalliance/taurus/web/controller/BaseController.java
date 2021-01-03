package com.geekalliance.taurus.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekalliance.taurus.core.exception.ApiException;
import com.geekalliance.taurus.core.exception.ErrorType;
import com.geekalliance.taurus.core.utils.BeanGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;

/**
 * @author maxuqiang
 */
public class BaseController {
    @Autowired
    protected Validator globalValidator;

    @Autowired
    protected BeanGenerator beanGenerator;

    @Autowired
    protected ObjectMapper objectMapper;


    public void throwApiException(ErrorType errorType) {
        throw new ApiException(errorType);
    }
}
