package com.geekalliance.taurus.base.system.mapper;

import com.geekalliance.taurus.base.api.system.entity.DictionaryType;
import com.geekalliance.taurus.base.api.system.params.QueryDictionaryTypeParam;
import com.geekalliance.taurus.rdb.mapper.RdbBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * DictionaryTypeMapper
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 */
@Mapper
public interface DictionaryTypeMapper extends RdbBaseMapper<DictionaryType> {

    /**
     * 获取字典类型集合
     * @param queryParam 查询参数
     * @return 字典类型集合
     */
//    List<DictionaryType> getDictionaryTypes(@Param("param") QueryDictionaryTypeParam queryParam);
}