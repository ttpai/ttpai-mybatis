package com.ttpai.framework.mybatis.autoconfigure.datasource.choose;

import com.ttpai.framework.mybatis.autoconfigure.common.condition.ConditionalOnBeanCount;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 重新启用 MybatisAutoConfiguration，生效条件是 DataSource 的实例只有一个
 *
 * @author kail
 * @see MyBatisAutoConfigurationExclude 排除 MybatisAutoConfiguration
 */
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ConditionalOnBeanCount(type = DataSource.class, count = ConditionalOnBeanCount.Count.SINGLE)
@Import(MybatisAutoConfiguration.class)
public class MyBatisAutoConfigurationImporter {

}
