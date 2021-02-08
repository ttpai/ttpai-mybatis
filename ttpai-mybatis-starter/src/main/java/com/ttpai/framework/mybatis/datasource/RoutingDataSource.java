package com.ttpai.framework.mybatis.datasource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

/**
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/7
 */
public class RoutingDataSource extends AbstractDataSource {

    private Map<String, DataSource> dataSourceMap;

    public RoutingDataSource(Map<String, DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }
}
