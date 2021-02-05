package com.ttpai.framework.mybatis;

import com.ttpai.framework.mybatis.config.MybatisConfigurationCustomer;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
@Configuration
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class TtpaiMybatisAutoConfiguration {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomer(Environment environment) {
        return new MybatisConfigurationCustomer(environment);
    }
}
