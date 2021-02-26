package com.ttpai.framework.mybatis.autoconfigure.datasource.support;

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

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @see MapperScannerConfigurer#postProcessBeanDefinitionRegistry
 */
@Configuration
public class MyBatisBeanFactoryPostProcessor implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    /**
     * DataSource Bean Name 符合以下前缀规范，支持自定义配置
     */
    public static final String[] AUTO_PREFIX = new String[]{
            "jade.dataSource.",  // 兼容 Rose
            "mybatis.dataSource.",  // 自定义前缀
            "ttpai.mybatis.dataSource." // // 自定义前缀
    };

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


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] definitionNames = beanFactory.getBeanDefinitionNames();

        for (String beanName : definitionNames) {
            Class<?> beanType = beanFactory.getType(beanName);

            int datasourceStatus = isDatasource(beanType, beanName);
            // 不是 DataSource，不处理
            if (datasourceStatus < 0) {
                continue;
            }
            if (datasourceStatus == 0) {
                // TODO 没有前缀，根据配置获取映射
            }
            if (datasourceStatus > 0) {
                String packageName = beanName.substring(datasourceStatus);
                this.scan(packageName);
            }
            // TODO 单个 DataSource 的情况
        }
    }

    /**
     * @return -1:不是 DataSource; 0 是 DataSource; >0 前缀索引截止位置
     */
    protected int isDatasource(Class<?> beanType, String beanName) {
        // DataSource 不是是 beanType 的父类
        if (!DataSource.class.isAssignableFrom(beanType)) {
            return -1;
        }

        // 找到了前缀
        for (String prefix : AUTO_PREFIX) {
            if (beanName.startsWith(prefix)) {
                return prefix.length();
            }
        }

        // 没有找到前缀
        return 0;
    }

    /**
     * 其他自定义配置
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


    public static void main(String[] args) {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("xyz.kail.demo.mybatis.spring.boot.mapper");
        objects.add("xyz.kail.demo.mybatis.spring.boot.mapper.sys");
        objects.add("xyz.kail.demo.mybatis.spring.boot");

        Collections.sort(objects);
        Collections.reverse(objects);

        // 先查找子包，再查找父包
        System.out.println(objects);

    }

}
