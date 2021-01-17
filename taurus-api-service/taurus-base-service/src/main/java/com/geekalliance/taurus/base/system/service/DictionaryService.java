package com.geekalliance.taurus.base.system.service;

import com.geekalliance.taurus.core.params.CommonDeleteParam;
import com.geekalliance.taurus.base.api.system.params.AddDictionaryParam;
import com.geekalliance.taurus.base.api.system.params.UpdateDictionaryParam;
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

    public boolean add(AddDictionaryParam saveParam) {
        return false;
    }

    public boolean delete(CommonDeleteParam deleteParam) {
        return false;
    }

    public boolean update(UpdateDictionaryParam updateParam) {
        return false;
    }
}
