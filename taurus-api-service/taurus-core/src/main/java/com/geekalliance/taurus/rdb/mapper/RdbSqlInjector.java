package com.geekalliance.taurus.rdb.mapper;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.geekalliance.taurus.rdb.methods.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Date 2019/12/27
 * @Author maxuqiang
 **/
@Component
public class RdbSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new SelectIgnoreLogicDelete());
        methodList.add(new UpdateIgnoreLogicDelete());
        methodList.add(new DeleteBatchByIdsIgnoreLogicDelete());
        methodList.add(new DeleteByIdIgnoreLogicDelete());
        methodList.add(new DeleteByMapIgnoreLogicDelete());
        methodList.add(new DeleteIgnoreLogicDelete());
        return methodList;
    }
}
