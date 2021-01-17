package com.geekalliance.taurus.base.oauth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.geekalliance.taurus.base.api.auth.entity.Resource;
import com.geekalliance.taurus.base.api.auth.enums.ResourceTypeEnum;
import com.geekalliance.taurus.base.auth.mapper.ResourceMapper;
import com.geekalliance.taurus.core.entity.BaseTreeNode;
import com.geekalliance.taurus.core.exception.InternalException;
import com.geekalliance.taurus.core.exception.SystemErrorType;
import com.geekalliance.taurus.core.holder.UserContextHolder;
import com.geekalliance.taurus.core.holder.entity.TokenUser;
import com.geekalliance.taurus.core.utils.BaseTreeNodeConverterUtils;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.toolkit.utils.CollectionUtils;
import com.geekalliance.taurus.toolkit.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 提供权限验证时用户、角色、行为、模块列表等资源信息的查询
 *
 * @author maxuqiang
 */
@Service
@Slf4j
public class AuthResourceService {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ResourceMapper resourceMapper;

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

    public boolean tokenValid(String token) {
        if (!StringUtils.isNotBlank(token)) {
            log.error("token is blank");
            return false;
        }
        try {
            OAuth2AccessToken result = tokenStore.readAccessToken(token);
            if (Objects.isNull(result)) {
                throw new InternalException(SystemErrorType.INVALID_TOKEN);
            }
            if (!Objects.isNull(result) && result.isExpired()) {
                throw new InternalException(SystemErrorType.EXPIRED_TOKEN);
            }
        } catch (OAuth2Exception e) {
            throw new InternalException(SystemErrorType.valueOf(e.getOAuth2ErrorCode().toUpperCase()));
        }
        return true;
    }

    public List<BaseTreeNode<Resource>> getPermissionModule() {
        return BaseTreeNodeConverterUtils.converter(getPermissionResourceByTypeAndParent(ResourceTypeEnum.MODULE, StringPool.EMPTY));
    }

    public List<Resource> getPermissionAction(String parent) {
        return getPermissionResourceByTypeAndParent(ResourceTypeEnum.ACTION, parent);
    }

    public boolean hasPermissionByPermitFlag(String permitFlag) {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("res.permit_flag", permitFlag);
        TokenUser tokenUser = UserContextHolder.getInstance().getTokenUser();
        if (!tokenUser.isSuperManage()) {
            queryWrapper.eq("ur.user_id", UserContextHolder.getInstance().getTokenUser().getId());
        }
        List<Resource> resources = resourceMapper.selectListByUserPermission(queryWrapper);
        return CollectionUtils.isNotEmpty(resources);
    }

    private List<Resource> getPermissionResourceByTypeAndParent(ResourceTypeEnum type, String parent) {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("res.type", type.getCode());
        queryWrapper.eq(StringUtils.isNotBlank(parent), "res.parent", parent);
        TokenUser tokenUser = UserContextHolder.getInstance().getTokenUser();
        if (!tokenUser.isSuperManage()) {
            queryWrapper.eq("ur.user_id", UserContextHolder.getInstance().getTokenUser().getId());
        }
        return resourceMapper.selectListByUserPermission(queryWrapper);
    }
}
