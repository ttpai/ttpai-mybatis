package com.ttpai.framework.mybatis.plugin.datasource.transaction;

import com.ttpai.framework.mybatis.plugin.datasource.support.RoutingDataSource;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.mybatis.spring.transaction.SpringManagedTransaction;
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
 * 拷贝 SpringManagedTransaction 代码 并重写获取 DataSource 的逻辑
 * <p>
 * 使用栈的方式管理多数据源，让其支持在事务下正确运行
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/19
 * @see org.mybatis.spring.transaction.SpringManagedTransaction
 */
public class RoutingTransaction implements Transaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutingTransaction.class);

    private final DataSource dataSource;

    private final Deque<DataSourceHolder> dataSourceStack;

    /**
     * @see RoutingDataSource
     */
    public RoutingTransaction(DataSource dataSource) {
        notNull(dataSource, "No DataSource specified");
        this.dataSource = dataSource;
        this.dataSourceStack = new ArrayDeque<>();
    }

    /**
     * @see SpringManagedTransaction#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        // 每次都要执行打开
        openConnection();

        return Objects.requireNonNull(this.dataSourceStack.peek()).getConnection();
    }

    /**
     * @see SpringManagedTransaction#openConnection()
     */
    private void openConnection() throws SQLException {
        // 选择真正的DataSource
        DataSource realDataSource = detectedRealDataSource();
        // 委托给 Spring 事务管理器获取连接
        Connection connection = DataSourceUtils.getConnection(realDataSource);
        boolean autoCommit = connection.getAutoCommit();
        boolean isConnectionTransactional = DataSourceUtils.isConnectionTransactional(connection, realDataSource);
        // 把DataSource和对应创建的连接放入自定义容器中
        DataSourceHolder dataSourceHolder = new DataSourceHolder(realDataSource, connection, autoCommit,
                isConnectionTransactional);
        // 推入到栈中保存，保证不重复
        if (!dataSourceStack.contains(dataSourceHolder)) {
            this.dataSourceStack.push(dataSourceHolder);
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
     * @see SpringManagedTransaction#commit()
     */
    @Override
    public void commit() throws SQLException {
        for (DataSourceHolder dataSourceHolder : this.dataSourceStack) {
            if (dataSourceHolder.getConnection() != null
                    && !dataSourceHolder.isConnectionTransactional()
                    && !dataSourceHolder.isAutoCommit() //
            ) {
                LOGGER.debug(() -> "Committing JDBC Connection [" + dataSourceHolder.getConnection() + "]");
                dataSourceHolder.getConnection().commit();
            }
        }
    }

    /**
     * @see SpringManagedTransaction#rollback()
     */
    @Override
    public void rollback() throws SQLException {
        for (DataSourceHolder dataSourceHolder : this.dataSourceStack) {
            if (dataSourceHolder.getConnection() != null
                    && !dataSourceHolder.isConnectionTransactional()
                    && !dataSourceHolder.isAutoCommit() //
            ) {
                LOGGER.debug(() -> "Committing JDBC Connection [" + dataSourceHolder.getConnection() + "]");
                dataSourceHolder.getConnection().rollback();
            }
        }
    }

    /**
     * @see SpringManagedTransaction#close()
     */
    @Override
    public void close() throws SQLException {
        while (!this.dataSourceStack.isEmpty()) {
            DataSourceHolder dataSourceHolder = this.dataSourceStack.pop();
            DataSourceUtils.releaseConnection(dataSourceHolder.getConnection(), dataSourceHolder.getDataSource());
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
