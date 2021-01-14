package com.geekalliance.taurus.base.system.service;

import com.geekalliance.taurus.base.api.system.entity.Dictionary;
import com.geekalliance.taurus.base.system.mapper.DictionaryMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * DictionaryService
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Service
public class DictionaryService extends RdbService<DictionaryMapper, Dictionary> {

    @Resource
    private DictionaryMapper dictionaryMapper;

}