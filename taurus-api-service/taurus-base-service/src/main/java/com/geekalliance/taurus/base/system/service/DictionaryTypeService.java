package com.geekalliance.taurus.base.system.service;

import com.geekalliance.taurus.base.api.system.params.QueryDictionaryParam;
import com.geekalliance.taurus.base.api.system.params.QueryDictionaryTypeParam;
import com.geekalliance.taurus.base.api.system.vo.DictionaryTypeVO;
import com.geekalliance.taurus.base.api.system.vo.DictionaryVO;
import com.geekalliance.taurus.base.system.mapper.DictionaryMapper;
import com.geekalliance.taurus.base.system.mapper.DictionaryTypeMapper;
import com.geekalliance.taurus.rdb.service.RdbService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.geekalliance.taurus.base.api.system.entity.DictionaryType;

import java.util.List;

/**
 *DictionaryTypeService
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 *
 */
@Service
public class DictionaryTypeService extends RdbService<DictionaryTypeMapper, DictionaryType> {

    @Resource
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Resource
    private DictionaryMapper dictionaryMapper;

    public List<DictionaryTypeVO> getDictionaryTypes(QueryDictionaryTypeParam queryParam) {
        return null;
    }

    public List<DictionaryVO> getDictionaries(QueryDictionaryParam queryParam) {
        return null;
    }
}
