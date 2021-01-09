package com.geekalliance.taurus.base.init;


import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.base.api.auth.enums.GrantTypeEnum;
import com.geekalliance.taurus.base.api.auth.enums.PasswordTypeEnum;
import com.geekalliance.taurus.base.config.InitUserProperties;
import com.geekalliance.taurus.base.oauth.config.OauthClientProperties;
import com.geekalliance.taurus.base.service.BaseUserService;
import com.geekalliance.taurus.rdb.config.DynamicDataSourceConfig;
import com.geekalliance.taurus.rdb.utils.ExclusiveLockUtils;
import com.geekalliance.taurus.toolkit.enums.CommonEnum;
import com.hollysys.platform.common.core.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author maxuqiang
 * @description
 * @date 2019/12/30
 **/
@Slf4j
@Service
public class InitBaseApplicationService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    private BaseUserService baseUserService;

    @Resource
    private OauthClientProperties oauthClient;

    @Resource
    private InitUserProperties initUserProperties;

    @Async
    public void initClient() {
        Connection connection = DynamicDataSourceConfig.getConnection();
        try {
            if (Objects.nonNull(connection)) {
                ExclusiveLockUtils.lockTable(connection, "dv_change_log_lock", "locked");
                initBrowserClient();
            }
        } catch (Exception e) {
            log.error("init client error", e);
        } finally {
            try {
                if (Objects.nonNull(connection)) {
                    ExclusiveLockUtils.releaseLock(connection);
                }
            } catch (SQLException e) {
                log.error("init client releaseLock error", e);
            }
        }
    }

    @Async
    public void initUser() {
        try {
            BaseUser user = baseUserService.getSysUserByUserName(initUserProperties.getUserName());
            if (Objects.isNull(user)) {
                log.info("初始化系统用户 {} 开始", initUserProperties.getUserName());
                user = new BaseUser();
                user.setId(Md5Utils.encodeByMD5(initUserProperties.getUserName()));
                user.setUsername(initUserProperties.getUserName());
                user.setPassword(passwordEncoder.encode(initUserProperties.getPassword()));
                user.setSuperFlag(CommonEnum.YES.getCode());
                user.setPasswordType(PasswordTypeEnum.INIT_PASSWORD.getCode());
                baseUserService.saveOrUpdate(user);
                log.info("初始化系统用户 {} 结束", initUserProperties.getUserName());
            }
        } catch (Exception e) {
            log.error("初始化系统用户错误 {}", e.getMessage());
        }
    }

    private void initBrowserClient() {
        try {
            jdbcClientDetailsService.loadClientByClientId(oauthClient.getBrowserClientId());
        } catch (NoSuchClientException e) {
            log.info("初始化B端Oauth2 client开始");
            Set<String> grantType = new TreeSet<>();
            grantType.add(GrantTypeEnum.PASSWORD.getCode());
            grantType.add(GrantTypeEnum.REFRESH_TOKEN.getCode());
            BaseClientDetails baseClientDetails = new BaseClientDetails();
            baseClientDetails.setClientId(oauthClient.getBrowserClientId());
            baseClientDetails.setAccessTokenValiditySeconds(2592000);
            baseClientDetails.setRefreshTokenValiditySeconds(5184000);
            baseClientDetails.setClientSecret(oauthClient.getBrowserClientSecret());
            baseClientDetails.setScope(oauthClient.getBrowserScope());
            baseClientDetails.setAuthorizedGrantTypes(grantType);
            jdbcClientDetailsService.addClientDetails(baseClientDetails);
            log.info("初始化B端Oauth2 client结束");
        }
    }
}