package com.geekalliance.taurus.base.organization.mapper;

import com.geekalliance.taurus.base.api.organization.entity.PersonPost;
import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 21:50
 */
@Mapper
public interface PersonPostMapper extends RdbBaseMapper<PersonPost> {

}