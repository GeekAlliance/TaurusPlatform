package com.geekalliance.taurus.core.holder;


import com.geekalliance.taurus.core.holder.entity.TokenUser;
import com.geekalliance.taurus.toolkit.utils.JsonUtils;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author maxuqiang
 */
public class UserContextHolder {

    private final ThreadLocal<Map<String, String>> threadLocal;

    private UserContextHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    /**
     * 创建实例
     */
    public static UserContextHolder getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 静态内部类单例模式
     * 单例初使化
     */
    private static class SingletonHolder {
        private static final UserContextHolder INSTANCE = new UserContextHolder();
    }

    /**
     * 用户上下文中放入信息
     */
    public void setContext(Map<String, String> map) {
        threadLocal.set(map);
    }

    /**
     * 用户上下文中放入信息
     */
    public void setContext(TokenUser user) {
        Map map = JsonUtils.serializable(JsonUtils.deserializer(user), HashMap.class);
        threadLocal.set(map);
    }

    /**
     * 获取上下文中的信息
     */
    public Map<String, String> getContext() {
        return threadLocal.get();
    }

    /**
     * 获取上下文中的用户名
     */
    public String getUsername() {
        return Optional.ofNullable(threadLocal.get()).orElse(Maps.newHashMap()).get("user_name");
    }

    /**
     * 返回当前登录TokenUser
     *
     * @return
     */
    public TokenUser getTokenUser() {
        TokenUser user = JsonUtils.serializable(JsonUtils.deserializer(Optional.ofNullable(threadLocal.get()).orElse(Maps.newHashMap())), TokenUser.class);
        return user;
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }

}
