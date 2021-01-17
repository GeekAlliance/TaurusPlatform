package com.geekalliance.taurus.base.init;


import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.base.api.auth.enums.GrantTypeEnum;
import com.geekalliance.taurus.base.api.auth.enums.PasswordTypeEnum;
import com.geekalliance.taurus.base.api.auth.register.entity.RegisterApplication;
import com.geekalliance.taurus.base.api.auth.register.utils.GenRegisterResourceUtil;
import com.geekalliance.taurus.base.auth.service.BaseUserService;
import com.geekalliance.taurus.base.auth.service.RegisterApplicationService;
import com.geekalliance.taurus.base.config.InitUserProperties;
import com.geekalliance.taurus.base.oauth.config.OauthClientProperties;
import com.geekalliance.taurus.rdb.config.DynamicDataSourceConfig;
import com.geekalliance.taurus.rdb.utils.ExclusiveLockUtils;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.toolkit.enums.CommonEnum;
import com.geekalliance.taurus.toolkit.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
    private static boolean complete = false;

    private static final String APPLICATION_INIT = "i18n/base_application_init_" + LocaleContextHolder.getLocale() + StringPool.DOT_XML;

    @Value("${spring.liquibase.database-change-log-lock-table}")
    private String lockTableName;

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

    @Autowired
    private RegisterApplicationService registerApplicationService;

    @Async
    public void initApplication() {
        while (!complete) {
            synchronized (APPLICATION_INIT) {
                List<RegisterApplication> applications = GenRegisterResourceUtil.getApplication(APPLICATION_INIT);
                try {
                    registerApplicationService.register(applications, true);
                    complete = true;
                } catch (RuntimeException e) {
                    log.error("init application error {}", e);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        log.error("init application interrupted exception");
                        complete = false;
                    }
                    complete = false;
                }
            }
        }
    }

    @Async
    public void initClient() {
        Connection connection = DynamicDataSourceConfig.getConnection();
        try {
            if (Objects.nonNull(connection)) {
                ExclusiveLockUtils.lockTable(connection, lockTableName, "locked");
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
            BaseUser user = baseUserService.getBaseUserByUserName(initUserProperties.getUsername());
            if (Objects.isNull(user)) {
                log.info("初始化系统用户 {} 开始", initUserProperties.getUsername());
                user = new BaseUser();
                user.setId(Md5Utils.encodeByMD5(initUserProperties.getUsername()));
                user.setUsername(initUserProperties.getUsername());
                user.setPassword(passwordEncoder.encode(initUserProperties.getPassword()));
                user.setSuperFlag(CommonEnum.YES.getCode());
                user.setPasswordType(PasswordTypeEnum.INIT_PASSWORD.getCode());
                baseUserService.saveOrUpdate(user);
                log.info("初始化系统用户 {} 结束", initUserProperties.getUsername());
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
