package com.ttpai.framework.mybatis;

import com.ttpai.framework.mybatis.config.MybatisConfigurationCustomer;
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
 * Springboot 项目的自动装配类 对Mybatis原生的autoconfig做增强
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
@org.springframework.context.annotation.Configuration
@Import(BootMybatisConfiguration.PostProcessorInitializer.class)
public class BootMybatisConfiguration {

    public static class PostProcessorInitializer implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

        @Override
        public void setBeanFactory(BeanFactory beanFactory) {
            // 加上后置处理器
            ((ConfigurableListableBeanFactory) beanFactory)
                    .addBeanPostProcessor(new MapperScannerConfigurerPostProcessor());
        }

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                            BeanDefinitionRegistry registry) {

        }
    }

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomer(Environment environment) {
        return new MybatisConfigurationCustomer(environment);
    }

    // @Bean
    // @Primary
    // public DataSource routingDataSource() {
    // Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
    // return new RoutingDataSource(dataSourceMap);
    // }
}
