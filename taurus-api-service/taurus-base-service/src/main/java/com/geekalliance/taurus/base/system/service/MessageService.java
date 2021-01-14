package com.geekalliance.taurus.base.system.service;

import com.geekalliance.taurus.base.api.system.entity.Message;
import com.geekalliance.taurus.base.system.mapper.MessageMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * MessageService
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Service
public class MessageService extends RdbService<MessageMapper, Message> {

    @Resource
    private MessageMapper messageMapper;


}
