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
import org.springframework.context.annotation.Bean;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static com.ttpai.framework.mybatis.autoconfigure.datasource.MyBatisMultiDataSourceProcessorConfigure.MAPPING_BEAN_NAME;

/**
 * @see #onStartup(ServletContext)
 */
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

    /**
     * 早期 SqlSessionFactory 的名称，用于在 ConfigurationClassParser.parse 过程中，提前获取 Bean 的定义
     * <p>
     * 在真正初始化过程中会被移除
     */
    private static final String EARLY_SQL_SESSION_FACTORY = "SqlSessionFactory.EARLY";

    @Bean(name = EARLY_SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory() {
        return null;
    }

    @Override
    @SuppressWarnings("all")
    public void afterPropertiesSet() throws Exception {
        // 删除早期 Bean
        beanFactory.removeBeanDefinition(EARLY_SQL_SESSION_FACTORY);

        Map<String, String> mappings = beanFactory.getBean(MAPPING_BEAN_NAME, Map.class);

        MybatisAutoConfiguration delegation;

        for (Map.Entry<String, String> packageDatasource : mappings.entrySet()) {
            DataSource dataSource = beanFactory.getBean(packageDatasource.getValue(), DataSource.class);

            //
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            // 如果没有配置 configuration，这里会为 null，需要判断一下
            if (properties.getConfiguration() != null) {
                BeanUtils.copyProperties(properties.getConfiguration(), configuration);
            }
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

            beanFactory.registerSingleton(
                    SqlSessionFactory.class.getSimpleName() + "." + packageDatasource.getKey(), //
                    sqlSessionFactory //
            );
        }
    }

    /**
     * 为了把 Bean 的初始化提前到 AbstractApplicationContext#onRefresh() 的阶段
     * <p>
     * see AbstractApplicationContext#onRefresh()
     * see EmbeddedWebApplicationContext#createEmbeddedServletContainer()
     * see EmbeddedWebApplicationContext#getSelfInitializer()
     * see EmbeddedWebApplicationContext#selfInitialize(ServletContext)
     * see EmbeddedWebApplicationContext#getServletContextInitializerBeans()
     * see ServletContextInitializerBeans#ServletContextInitializerBeans(ListableBeanFactory)
     * see ServletContextInitializerBeans#addServletContextInitializerBeans(ListableBeanFactory)
     * see ServletContextInitializerBeans#getOrderedBeansOfType(ListableBeanFactory, Class)
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // getBean
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
