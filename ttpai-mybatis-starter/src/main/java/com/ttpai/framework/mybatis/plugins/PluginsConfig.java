package com.ttpai.framework.mybatis.plugins;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

public class PluginsConfig {

    @AutoConfigureBefore(MybatisAutoConfiguration.class)
    @AutoConfigureAfter(DataSourceAutoConfiguration.class)
    @ConditionalOnClass(name = "com.ttpai.framework.mybatis.plugin.datasource.MyBatisDatasourcePlugin")
    public static class Datasource {

        @Bean
        public DatasourcePluginConfig datasourcePluginConfig() {
            return new DatasourcePluginConfig();
        }

    }

}
