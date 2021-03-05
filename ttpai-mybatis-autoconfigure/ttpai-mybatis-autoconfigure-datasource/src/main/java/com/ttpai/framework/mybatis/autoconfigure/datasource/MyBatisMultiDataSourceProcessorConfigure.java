package com.ttpai.framework.mybatis.autoconfigure.datasource;

import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInit;
import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInitEvent;
import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInitEventListener;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
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

    public static final String MAPPING_BEAN_NAME = //
            MyBatisMultiDataSourceProcessorConfigure.class.getSimpleName() + ".packageDataSourceMappings";

    private ApplicationContext applicationContext;

    private BeanDefinitionRegistry registry;

    /**
     * 默认值是
     *
     * @see #findDefaultPackages(BeanFactory)
     * @see AutoConfigurationPackages#get(BeanFactory)
     */
    private List<String> defaultPackageNames;

    // TODO 增加配置提示
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
        // 初始化 默认包
        this.defaultPackageNames = this.findDefaultPackages(beanFactory);

        // 查找 DataSource BeanName
        Set<String> dataSourceNames = this.filterDataSourceNames(beanFactory);

        // 没有 DataSource
        if (dataSourceNames.isEmpty()) {
            return;
        }

        // 包名对应 DataSource 名称的关系： packageName <-> dataSourceName
        Map<String, String> packageDataSourceMappings = this.findDataSourceMappings(dataSourceNames);

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
     * 获取 springboot 扫描的 base package
     */
    protected List<String> findDefaultPackages(BeanFactory beanFactory) {
        return AutoConfigurationPackages.get(beanFactory);
    }

    /**
     * 查找类型是 DataSource 的 BeanName
     */
    protected Set<String> filterDataSourceNames(ConfigurableListableBeanFactory beanFactory) {
        Set<String> dataSourceBeanName = new HashSet<>();

        String[] definitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : definitionNames) {
            Class<?> beanType = beanFactory.getType(beanName);

            // 不是 DataSource，不处理
            if (null == beanType || !DataSource.class.isAssignableFrom(beanType)) {
                continue;
            }

            // 排除 抽象的 DataSource，如 xml 中配置的 parent DataSource
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.isAbstract() || beanDefinition.isPrototype()) {
                continue;
            }

            // FIXME 会不会扫描到 tomcat 的 datasource，是否需要处理，为什么

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
     * 多数据源的情况，对多数据源进行包的映射
     * <p>
     * FIXME package 重复配置的问题 和 逻辑调整
     */
    protected Map<String, String> findDataSourceMappings(Set<String> dataSourceNames) {
        Map<String, String> packageBeanMappings = new HashMap<>();

        // 所有配置的 packages
        List<String> totalPackages = new ArrayList<>();

        for (String dataSourceName : dataSourceNames) {
            // 如果数据源名称符合规范，则使用约定格式的 包名->数据源的映射
            int prefixIndex = hasAutoPrefix(dataSourceName);
            if (prefixIndex > 0) {
                String foundPackage = dataSourceName.substring(prefixIndex);

                packageBeanMappings.put(foundPackage, dataSourceName);
                totalPackages.add(foundPackage);
            }

            // 查找用户自定义配置的包名映射
            List<String> packages = this.findDataSourcePackage(dataSourceName);
            if (!packages.isEmpty()) {
                for (String pkg : packages) {
                    packageBeanMappings.put(pkg, dataSourceName);
                    totalPackages.add(pkg);
                }
            }

            // 如果是单一数据源并且存在 SpringBoot 默认包名，则加入映射关系
            if (1 == dataSourceNames.size() && !defaultPackageNames.isEmpty()) {
                for (String defaultPackageName : defaultPackageNames) {
                    packageBeanMappings.put(defaultPackageName, dataSourceName);
                }
                totalPackages.addAll(defaultPackageNames);
            }
        }

        // 配置不规范告警 存在多个数据源对应同一个包的情况
        if (packageBeanMappings.keySet().size() != totalPackages.size()) {
            throw new IllegalStateException("数据源配置信息有误,多个数据源不能映射到同一个的包名上（同一个包下不能使用多个数据源）,请检查数据源相关配置");
        }

        return packageBeanMappings;
    }

    /**
     * 找到 DataSource 对应的 packages
     * <p>
     * 1. 先读取配置文件里配置的映射关系
     * <p>
     * 2. 读取bean上面的别名，判断别名是否符合规范
     */
    protected List<String> findDataSourcePackage(String dataSourceBeanName) {
        List<String> packages = new ArrayList<>();
        // 配置文件读取
        String packageNames = applicationContext.getEnvironment().getProperty(CONFIG_PREFIX + dataSourceBeanName);
        if (packageNames != null) {
            // TODO
            String[] split = packageNames.split(",");
            packages.addAll(Arrays.asList(split));
        }
        // 别名读取
        String[] aliases = applicationContext.getAliases(dataSourceBeanName);
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
     * 这里扩充其他自定义扫描规则
     */
    protected void scan(String packages) {
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
        scanner.setAnnotationClass(Mapper.class);
        scanner.setResourceLoader(applicationContext);
        scanner.setSqlSessionFactoryBeanName(SqlSessionFactory.class.getSimpleName() + "." + packages);
        scanner.registerFilters();
        scanner.scan(packages);
    }

}
