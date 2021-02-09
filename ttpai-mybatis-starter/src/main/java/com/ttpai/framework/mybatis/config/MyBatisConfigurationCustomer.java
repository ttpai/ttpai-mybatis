package com.ttpai.framework.mybatis.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * MyBatis 修改默认配置
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 * @see DefaultMyBatisConfig
 */
public class MyBatisConfigurationCustomer implements ConfigurationCustomizer {

    private final Environment environment;

    public MyBatisConfigurationCustomer(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void customize(Configuration configuration) {
        for (DefaultMyBatisConfig value : DefaultMyBatisConfig.values()) {
            String property = environment.getProperty(value.getKey());
            if (StringUtils.isEmpty(property)) {
                value.setConfig(configuration);
            }
        }
    }
}
