package com.ttpai.framework.mybatis.datasource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

/**
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/7
 */
public class RoutingDataSource extends AbstractDataSource {

    private final Map<String, DataSource> dataSourceMap;

    private final Optional<DataSource> defaultDataSource;

    public RoutingDataSource(Map<String, DataSource> dataSourceMap) {
        if (dataSourceMap == null) {
            throw new IllegalArgumentException("dataSource不能为空");
        }
        this.dataSourceMap = dataSourceMap;
        this.defaultDataSource = dataSourceMap.values().stream().findFirst();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return detectDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return detectDataSource().getConnection(username, password);
    }

    private DataSource detectDataSource() {
        DataSource dataSource = null;
        String dataSourceKey = DataSourceContextHolder.getDataSourceKey();
        if (dataSourceKey != null) {
            dataSource = dataSourceMap.get(dataSourceKey);
        }
        if (dataSource == null && defaultDataSource.isPresent()) {
            dataSource = defaultDataSource.get();
        }
        if (dataSource == null) {
            throw new IllegalStateException("datasource为空");
        }
        return dataSource;
    }
}
