package com.ttpai.framework.mybatis.processor;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/8
 */

@Component
public class MapperScannerConfigurerPostProcessor implements BeanPostProcessor {

    @Override public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MapperScannerConfigurer) {
            MapperScannerConfigurer configurer = (MapperScannerConfigurer) bean;
            configurer.setAnnotationClass(Mapper.class);
        }
        return bean;
    }
}
