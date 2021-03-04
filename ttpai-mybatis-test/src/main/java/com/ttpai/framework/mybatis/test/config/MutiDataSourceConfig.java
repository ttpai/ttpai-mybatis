package com.ttpai.framework.mybatis.test.config;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/3/3
 */
@Configuration
public class MutiDataSourceConfig {

    @Bean(name = "mybatis.dataSource.com.ttpai.framework.mybatis.test.biz.user.dao")
    public DataSource userDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://172.16.2.160:3306/tanlilin?useUnicode=true&characterEncoding=utf-8");
        druidDataSource.setUsername("admin");
        druidDataSource.setPassword("admin@123123");
        return druidDataSource;
    }
    @Bean(name = "mybatis.dataSource.com.ttpai.framework.mybatis.test.biz.student.dao")
    public DataSource studentDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://172.16.2.160:3306/zhangzichao?useUnicode=true&characterEncoding=utf-8");
        druidDataSource.setUsername("admin");
        druidDataSource.setPassword("admin@123123");
        return druidDataSource;
    }
    
    @Bean(name = {"userDataSourceTransactionManager"})
    public PlatformTransactionManager userDataSourceTransactionManager() {
        return new DataSourceTransactionManager(userDataSource());
    }
    @Bean(name = {"studentDataSourceTransactionManager"})
    public PlatformTransactionManager studentDataSourceTransactionManager() {
        return new DataSourceTransactionManager(studentDataSource());
    }
    @Bean("userDataSourceTransactionPointcutAdvisor")
    public DefaultBeanFactoryPointcutAdvisor  userDataSourceTransactionPointcutAdvisor() {
        return customBeanFactoryPointcutAdvisor(userDataSourceTransactionManager(), "execution(* com.ttpai.framework.mybatis.test.biz.user.service..*.*(..))");
    }
    @Bean("studentDataSourceTransactionPointcutAdvisor")
    public DefaultBeanFactoryPointcutAdvisor  studentDataSourceTransactionPointcutAdvisor() {
        return customBeanFactoryPointcutAdvisor(studentDataSourceTransactionManager(), "execution(* com.ttpai.framework.mybatis.test.biz.student.service..*.*(..))");
    }

    public static DefaultBeanFactoryPointcutAdvisor customBeanFactoryPointcutAdvisor(PlatformTransactionManager transactionManager, String expression) {
        DefaultBeanFactoryPointcutAdvisor defaultBeanFactoryPointcutAdvisor = new DefaultBeanFactoryPointcutAdvisor();
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        transactionInterceptor.setTransactionAttributeSources(newTransactionAttributes());
        defaultBeanFactoryPointcutAdvisor.setAdvice(transactionInterceptor);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        defaultBeanFactoryPointcutAdvisor.setPointcut(pointcut);
        return defaultBeanFactoryPointcutAdvisor;
    }

    private static NameMatchTransactionAttributeSource[] newTransactionAttributes() {
        return new NameMatchTransactionAttributeSource[]{newNameMatchTransactionAttributeSource("select*", newReadTransactionAttribute()), newNameMatchTransactionAttributeSource("find*", newReadTransactionAttribute()), newNameMatchTransactionAttributeSource("save*", newWriteTransactionAttribute()), newNameMatchTransactionAttributeSource("insert*", newWriteTransactionAttribute()), newNameMatchTransactionAttributeSource("update*", newWriteTransactionAttribute()), newNameMatchTransactionAttributeSource("del*", newWriteTransactionAttribute()), newNameMatchTransactionAttributeSource("add*", newWriteTransactionAttribute()), newNameMatchTransactionAttributeSource("test*", newWriteTransactionAttribute())};
    }

    private static NameMatchTransactionAttributeSource newNameMatchTransactionAttributeSource(String methodName, TransactionAttribute attr) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod(methodName, attr);
        return source;
    }

    private static RuleBasedTransactionAttribute newReadTransactionAttribute() {
        RuleBasedTransactionAttribute attribute = new RuleBasedTransactionAttribute();
        attribute.setPropagationBehavior(1);
        attribute.setReadOnly(true);
        return attribute;
    }

    private static RuleBasedTransactionAttribute newWriteTransactionAttribute() {
        RuleBasedTransactionAttribute attribute = new RuleBasedTransactionAttribute();
        attribute.setPropagationBehavior(0);
        List<RollbackRuleAttribute> rollbackRules = new LinkedList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));
        attribute.setRollbackRules(rollbackRules);
        return attribute;
    }
}
