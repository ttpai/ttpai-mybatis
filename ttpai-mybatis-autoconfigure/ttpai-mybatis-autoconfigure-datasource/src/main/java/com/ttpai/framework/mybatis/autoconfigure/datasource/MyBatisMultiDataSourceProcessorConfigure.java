package com.ttpai.framework.mybatis.autoconfigure.datasource;

import com.ttpai.framework.mybatis.autoconfigure.common.condition.ConditionalOnBeanCount;
import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInit;
import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInitEvent;
import com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInitEventListener;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * @see MapperScannerConfigurer#postProcessBeanDefinitionRegistry
 */
@AutoConfigureAfter(value = {DataSourceAutoConfiguration.class})
@ConditionalOnBeanCount(type = {DataSource.class}, count = ConditionalOnBeanCount.Count.MULTI)
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
     *
     * @param beanFactory Spring 容器
     * @return package 列表
     */
    protected List<String> findDefaultPackages(BeanFactory beanFactory) {
        return AutoConfigurationPackages.get(beanFactory);
    }

    /**
     * 查找类型是 DataSource 的 BeanName
     *
     * @param beanFactory Spring 容器
     * @return DataSource 的 BeanName 列表
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

            // 记录 DataSource Name
            dataSourceBeanName.add(beanName);
        }

        return dataSourceBeanName;
    }

    /**
     * @param beanName Bean 的名称
     * @return 0 是 没有前缀; 大于 0 前缀索引截止位置
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
     *
     * @param dataSourceNames DataSource Bean 名称
     * @return package 和 beanName 的映射
     */
    protected Map<String, String> findDataSourceMappings(Set<String> dataSourceNames) {
        Map<String, String> packageBeanMappings = new HashMap<>();
        // 如果是单一数据源并且存在 SpringBoot 默认包名，则加入映射关系
        if (1 == dataSourceNames.size() && !defaultPackageNames.isEmpty()) {
            for (String defaultPackageName : defaultPackageNames) {
                packageBeanMappings.put(defaultPackageName, dataSourceNames.iterator().next());
            }
        }
        for (String dataSourceName : dataSourceNames) {
            // 如果数据源名称符合规范，则使用约定格式的 包名->数据源的映射
            int prefixIndex = hasAutoPrefix(dataSourceName);
            if (prefixIndex > 0) {
                String foundPackage = dataSourceName.substring(prefixIndex);
                checkAndCacheMapping(packageBeanMappings, foundPackage, dataSourceName);
            }
            // 查找用户自定义配置的包名映射
            List<String> packages = this.findDataSourcePackage(dataSourceName);
            if (!packages.isEmpty()) {
                for (String pkg : packages) {
                    checkAndCacheMapping(packageBeanMappings, pkg, dataSourceName);
                }
            }
        }
        return packageBeanMappings;
    }

    /**
     * 检查是否有重复配置
     * 没有重复配置就放入映射关系
     *
     * @param packageBeanMappings 映射关系map
     * @param packageName         包名
     * @param dataSourceName      数据源名
     */
    private void checkAndCacheMapping(Map<String, String> packageBeanMappings, String packageName,
                                      String dataSourceName) {
        // 配置不规范告警 存在多个数据源对应同一个包的情况
        String existedDataSource = packageBeanMappings.get(packageName);
        if (existedDataSource != null && !existedDataSource.equals(dataSourceName)) {
            throw new IllegalStateException("数据源配置信息有误,多个数据源不能映射到同一个的包名上【package: " + packageName + "】,请检查数据源相关配置");
        }
        packageBeanMappings.put(packageName, dataSourceName);
    }

    /**
     * 找到 DataSource 对应的 packages
     * <p>
     * 1. 先读取配置文件里配置的映射关系
     * <p>
     * 2. 读取bean上面的别名，判断别名是否符合规范
     *
     * @param dataSourceBeanName DataSource Bean 名称
     * @return DataSource 对应的 packages
     */
    protected List<String> findDataSourcePackage(String dataSourceBeanName) {
        List<String> packages = new ArrayList<>();
        // 配置文件读取
        String packageNames = applicationContext.getEnvironment().getProperty(CONFIG_PREFIX + dataSourceBeanName);
        if (packageNames != null) {
            packages.addAll(StringUtils.commaDelimitedListToSet(packageNames));
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
     *
     * @param packages package 名
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
