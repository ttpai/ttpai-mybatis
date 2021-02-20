package com.ttpai.framework.mybatis.plugin.datasource.support;

import java.sql.Connection;

import javax.sql.DataSource;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * DataSource的容器，用于切换和释放连接
 * 
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/20
 */
@Getter
@EqualsAndHashCode
public class DataSourceContainer {

    private final DataSource dataSource;

    private final Connection connection;

    private final boolean isConnectionTransactional;

    private final boolean autoCommit;

    public DataSourceContainer(DataSource dataSource, Connection connection, boolean isConnectionTransactional,
            boolean autoCommit) {
        this.dataSource = dataSource;
        this.connection = connection;
        this.isConnectionTransactional = isConnectionTransactional;
        this.autoCommit = autoCommit;
    }
}
