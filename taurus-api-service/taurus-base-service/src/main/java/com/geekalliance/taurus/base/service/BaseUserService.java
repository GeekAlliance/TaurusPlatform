package com.geekalliance.taurus.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.geekalliance.taurus.base.api.auth.dto.BaseUserPageDTO;
import com.geekalliance.taurus.base.api.auth.dto.QueryUserDTO;
import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.base.mapper.BaseUserMapper;
import com.geekalliance.taurus.core.params.PageQueryParam;
import com.geekalliance.taurus.rdb.service.RdbService;
import com.geekalliance.taurus.rdb.utils.PageConverterUtils;
import com.geekalliance.taurus.toolkit.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseUserService extends RdbService<BaseUserMapper, BaseUser> {
    public BaseUser getBaseUserByUserName(String username) {
        QueryWrapper<BaseUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<BaseUser> baseUsers = list(queryWrapper);
        if(CollectionUtils.isNotEmpty(baseUsers)){
            return baseUsers.get(0);
        }
        return null;
    }

    public IPage<BaseUserPageDTO> pageQuery(PageQueryParam<QueryUserDTO> param) {
        return super.page(PageConverterUtils.getPage(param));
    }
}
