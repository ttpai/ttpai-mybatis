package com.ttpai.framework.mybatis.autoconfigure.datasource.support;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.io.ResourceLoader;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import static com.ttpai.framework.mybatis.autoconfigure.datasource.MyBatisMultiDataSourceProcessorConfigure.MAPPING_BEAN_NAME;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class MyBatisSqlSessionFactoryInit implements InitializingBean, ServletContextInitializer, PriorityOrdered {

    @Resource
    private DefaultListableBeanFactory beanFactory;

    @Resource
    private MybatisProperties properties;

    @Resource
    private ObjectProvider<Interceptor[]> interceptorsProvider;

    @Resource
    private ObjectProvider<TypeHandler[]> typeHandlersProvider;

    @Resource
    private ObjectProvider<LanguageDriver[]> languageDriversProvider;

    @Resource
    private ResourceLoader resourceLoader;

    @Resource
    private ObjectProvider<DatabaseIdProvider> databaseIdProvider;

    @Resource
    private ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider;

    @Override
    @SuppressWarnings("all")
    public void afterPropertiesSet() throws Exception {
        Map<String, String> mappings = beanFactory.getBean(MAPPING_BEAN_NAME, Map.class);
        MybatisAutoConfiguration delegation;
        for (Map.Entry<String, String> entry : mappings.entrySet()) {
            DataSource dataSource = beanFactory.getBean(entry.getValue(), DataSource.class);
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            BeanUtils.copyProperties(properties.getConfiguration(), configuration);
            properties.setConfiguration(configuration);
            delegation = new MybatisAutoConfiguration(
                    properties,
                    interceptorsProvider,
                    typeHandlersProvider,
                    languageDriversProvider,
                    resourceLoader,
                    databaseIdProvider,
                    configurationCustomizersProvider);
            delegation.afterPropertiesSet();
            SqlSessionFactory sqlSessionFactory = delegation.sqlSessionFactory(dataSource);
            beanFactory.registerSingleton("SqlSessionFactory." + entry.getKey(), sqlSessionFactory);
        }
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
