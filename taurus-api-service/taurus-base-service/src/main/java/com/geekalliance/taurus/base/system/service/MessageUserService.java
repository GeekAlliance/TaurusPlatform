package com.geekalliance.taurus.base.system.service;

import com.geekalliance.taurus.base.api.system.entity.MessageUser;
import com.geekalliance.taurus.base.system.mapper.MessageUserMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * NoticeUserService
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Service
public class MessageUserService extends RdbService<MessageUserMapper, MessageUser> {

    @Resource
    private MessageUserMapper messageUserMapper;

}
