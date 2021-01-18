package com.geekalliance.taurus.base.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.geekalliance.taurus.base.api.system.entity.Dictionary;
import com.geekalliance.taurus.base.api.system.entity.DictionaryType;
import com.geekalliance.taurus.base.api.system.exception.DictionaryErrorType;
import com.geekalliance.taurus.base.api.system.params.QueryDictionaryParam;
import com.geekalliance.taurus.base.api.system.params.QueryDictionaryTypeParam;
import com.geekalliance.taurus.base.api.system.vo.DictionaryTypeVO;
import com.geekalliance.taurus.base.api.system.vo.DictionaryVO;
import com.geekalliance.taurus.base.system.mapper.DictionaryMapper;
import com.geekalliance.taurus.base.system.mapper.DictionaryTypeMapper;
import com.geekalliance.taurus.core.exception.InternalException;
import com.geekalliance.taurus.rdb.service.RdbService;
import com.geekalliance.taurus.toolkit.enums.CommonEnum;
import com.geekalliance.taurus.toolkit.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    public List<DictionaryTypeVO> getDictionaryTypes(QueryDictionaryTypeParam queryParam) {

        QueryWrapper<DictionaryType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("application",queryParam.getApplication());
        if(StringUtils.isNotEmpty(queryParam.getModule())){
            queryWrapper.eq("module",queryParam.getModule());
        }
        if(StringUtils.isNotEmpty(queryParam.getCode())){
            queryWrapper.eq("code",queryParam.getCode());
        }
        queryWrapper.eq("delete_flag", CommonEnum.NO);
        List<DictionaryType> result = this.list(queryWrapper);
        return beanGenerator.convert(result,DictionaryTypeVO.class);

    }


}
