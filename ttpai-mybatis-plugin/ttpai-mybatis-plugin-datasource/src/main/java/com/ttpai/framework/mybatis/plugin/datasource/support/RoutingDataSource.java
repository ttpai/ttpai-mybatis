package com.ttpai.framework.mybatis.plugin.datasource.support;

import com.ttpai.framework.mybatis.plugin.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * 带路由功能的数据源
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/7
 */
@Slf4j
public class RoutingDataSource extends AbstractDataSource implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String, DataSource> dataSourceMap;

    /**
     * 缓存
     */
    private final Map<String, DataSource> cachedDataSourceMap = new HashMap<>();

    @Override
    public Connection getConnection() throws SQLException {
        return detectDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return detectDataSource().getConnection(username, password);
    }

    public DataSource detectDataSource() {
        DataSource dataSource;
        // 先初始化
        initDataSource();

        // 没有数据源
        Optional<DataSource> dataSourceContainer = dataSourceMap.values().stream().findFirst();
        if (!dataSourceContainer.isPresent()) {
            throw new IllegalStateException("datasource为空");
        }

        // 一个数据源
        if (dataSourceMap.size() == 1) {
            return dataSourceContainer.get();
        }

        // 没有走 MyBatis 插件
        String dataSourceKey = DataSourceContextHolder.getDataSourceKey();
        if (dataSourceKey == null) {
            // 如果没有Key，说明不是 mybatis 调用的，直接获取
            return dataSourceContainer.get();
        }

        // 缓存中有
        dataSource = cachedDataSourceMap.get(dataSourceKey);
        if (null != dataSource) {
            return dataSource;
        }

        /*
         * 1. 根据 @DS 注解找数据源
         */
        String dsKey = getAnnotationDataSourceKey(dataSourceKey);
        // 如果获取到注解并且能获取到对应DataSource，加入缓存并返回
        if (dsKey != null && (dataSource = dataSourceMap.get(dsKey)) != null) {
            cachedDataSourceMap.put(dataSourceKey, dataSource);
            return dataSource;
        }

        /*
         * 2. 如果 @DS 注解找不到，适配 jade 逻辑，根据 package 名称找
         */
        for (String key : genJadeDataSourceKeys(dataSourceKey)) {
            dataSource = dataSourceMap.get(key);
            if (dataSource != null) {
                cachedDataSourceMap.put(dataSourceKey, dataSource);
                return dataSource;
            }
        }

        throw new IllegalStateException("datasource为空");
    }

    private String getAnnotationDataSourceKey(String dataSourceKey) {
        String annotationKey = null;
        int index = dataSourceKey.lastIndexOf(".");
        String classname = dataSourceKey.substring(0, index);
        try {
            Class<?> clazz = Class.forName(classname);
            DS ds = clazz.getAnnotation(DS.class);
            if (ds != null) {
                annotationKey = ds.value();
            }
        } catch (ClassNotFoundException e) {
            log.warn("获取注解时无法生成对应的类", e);
        }
        return annotationKey;
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
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
