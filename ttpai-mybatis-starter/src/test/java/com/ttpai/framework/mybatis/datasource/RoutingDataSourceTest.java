package com.ttpai.framework.mybatis.datasource;

import com.ttpai.framework.mybatis.plugin.datasource.support.DataSourceContextHolder;
import com.ttpai.framework.mybatis.plugin.datasource.support.RoutingDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.datasource.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/10
 */
@RunWith(MockitoJUnitRunner.class)
public class RoutingDataSourceTest {

    @Mock
    Map<String, DataSource> dataSourceMap;

    @InjectMocks
    RoutingDataSource routingDataSource;

    @Mock
    Connection connection;

    @Before
    public void setUp() {
        DataSource dataSource = new AbstractDataSource() {

            @Override
            public Connection getConnection() throws SQLException {
                return connection;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return connection;
            }
        };
        Mockito.when(dataSourceMap.get("jade.dataSource.com.ttpai.dao"))
                .thenReturn(dataSource);
    }

    @Test
    public void testDetectDataSource() throws SQLException {
        DataSourceContextHolder.setDataSourceKey("com.ttpai.dao.Mapper.insert");
        Connection connection = routingDataSource.getConnection();
        Assert.assertEquals(connection, this.connection);
    }

}
