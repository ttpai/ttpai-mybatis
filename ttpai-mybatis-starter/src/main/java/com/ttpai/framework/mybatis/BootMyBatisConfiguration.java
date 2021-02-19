package com.ttpai.framework.mybatis;

import com.ttpai.framework.mybatis.config.MyBatisConfigurationCustomer;
import com.ttpai.framework.mybatis.datasource.RoutingDataSource;
import com.ttpai.framework.mybatis.datasource.RoutingDataSourcePlugin;
import com.ttpai.framework.mybatis.processor.MapperScannerConfigurerPostProcessor;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Springboot 项目的自动装配类 对Mybatis原生的autoconfig做增强
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
@Import(BootMyBatisConfiguration.MyBatisFunctionEnhanceRegistrar.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class BootMyBatisConfiguration {

    @Bean("routingDataSource")
    @Primary
    public RoutingDataSource routingDataSource() {
        return new RoutingDataSource();
    }

    /**
     *
     */
    public static class MyBatisFunctionEnhanceRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

        private ConfigurableListableBeanFactory beanFactory;

        @Override
        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
            // nothing
        }

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
    }

    /**
     * 默认配置处理
     */
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomer(Environment environment) {
        return new MyBatisConfigurationCustomer(environment);
    }

    @Bean
    public RoutingDataSourcePlugin routingDataSourcePlugin() {
        return new RoutingDataSourcePlugin();
    }
}
