package com.geekalliance.taurus.core.utils;

import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;


/**
 * 通用DTO生成器
 * @author maxuqiang
 */
@Lazy(value = false)
@Configuration
public class BeanGeneratorWrapper implements BeanGenerator {

  @Autowired
  private Mapper beanMapper;

  @Bean
  public DozerBeanMapperFactoryBean mapper() {
    ClassPathResource classPathResource = new ClassPathResource("dozerJdk8Converters.xml");
    Resource[] mappingFiles = {classPathResource};
    DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean = new DozerBeanMapperFactoryBean();
    dozerBeanMapperFactoryBean.setMappingFiles(mappingFiles);
    return dozerBeanMapperFactoryBean;
  }

  @Override
  public <T extends Serializable, S extends Serializable> T convert(S s, Class<T> clz) {
    if(s == null){
      return null;
    }
    return beanMapper.map(s, clz);
  }

  @Override
  public <T extends Serializable, S extends Serializable> Collection<T> convert(Collection<S> s,
                                                                                Class<T> clz) {
    if (s == null) {
      return null;
    }

    Collection<T> retList = null;
    if (Set.class.isAssignableFrom(s.getClass())) {
      retList = new HashSet<T>();
    }

    if (List.class.isAssignableFrom(s.getClass())) {
      retList = new ArrayList<T>();
    }

    if (retList == null) {
      throw new RuntimeException("无法生成容器类，类型异常：" + s.getClass());
    }

    Iterator<S> it = s.iterator();
    while (it.hasNext()) {
      Serializable srcOV = it.next();
      retList.add(convert(srcOV, clz));
    }

    return retList;
  }

  @Override
  public <T extends Serializable, S extends Serializable> List<T> convert(List<S> s, Class<T> clz) {
    if (s == null) {
      return null;
    }
    List<T> retList = new ArrayList<T>();
    Iterator<S> it = s.iterator();
    while (it.hasNext()) {
      Serializable srcOV = it.next();
      retList.add(convert(srcOV, clz));
    }

    return retList;
  }

  @Override
  public <T extends Serializable, S extends Serializable> Set<T> convert(Set<S> s, Class<T> clz) {
    if (s == null) {
      return null;
    }
    Set<T> retList = new HashSet<T>();
    Iterator<S> it = s.iterator();
    while (it.hasNext()) {
      Serializable srcOV = it.next();
      retList.add(convert(srcOV, clz));
    }

    return retList;
  }

  @Override
  public <T extends Serializable, S extends Serializable> T[] convert(S[] s, Class<T> clz) {
    if (s == null) {
      return null;
    }

    @SuppressWarnings("unchecked")
    T[] arr = (T[]) Array.newInstance(clz, s.length);

    for (int i = 0; i < s.length; i++) {
      arr[i] = convert(s[i], clz);
    }

    return arr;
  }
}