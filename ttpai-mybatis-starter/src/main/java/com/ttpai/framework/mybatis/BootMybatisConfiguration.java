package com.ttpai.framework.mybatis;

import com.ttpai.framework.mybatis.config.MybatisConfigurationCustomer;
import com.ttpai.framework.mybatis.datasource.RoutingDataSource;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.util.Map;

import javax.sql.DataSource;

/**
 * Springboot 项目的自动装配类 对Mybatis原生的autoconfig做增强
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
@Configuration
@AutoConfigureBefore(MybatisAutoConfiguration.class)
@ConditionalOnBean(DataSource.class)
public class BootMybatisConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
