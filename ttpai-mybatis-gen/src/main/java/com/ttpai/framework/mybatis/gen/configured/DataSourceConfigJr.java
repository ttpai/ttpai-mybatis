package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据源配置，通过该配置，指定需要生成代码的具体数据库
 *
 * @author lilin.tan@ttpai.cn
 *         see
 *         {https://baomidou.com/config/generator-config.html#%E6%95%B0%E6%8D%AE%E6%BA%90-datasourceconfig-%E9%85%8D%E7%BD%AE}
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
