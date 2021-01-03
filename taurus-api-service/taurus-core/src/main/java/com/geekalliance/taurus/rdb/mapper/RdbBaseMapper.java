package com.geekalliance.taurus.rdb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @Description
 * @Date 2019/12/27
 * @Author maxuqiang
 **/

public interface RdbBaseMapper<T> extends BaseMapper<T> {
    T getByIdIgnoreLogicDelete(Serializable idVal);

    int updateByIdIgnoreLogicDelete(@Param(Constants.ENTITY) T entity);

    int deleteByIdIgnoreLogicDelete(Serializable id);

    int deleteByMapIgnoreLogicDelete(@Param("cm") Map<String, Object> columnMap);

    int deleteIgnoreLogicDelete(@Param("ew") Wrapper<T> wrapper);

    int deleteBatchIdsIgnoreLogicDelete(@Param("coll") Collection<? extends Serializable> idList);
}
