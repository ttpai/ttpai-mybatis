package com.ttpai.framework.mybatis.plugin.datasource.support;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

/**
 * 自定义的mybatis事务工厂
 * @author zichao.zhang
 */
public class RoutingTransactionFactory extends SpringManagedTransactionFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new RoutingTransaction(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction newTransaction(Connection conn) {
        throw new UnsupportedOperationException("New Spring transactions require a DataSource");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperties(Properties props) {
        // not needed in this version
    }

}
