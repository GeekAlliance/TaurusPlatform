package com.geekalliance.taurus.base.system.mapper;

import com.geekalliance.taurus.base.api.system.entity.Message;
import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * MessageMapper
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Mapper
public interface MessageMapper extends RdbBaseMapper<Message> {

}