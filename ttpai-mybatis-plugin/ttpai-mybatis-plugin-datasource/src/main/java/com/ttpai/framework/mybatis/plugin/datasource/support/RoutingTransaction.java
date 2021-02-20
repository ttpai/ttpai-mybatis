package com.ttpai.framework.mybatis.plugin.datasource.support;

import org.apache.ibatis.transaction.Transaction;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import static org.springframework.util.Assert.notNull;

/**
 * 拷贝SpringManagedTransaction代码
 * 并重写获取DataSource的逻辑
 * 以便支持多数据源
 * 
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/19
 */
public class RoutingTransaction implements Transaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutingTransaction.class);

    private final DataSource dataSource;

    private Connection connection;

    private boolean isConnectionTransactional;

    private boolean autoCommit;

    public RoutingTransaction(DataSource dataSource) {
        notNull(dataSource, "No DataSource specified");
        this.dataSource = dataSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (this.connection == null) {
            openConnection();
        }
        return this.connection;
    }

    /**
     * Gets a connection from Spring transaction manager and discovers if this {@code Transaction} should manage
     * connection or let it to Spring.
     * <p>
     * It also reads autocommit setting because when using Spring Transaction MyBatis thinks that autocommit is always
     * false and will always call commit/rollback so we need to no-op that calls.
     */
    private void openConnection() throws SQLException {
        DataSource realDataSource = detectedRealDataSource();
        this.connection = DataSourceUtils.getConnection(realDataSource);
        this.autoCommit = this.connection.getAutoCommit();
        this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.connection, realDataSource);

        LOGGER.debug(() -> "JDBC Connection [" + this.connection + "] will"
                + (this.isConnectionTransactional ? " " : " not ") + "be managed by Spring");
    }

    private DataSource detectedRealDataSource() {
        if (this.dataSource instanceof RoutingDataSource) {
            RoutingDataSource routingDataSource = (RoutingDataSource) this.dataSource;
            return routingDataSource.detectDataSource();
        }
        return this.dataSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit() throws SQLException {
        if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
            LOGGER.debug(() -> "Committing JDBC Connection [" + this.connection + "]");
            this.connection.commit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollback() throws SQLException {
        if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
            LOGGER.debug(() -> "Rolling back JDBC Connection [" + this.connection + "]");
            this.connection.rollback();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws SQLException {
        DataSourceUtils.releaseConnection(this.connection, this.dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getTimeout() throws SQLException {
        ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
        if (holder != null && holder.hasTimeout()) {
            return holder.getTimeToLiveInSeconds();
        }
        return null;
    }

}
