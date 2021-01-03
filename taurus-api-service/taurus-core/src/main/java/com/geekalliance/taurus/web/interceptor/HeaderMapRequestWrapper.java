package com.geekalliance.taurus.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Date 2019/12/24
 * @Author maxuqiang
 **/

public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    private Map<String,String> headerMap = new HashMap<>();

    public HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void addHeader(String name,String value){
        headerMap.put(name,value);
    }

    @Override
    public String getHeader(String name){
        String value = super.getHeader(name);
        if(headerMap.containsKey(name)){
            value = headerMap.get(name);
        }
        return value;
    }
}
