package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/9 14:38
 **/
@Configuration
@ConfigurationProperties(prefix = "ttpai.mybatis.generator.datasource")
public class DataSourceConfigJr extends DataSourceConfig {

    public DataSourceConfigJr() {
        super.setDbType(DbType.MYSQL);
        super.setDriverName("com.mysql.jdbc.Driver");
    }
}
