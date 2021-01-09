package com.geekalliance.taurus.base.oauth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供权限验证时用户、角色、行为、模块列表等资源信息的查询
 *
 * @author maxuqiang
 */
@Service
@Slf4j
public class ResourceService {
    /**
     * 根据当前用户 查询角色编号
     *
     * @param id
     * @return
     */
    public List<String> getAuthorities(String id) {
        List<String> authorities = new ArrayList<>();
        return authorities;
    }
}
