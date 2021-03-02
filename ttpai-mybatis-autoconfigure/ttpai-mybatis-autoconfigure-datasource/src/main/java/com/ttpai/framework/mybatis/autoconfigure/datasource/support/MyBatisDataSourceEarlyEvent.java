package com.ttpai.framework.mybatis.autoconfigure.datasource.support;

import org.springframework.context.ApplicationEvent;

/**
 * @see MyBatisSqlSessionFactoryInitListener
 */
public class MyBatisDataSourceEarlyEvent extends ApplicationEvent {

    public MyBatisDataSourceEarlyEvent(Object source) {
        super(source);
    }
}
