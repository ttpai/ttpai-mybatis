package com.ttpai.framework.mybatis.plugins;

import com.ttpai.framework.mybatis.plugin.datasource.MyBatisDatasourcePlugin;
import com.ttpai.framework.mybatis.plugin.datasource.support.RoutingDataSource;
import com.ttpai.framework.mybatis.plugin.datasource.support.SqlSessionFactoryPostProcessor;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 多数据源支持
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
@AutoConfigureBefore(MybatisAutoConfiguration.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ConditionalOnClass(name = "com.ttpai.framework.mybatis.plugin.datasource.MyBatisDatasourcePlugin")
public class DatasourcePluginConfig {

    /**
     * 数据源插件
     */
    @Bean
    public MyBatisDatasourcePlugin routingDataSourcePlugin() {
        return new MyBatisDatasourcePlugin();
    }

    /**
     * 数据源路由
     */
    @Primary
    @Bean("routingDataSource")
    public RoutingDataSource routingDataSource() {
        return new RoutingDataSource();
    }

    @Bean
    public SqlSessionFactoryPostProcessor sqlSessionFactoryPostProcessor() {
        return new SqlSessionFactoryPostProcessor();
    }
}
