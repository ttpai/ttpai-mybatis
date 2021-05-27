package com.ttpai.framework.mybatis;

import com.ttpai.framework.mybatis.config.MyBatisConfigurationCustomer;
import com.ttpai.framework.mybatis.processor.MapperScannerConfigurerPostProcessor;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Springboot 项目的自动装配类 对 MyBatis 原生的 autoconfig 做增强
 *
 * @author zichao.zhang@ttpai.cn
 * @since 2021/2/5
 */
@Import({ //
        BootMyBatisConfiguration.MyBatisFunctionEnhance.class
})
public class BootMyBatisConfiguration {

    /**
     * MyBatis 功能增强
     */
    public static class MyBatisFunctionEnhance implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

        private ConfigurableListableBeanFactory beanFactory;

        @Override
        public void setBeanFactory(BeanFactory beanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
            addPostProcessor();
        }

        /**
         * 加上后置处理器
         */
        private void addPostProcessor() {
            // 自定义 @MapperScan 行为， Mapper 上必须增加 @Mapper 注解
            beanFactory.addBeanPostProcessor(new MapperScannerConfigurerPostProcessor());
        }

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                            BeanDefinitionRegistry registry) {
            // 生命周期使用
        }
    }

    /**
     * 默认配置处理
     */
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomer(Environment environment) {
        return new MyBatisConfigurationCustomer(environment);
    }

}
