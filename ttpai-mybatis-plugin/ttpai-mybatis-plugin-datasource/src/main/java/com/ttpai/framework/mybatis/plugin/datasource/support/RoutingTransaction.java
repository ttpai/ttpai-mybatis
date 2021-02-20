package com.ttpai.framework.mybatis.plugin.datasource.support;

import org.apache.ibatis.transaction.Transaction;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import javax.sql.DataSource;

import static org.springframework.util.Assert.notNull;

/**
 * 拷贝SpringManagedTransaction代码 并重写获取DataSource的逻辑 使用栈的方式管理多数据源，让其支持在事务下正确运行
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/19
 */
public class RoutingTransaction implements Transaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutingTransaction.class);

    private final DataSource dataSource;

    private final Deque<DataSourceContainer> dataSourceStack;

    public RoutingTransaction(DataSource dataSource) {
        notNull(dataSource, "No DataSource specified");
        this.dataSource = dataSource;
        this.dataSourceStack = new ArrayDeque<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() throws SQLException {
        // 每次都要执行打开
        openConnection();
        return Objects.requireNonNull(this.dataSourceStack.peek()).getConnection();
    }

    /**
     * Gets a connection from Spring transaction manager and discovers if this {@code Transaction} should manage
     * connection or let it to Spring.
     * <p>
     * It also reads autocommit setting because when using Spring Transaction MyBatis thinks that autocommit is always
     * false and will always call commit/rollback so we need to no-op that calls.
     */
    private void openConnection() throws SQLException {
        // 选择真正的DataSource
        DataSource realDataSource = detectedRealDataSource();
        // 委托给Spring事务管理器获取连接
        Connection connection = DataSourceUtils.getConnection(realDataSource);
        boolean autoCommit = connection.getAutoCommit();
        boolean isConnectionTransactional = DataSourceUtils.isConnectionTransactional(connection, realDataSource);
        // 把DataSource和对应创建的连接放入自定义容器中
        DataSourceContainer dataSourceContainer = new DataSourceContainer(realDataSource, connection, autoCommit,
                isConnectionTransactional);
        // 推入到栈中保存，保证不重复
        if (!dataSourceStack.contains(dataSourceContainer)) {
            this.dataSourceStack.push(dataSourceContainer);
        }
        LOGGER.debug(() -> "JDBC Connection [" + connection + "] will"
                + (isConnectionTransactional ? " " : " not ") + "be managed by Spring");
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
        for (DataSourceContainer dataSourceContainer : this.dataSourceStack) {
            if (dataSourceContainer.getConnection() != null && !dataSourceContainer
                    .isConnectionTransactional() && !dataSourceContainer.isAutoCommit()) {
                LOGGER.debug(() -> "Committing JDBC Connection [" + dataSourceContainer.getConnection() + "]");
                dataSourceContainer.getConnection().commit();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollback() throws SQLException {
        for (DataSourceContainer dataSourceContainer : this.dataSourceStack) {
            if (dataSourceContainer.getConnection() != null && !dataSourceContainer
                    .isConnectionTransactional() && !dataSourceContainer.isAutoCommit()) {
                LOGGER.debug(() -> "Committing JDBC Connection [" + dataSourceContainer.getConnection() + "]");
                dataSourceContainer.getConnection().rollback();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws SQLException {
        while (!this.dataSourceStack.isEmpty()) {
            DataSourceContainer dataSourceContainer = this.dataSourceStack.pop();
            DataSourceUtils.releaseConnection(dataSourceContainer.getConnection(), dataSourceContainer.getDataSource());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getTimeout() throws SQLException {
        ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager
                .getResource(Objects.requireNonNull(this.dataSourceStack.peek()).getDataSource());
        if (holder != null && holder.hasTimeout()) {
            return holder.getTimeToLiveInSeconds();
        }
        return null;
    }

}
