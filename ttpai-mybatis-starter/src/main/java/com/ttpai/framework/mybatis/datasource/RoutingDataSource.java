package com.ttpai.framework.mybatis.datasource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

/**
 * 数据源路由
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/7
 */
public class RoutingDataSource extends AbstractDataSource implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String, DataSource> dataSourceMap;

    //todo 缓存
    private Map<String, DataSource> cachedDataSourceMap;

    @Override
    public Connection getConnection() throws SQLException {
        return detectDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return detectDataSource().getConnection(username, password);
    }

    private DataSource detectDataSource() {
        initDataSource();
        Optional<DataSource> dataSourceContainer = dataSourceMap.values().stream().findFirst();
        if (dataSourceContainer.isPresent() && dataSourceMap.size() == 1) {
            return dataSourceContainer.get();
        }
        DataSource dataSource = null;
        String dataSourceKey = DataSourceContextHolder.getDataSourceKey();
        if (dataSourceKey != null) {
            for (String key : genJadeDataSourceKeys(dataSourceKey)) {
                dataSource = dataSourceMap.get(key);
                if (dataSource != null) {
                    break;
                }
            }
        }
        if (dataSource == null) {
            throw new IllegalStateException("datasource为空");
        }
        return dataSource;
    }

    public List<String> genJadeDataSourceKeys(String dataSourceKey) {
        List<String> list = new ArrayList<>();
        int index = dataSourceKey.lastIndexOf(".");
        while (index > 0) {
            String key = dataSourceKey.substring(0, index);
            list.add("jade.dataSource." + key);
            index = key.lastIndexOf(".");
        }
        return list;
    }

    private synchronized void initDataSource() {
        if (this.dataSourceMap == null) {
            Map<String, DataSource> localDataSourceMap = applicationContext.getBeansOfType(DataSource.class);
            if (!localDataSourceMap.isEmpty()) {
                String deleteKey = null;
                for (Map.Entry<String, DataSource> entry : localDataSourceMap.entrySet()) {
                    if (entry.getValue().equals(this)) {
                        deleteKey = entry.getKey();
                    }
                }
                if (deleteKey != null) {
                    localDataSourceMap.remove(deleteKey);
                }
            }
            this.dataSourceMap = localDataSourceMap;
            this.cachedDataSourceMap = new HashMap<>(dataSourceMap.size());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
