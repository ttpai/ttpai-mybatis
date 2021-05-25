package com.ttpai.framework.mybatis.autoconfigure.datasource.choose;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * 排除 MybatisAutoConfiguration
 *
 * @author kail
 * @see AutoConfigurationImportSelector
 */
public class MyBatisAutoConfigurationExclude implements SpringApplicationRunListener {

    private static final String EXCLUDE_KEY = "spring.autoconfigure.exclude";

    /**
     * 必须有的默认构造函数，
     */
    public MyBatisAutoConfigurationExclude(SpringApplication application, String[] args) {

    }

    public void starting() {

    }



    public void environmentPrepared(ConfigurableEnvironment environment) {

        // 获取原始值
        final ArrayList<String> excludes = environment.getProperty(EXCLUDE_KEY, ArrayList.class,
                new ArrayList<String>());
        // 增加新值
        excludes.add(MybatisAutoConfiguration.class.getName());
        // 组合
        final Map<String, Object> exclude = Collections.singletonMap(EXCLUDE_KEY, String.join(",", excludes));
        // 填充
        final MapPropertySource mapPropertySource = new MapPropertySource(this.getClass().getName(), exclude);
        // 设置
        environment.getPropertySources().addFirst(mapPropertySource);
    }

    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    public void started(ConfigurableApplicationContext context) {

    }

    public void running(ConfigurableApplicationContext context) {

    }

    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
