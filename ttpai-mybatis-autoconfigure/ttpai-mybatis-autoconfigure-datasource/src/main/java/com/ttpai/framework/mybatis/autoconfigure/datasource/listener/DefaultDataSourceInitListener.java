package com.ttpai.framework.mybatis.autoconfigure.datasource.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Collections;
import java.util.Map;

/**
 * 多数据源 且没有 @Primary 时，避免报错
 *
 * @author kail
 *         see org.springframework.boot.SpringApplication#initialize(Object[])
 * @see org.springframework.boot.context.event.EventPublishingRunListener#environmentPrepared(ConfigurableEnvironment)
 */
public class DefaultDataSourceInitListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    /**
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
     *      see org.springframework.boot.autoconfigure.jdbc.DataSourceInitializer#init()
     */
    private static final String KEY = "spring.datasource.initialize";

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();

        // 如果配置过了，忽略
        if (environment.containsProperty(KEY)) {
            return;
        }

        // 没有配置的话默认值是 false
        Map<String, Object> defaultConf = Collections.singletonMap(KEY, false);

        // 增加到 Environment
        final MapPropertySource mapPropertySource = new MapPropertySource(
                this.getClass().getName(),  //
                defaultConf //
        );
        environment.getPropertySources().addLast(mapPropertySource);

    }
}
