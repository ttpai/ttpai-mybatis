package com.ttpai.framework.mybatis.plugin.datasource.support;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 动态修改sqlSessionFactory的Environment
 * 替换mybatis的事务工厂为自己实现的事务工厂
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/20
 */
public class SqlSessionFactoryPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        //当前实例为SqlSessionFactory时
        if (bean instanceof SqlSessionFactory) {
            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) bean;
            //获取默认的Environment配置
            Environment environment = sqlSessionFactory.getConfiguration().getEnvironment();
            //替换Environment配置
            sqlSessionFactory.getConfiguration().setEnvironment(
                    new Environment(environment.getId(), new RoutingTransactionFactory(), environment.getDataSource()));
        }
        return bean;
    }
}
