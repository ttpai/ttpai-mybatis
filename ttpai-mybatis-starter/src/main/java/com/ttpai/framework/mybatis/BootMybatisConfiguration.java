package com.ttpai.framework.mybatis;

import com.ttpai.framework.mybatis.config.MybatisConfigurationCustomer;
import com.ttpai.framework.mybatis.processor.MapperScannerConfigurerPostProcessor;

import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Springboot 项目的自动装配类 对Mybatis原生的autoconfig做增强
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
@Configuration
@AutoConfigureBefore({MapperScannerRegistrar.class, MybatisAutoConfiguration.class})
@ConditionalOnBean(DataSource.class)
public class BootMybatisConfiguration implements ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;

    private ConfigurableListableBeanFactory beanFactory;

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomer(Environment environment) {
        return new MybatisConfigurationCustomer(environment);
    }

    //    @Bean
    //    @Primary
    //    public DataSource routingDataSource() {
    //        Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
    //        return new RoutingDataSource(dataSourceMap);
    //    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
            this.beanFactory.addBeanPostProcessor(new MapperScannerConfigurerPostProcessor());
        }
    }
}
