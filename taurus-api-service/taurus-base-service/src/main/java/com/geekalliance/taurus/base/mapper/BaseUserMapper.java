package com.geekalliance.taurus.base.mapper;

import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserMapper extends RdbBaseMapper<BaseUser> {
    @Select("select * from base_user where username = #{username} and delete_tag = #{deleteTag}")
    BaseUser getSysUserByUserName(String username, String logicNotDeleteValue);
}
