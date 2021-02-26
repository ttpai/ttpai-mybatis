package com.ttpai.framework.mybatis.autoconfigure.datasource;

import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisBeanFactoryPostProcessor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

import static com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisBeanFactoryPostProcessor.AUTO_PREFIX;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@Import(MyBatisBeanFactoryPostProcessor.class)
public class MultiDatasourceConfigure implements InitializingBean {

    @Resource
    private DefaultListableBeanFactory beanFactory;

    private final MybatisAutoConfiguration delegation;

    public MultiDatasourceConfigure(MybatisProperties properties,
                                    ObjectProvider<Interceptor[]> interceptorsProvider,
                                    ObjectProvider<TypeHandler[]> typeHandlersProvider,
                                    ObjectProvider<LanguageDriver[]> languageDriversProvider,
                                    ResourceLoader resourceLoader,
                                    ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                    ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        this.delegation = new MybatisAutoConfiguration(
                properties,
                interceptorsProvider,
                typeHandlersProvider,
                languageDriversProvider,
                resourceLoader,
                databaseIdProvider,
                configurationCustomizersProvider
        );
        delegation.afterPropertiesSet();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] beanNamesForType = beanFactory.getBeanNamesForType(DataSource.class);

        for (String beanName : beanNamesForType) {
            for (String prefix : AUTO_PREFIX) {
                if (beanName.startsWith(prefix)) {
                    String packageName = beanName.substring(prefix.length());
                    DataSource dataSource = beanFactory.getBean(beanName, DataSource.class);
                    SqlSessionFactory sqlSessionFactory = delegation.sqlSessionFactory(dataSource);

                    beanFactory.registerSingleton("SqlSessionFactory." + packageName, sqlSessionFactory);
                    break;
                }

            }
        }
    }


}
