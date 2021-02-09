package com.ttpai.framework.mybatis;

import com.ttpai.framework.mybatis.config.MybatisConfigurationCustomer;
import com.ttpai.framework.mybatis.datasource.RoutingDataSource;
import com.ttpai.framework.mybatis.datasource.RoutingDataSourcePlugin;
import com.ttpai.framework.mybatis.processor.MapperScannerConfigurerPostProcessor;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

import javax.sql.DataSource;

/**
 * Springboot 项目的自动装配类 对Mybatis原生的autoconfig做增强
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
@org.springframework.context.annotation.Configuration
@Import(BootMybatisConfiguration.MybatisFunctionEnhanceRegistrar.class)
public class BootMybatisConfiguration {

    public static class MybatisFunctionEnhanceRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

        private ConfigurableListableBeanFactory beanFactory;

        @Override
        public void setBeanFactory(BeanFactory beanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
            addPostProcessor();
        }

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                            BeanDefinitionRegistry registry) {
            // bean 定义的构建器
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RoutingDataSource.class);
            Map<String, DataSource> dataSourceMap = beanFactory.getBeansOfType(DataSource.class);
            builder.addConstructorArgValue(dataSourceMap);
            // 获取bean 定义
            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            // 设置为主
            beanDefinition.setPrimary(true);
            registry.registerBeanDefinition(RoutingDataSource.class.getName(), beanDefinition);
        }

        /**
         * 加上后置处理器
         */
        private void addPostProcessor() {
            beanFactory.addBeanPostProcessor(new MapperScannerConfigurerPostProcessor());
        }
    }

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomer(Environment environment) {
        return new MybatisConfigurationCustomer(environment);
    }

    @Bean
    public RoutingDataSourcePlugin routingDataSourcePlugin() {
        return new RoutingDataSourcePlugin();
    }
}
