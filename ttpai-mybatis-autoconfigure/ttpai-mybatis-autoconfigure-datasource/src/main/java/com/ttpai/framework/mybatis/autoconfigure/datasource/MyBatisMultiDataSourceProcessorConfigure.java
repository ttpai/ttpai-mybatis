package com.ttpai.framework.mybatis.autoconfigure.datasource;

import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInit;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * @see MapperScannerConfigurer#postProcessBeanDefinitionRegistry
 */
@Configuration
@Import(MyBatisSqlSessionFactoryInit.class)
public class MyBatisMultiDataSourceProcessorConfigure implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    /**
     * DataSource Bean Name 符合以下前缀规范，支持自定义配置
     */
    public static final String[] AUTO_PREFIX = new String[]{
            "jade.dataSource.",  // 兼容 Rose
            "mybatis.dataSource.",  // 自定义前缀
            "ttpai.mybatis.dataSource." // 自定义前缀
    };

    public static final String MAPPING_BEAN_NAME = //
            MyBatisMultiDataSourceProcessorConfigure.class.getSimpleName() + ".dataSourcePackageMappings";

    private ApplicationContext applicationContext;

    private BeanDefinitionRegistry registry;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected String defaultPackageName() {
        // FIXME 单数据源逻辑处理（如 Spring 入口类）

        return "com.ttpai";
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Set<String> dataSourceNames = filterDataSourceNames(beanFactory);

        // 没有 DataSource
        if (dataSourceNames.isEmpty()) {
            return;
        }

        // dataSourceName <-> packageName
        Map<String, String> dataSourcePackageMappings = findDataSourceMappings(dataSourceNames);

        // 先查找子包，再查找父包
        ArrayList<String> packages = new ArrayList<>(dataSourcePackageMappings.values());
        Collections.sort(packages);
        Collections.reverse(packages);

        // FIXME 多个数据源对应用一个包，抛出异常，并提示原因

        // 扫包
        packages.forEach(this::scan);

        // 注册 dataSource <-> packageName 的映射关系
        beanFactory.registerSingleton(MAPPING_BEAN_NAME, dataSourcePackageMappings);
    }


    /**
     * 查找类型是 DataSource 的 Bean Name
     */
    private Set<String> filterDataSourceNames(ConfigurableListableBeanFactory beanFactory) {
        Set<String> dataSourceBeanName = new HashSet<>();

        String[] definitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : definitionNames) {
            Class<?> beanType = beanFactory.getType(beanName);

            // 不是 DataSource，不处理
            if (null == beanType || !DataSource.class.isAssignableFrom(beanType)) {
                continue;
            }

            // 记录 DataSource Name
            dataSourceBeanName.add(beanName);
        }

        return dataSourceBeanName;
    }

    /**
     * @return 0 是 没有前缀; >0 前缀索引截止位置
     */
    protected int hasAutoPrefix(String beanName) {
        for (String prefix : AUTO_PREFIX) {
            if (beanName.startsWith(prefix)) {
                return prefix.length();
            }
        }

        // 没有找到前缀
        return 0;
    }

    /**
     * 多数据源的情况
     */
    private Map<String, String> findDataSourceMappings(Set<String> dataSourceNames) {
        Map<String, String> beanPackageMappings = new HashMap<>();

        for (String dataSourceName : dataSourceNames) {
            int prefixIndex = hasAutoPrefix(dataSourceName);

            // 默认扫包
            String packageName = this.defaultPackageName();

            // 没有前缀
            if (prefixIndex <= 0) {
                // FIXME 从配置文件，或 其他地方找映射关系
                String foundPackage = this.findDataSourcePackage(dataSourceName);
                // FIXME 打印警告日志
                packageName = StringUtils.isEmpty(foundPackage) ? packageName : foundPackage;
            } else {
                String foundPackage = dataSourceName.substring(prefixIndex);
                // FIXME 打印警告日志
                packageName = StringUtils.isEmpty(foundPackage) ? packageName : foundPackage;
            }

            beanPackageMappings.put(dataSourceName, packageName);
        }

        return beanPackageMappings;
    }

    /**
     * FIXME 找到 DataSource 对应的 package
     */
    protected String findDataSourcePackage(String beanName) {
        return null;
    }


    /**
     * 其他自定义配置
     * TODO 覆盖该方法或者重写 ClassPathMapperScanner，扩充其他过滤规则，如 自定义注解 等
     * <p>
     * // TODO 扩充其他自定义配置
     */
    protected void scan(String packages) {
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
        scanner.setAnnotationClass(Mapper.class);
        scanner.setResourceLoader(applicationContext);
        scanner.setSqlSessionFactoryBeanName("SqlSessionFactory." + packages);
        scanner.registerFilters();
        scanner.scan(packages);
    }
}
