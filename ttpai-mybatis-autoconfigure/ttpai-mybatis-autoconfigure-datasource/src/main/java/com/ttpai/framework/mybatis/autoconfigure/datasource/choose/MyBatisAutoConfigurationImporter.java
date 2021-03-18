package com.ttpai.framework.mybatis.autoconfigure.datasource.choose;

import com.ttpai.framework.mybatis.autoconfigure.common.ConditionalOnBeanCount;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 重新启用 MybatisAutoConfiguration，生效条件是 DataSource 的实例只有一个
 *
 * @see MyBatisAutoConfigurationExcludeFilter 排除 MybatisAutoConfiguration
 */
@ConditionalOnBeanCount(type = DataSource.class, count = ConditionalOnBeanCount.Count.SINGLE)
@Import(MybatisAutoConfiguration.class)
public class MyBatisAutoConfigurationImporter {

}
