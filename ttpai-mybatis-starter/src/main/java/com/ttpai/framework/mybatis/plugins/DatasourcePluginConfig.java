package com.ttpai.framework.mybatis.plugins;

import com.ttpai.framework.mybatis.plugin.datasource.MyBatisDatasourcePlugin;
import com.ttpai.framework.mybatis.plugin.datasource.support.RoutingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Springboot 项目的自动装配类 对Mybatis原生的autoconfig做增强
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
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

}
