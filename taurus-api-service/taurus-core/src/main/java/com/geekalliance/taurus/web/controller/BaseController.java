package com.geekalliance.taurus.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekalliance.taurus.core.exception.ApiException;
import com.geekalliance.taurus.core.exception.ErrorType;
import com.geekalliance.taurus.core.result.PageResult;
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

    protected PageResult getResultByPage(IPage pageResult) {
        return getResultByPage(pageResult, null);
    }

    protected PageResult getResultByPage(IPage pageResult, Class clazz) {
        PageResult result = new PageResult();
        if (null != clazz) {
            result.setList(beanGenerator.convert(pageResult.getRecords(), clazz));
        } else {
            result.setList(pageResult.getRecords());
        }
        result.setCurrent(String.valueOf(pageResult.getCurrent()));
        result.setSize(String.valueOf(pageResult.getSize()));
        result.setTotal(String.valueOf(pageResult.getTotal()));
        return result;
    }
}
