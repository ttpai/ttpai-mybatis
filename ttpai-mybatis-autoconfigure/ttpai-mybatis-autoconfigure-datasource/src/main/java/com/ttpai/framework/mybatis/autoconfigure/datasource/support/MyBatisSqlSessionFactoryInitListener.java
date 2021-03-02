package com.ttpai.framework.mybatis.autoconfigure.datasource.support;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.Ordered;

/**
 * 把 MyBatisSqlSessionFactoryInit 的初始化时机提前，先进行初始化，
 * 避免 {@link com.ttpai.framework.mybatis.autoconfigure.datasource.MyBatisMultiDataSourceProcessorConfigure#scan(String)} 找不到 SqlSessionFactory
 *
 * @see AbstractApplicationContext#refresh()
 * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory) 调用 BeanFactoryPostProcessors
 * @see AbstractApplicationContext#registerListeners() 把 MyBatisSqlSessionFactoryInit 的初始化时机提前，先进行初始化
 * @see AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory) 实例化所有单例 Bean
 */
public class MyBatisSqlSessionFactoryInitListener implements SmartApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        ApplicationContext source = (ApplicationContext) event.getSource();
        source.getBean(MyBatisSqlSessionFactoryInit.class);
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return MyBatisDataSourceEarlyEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        if (null == sourceType) {
            return false;
        }

        return ApplicationContext.class.isAssignableFrom(sourceType);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
