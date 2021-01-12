package com.geekalliance.taurus.base.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.geekalliance.taurus.base.api.auth.dto.BaseUserPageDTO;
import com.geekalliance.taurus.base.api.auth.dto.QueryBaseUserDTO;
import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.base.config.InitUserProperties;
import com.geekalliance.taurus.base.auth.mapper.BaseUserMapper;
import com.geekalliance.taurus.core.params.PageQueryParam;
import com.geekalliance.taurus.core.utils.BaseEntityFillUtils;
import com.geekalliance.taurus.rdb.service.RdbService;
import com.geekalliance.taurus.rdb.utils.PageConverterUtils;
import com.geekalliance.taurus.toolkit.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseUserService extends RdbService<BaseUserMapper, BaseUser> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InitUserProperties userProperties;

    public BaseUser getBaseUserByUserName(String username) {
        QueryWrapper<BaseUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<BaseUser> baseUsers = list(queryWrapper);
        if (CollectionUtils.isNotEmpty(baseUsers)) {
            return baseUsers.get(0);
        }
        return null;
    }

    public IPage<BaseUserPageDTO> pageQuery(PageQueryParam<QueryBaseUserDTO> param) {
        return super.page(PageConverterUtils.getPage(param));
    }

    public boolean saveBaseUser(BaseUser baseUser) {
        baseUser.setPassword(getInitPassword());
        BaseEntityFillUtils.setBaseEntity(baseUser, true);
        return save(baseUser);
    }


    /**
     * 获取配置初始密码
     */
    public String getInitPassword() {
        return passwordEncoder.encode(userProperties.getPassword());
    }
}
