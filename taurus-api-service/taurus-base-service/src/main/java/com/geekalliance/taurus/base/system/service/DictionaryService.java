package com.geekalliance.taurus.base.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.geekalliance.taurus.base.api.system.entity.Dictionary;
import com.geekalliance.taurus.base.api.system.exception.DictionaryErrorType;
import com.geekalliance.taurus.base.api.system.params.AddDictionaryParam;
import com.geekalliance.taurus.base.api.system.params.QueryDictionaryParam;
import com.geekalliance.taurus.base.api.system.params.UpdateDictionaryParam;
import com.geekalliance.taurus.base.api.system.vo.DictionaryVO;
import com.geekalliance.taurus.base.system.mapper.DictionaryMapper;
import com.geekalliance.taurus.core.exception.InternalException;
import com.geekalliance.taurus.core.params.CommonDeleteParam;
import com.geekalliance.taurus.rdb.service.RdbService;
import com.geekalliance.taurus.toolkit.enums.CommonEnum;
import com.geekalliance.taurus.toolkit.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public List<DictionaryVO> getDictionaries(QueryDictionaryParam queryParam) {
        if (StringUtils.isBlank(queryParam.getTypeId()) && StringUtils.isBlank(queryParam.getTypeCode())) {
            throw new InternalException(DictionaryErrorType.TYPE_ID_OR_CODE_CANNOT_BE_EMPTY_AT_THE_SAME_TIME);
        }
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryParam.getTypeId())) {
            queryWrapper.eq("type_id", queryParam.getTypeId());
        }
        if (StringUtils.isNotEmpty(queryParam.getTypeCode())) {
            queryWrapper.eq("type_code", queryParam.getTypeCode());
        }
        queryWrapper.eq("delete_flag", CommonEnum.NO);
        List<Dictionary> list = this.list(queryWrapper);
        return beanGenerator.convert(list, DictionaryVO.class);
    }

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
