package com.ttpai.framework.mybatis.autoconfigure.datasource.support;

import org.springframework.context.ApplicationEvent;

/**
 * @see MyBatisSqlSessionFactoryInitEventListener
 */
public class MyBatisSqlSessionFactoryInitEvent extends ApplicationEvent {

    public MyBatisSqlSessionFactoryInitEvent(Object source) {
        super(source);
    }
}
