package com.ttpai.framework.mybatis.autoconfigure.datasource;

import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInit;
import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInitEvent;
import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInitEventListener;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

/**
 * @see MapperScannerConfigurer#postProcessBeanDefinitionRegistry
 */
@Import({MyBatisSqlSessionFactoryInit.class, MyBatisSqlSessionFactoryInitEventListener.class})
public class MyBatisMultiDataSourceProcessorConfigure
        implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    /**
     * DataSource Bean Name 符合以下前缀规范，支持自定义配置
     */
    public static final String[] AUTO_PREFIX = new String[] {
            "jade.dataSource.",  // 兼容 Rose
            "mybatis.dataSource.",  // 自定义前缀
            "ttpai.mybatis.dataSource." // 自定义前缀
    };

    public static final String MAPPING_BEAN_NAME = MyBatisMultiDataSourceProcessorConfigure.class.getSimpleName()
            + ".packageDataSourceMappings";

    private ApplicationContext applicationContext;

    private BeanDefinitionRegistry registry;

    private String defaultPackageName;

    private static final String CONFIG_PREFIX = "ttpai.mybatis.datasource.mapping.";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 设置springboot扫描的base package
        List<String> defaultPackages = AutoConfigurationPackages.get(beanFactory);
        this.defaultPackageName = StringUtils.collectionToCommaDelimitedString(defaultPackages);

        Set<String> dataSourceNames = filterDataSourceNames(beanFactory);

        // 没有 DataSource
        if (dataSourceNames.isEmpty()) {
            return;
        }

        // 包名对应DataSource名称的关系 packageName <-> dataSourceName
        Map<String, String> packageDataSourceMappings = findDataSourceMappings(dataSourceNames);

        // 先查找子包，再查找父包
        ArrayList<String> packages = new ArrayList<>(packageDataSourceMappings.keySet());
        Collections.sort(packages);
        Collections.reverse(packages);
        // 扫包
        packages.forEach(this::scan);

        // 注册 dataSource <-> packageName 的映射关系
        beanFactory.registerSingleton(MAPPING_BEAN_NAME, packageDataSourceMappings);
        // 发布早期事件，在 getBean 之前初始化
        applicationContext.publishEvent(new MyBatisSqlSessionFactoryInitEvent(applicationContext));

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
        List<String> totalPackages = new ArrayList<>();
        for (String dataSourceName : dataSourceNames) {
            // 如果数据源名称符合规范，则使用约定格式的包名->数据源的映射
            int prefixIndex = hasAutoPrefix(dataSourceName);
            if (prefixIndex > 0) {
                String foundPackage = dataSourceName.substring(prefixIndex);
                beanPackageMappings.put(foundPackage, dataSourceName);
                totalPackages.add(foundPackage);
            }
            // 查找用户自定义配置的包名映射
            List<String> packages = this.findDataSourcePackage(dataSourceName);
            if (!packages.isEmpty()) {
                for (String pkg : packages) {
                    beanPackageMappings.put(pkg, dataSourceName);
                    totalPackages.add(pkg);
                }
            }
            // 如果是单一数据源并且存在SpringBoot默认包名，则加入映射关系
            if (1 == dataSourceNames.size() && !StringUtils.isEmpty(defaultPackageName)) {
                beanPackageMappings.put(defaultPackageName, dataSourceName);
                totalPackages.add(defaultPackageName);
            }
        }
        // 配置不规范告警 存在多个数据源对应同一个包的情况
        if (beanPackageMappings.keySet().size() != totalPackages.size()) {
            throw new IllegalStateException("数据源配置信息有误,多个数据源不能映射到单个的包名上,请检查数据源相关配置");
        }
        return beanPackageMappings;
    }

    /**
     * 找到 DataSource 对应的 packages 1. 先读取配置文件里配置的映射关系 2. 读取bean上面的别名，判断别名是否符合规范
     */
    protected List<String> findDataSourcePackage(String beanName) {
        List<String> packages = new ArrayList<>();
        // 配置文件读取
        String packageNames = applicationContext.getEnvironment().getProperty(CONFIG_PREFIX + beanName);
        if (packageNames != null) {
            String[] split = packageNames.split(",");
            packages.addAll(Arrays.asList(split));
        }
        // 别名读取
        String[] aliases = applicationContext.getAliases(beanName);
        if (aliases.length > 0) {
            for (String alias : aliases) {
                int prefixIndex = hasAutoPrefix(alias);
                if (prefixIndex > 0) {
                    packages.add(alias.substring(prefixIndex));
                }
            }
        }
        return packages;
    }

    /**
     * 扩充其他自定义配置
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
