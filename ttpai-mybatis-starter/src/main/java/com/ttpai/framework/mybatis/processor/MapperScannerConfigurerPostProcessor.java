package com.ttpai.framework.mybatis.processor;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 自定义 @MapperScan 行为， Mapper 上必须增加 @Mapper 注解
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/8
 * @see org.mybatis.spring.annotation.MapperScan
 * @see org.mybatis.spring.annotation.MapperScannerRegistrar
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer
 */
public class MapperScannerConfigurerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MapperScannerConfigurer) {
            MapperScannerConfigurer configurer = (MapperScannerConfigurer) bean;
            configurer.setAnnotationClass(Mapper.class);
        }
        return bean;
    }
}
