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
 * 数据源路由
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

    private DataSource detectDataSource() {
        DataSource dataSource = null;
        // 先初始化
        initDataSource();
        // 如果只有一个数据源，直接获取不走寻址
        Optional<DataSource> dataSourceContainer = dataSourceMap.values().stream().findFirst();
        if (dataSourceContainer.isPresent() && dataSourceMap.size() == 1) {
            return dataSourceContainer.get();
        }
        String dataSourceKey = DataSourceContextHolder.getDataSourceKey();
        // 获取到DataSource key和 并且缓存中没有DataSource，走寻址逻辑
        if (dataSourceKey != null && (dataSource = cachedDataSourceMap.get(dataSourceKey)) == null) {
            // 获取类或者方法上的注解
            String annotationKey = getAnnotationDataSourceKey(dataSourceKey);
            // 如果获取到注解并且能获取到对应DataSource，加入缓存并返回
            if (annotationKey != null && (dataSource = dataSourceMap.get(annotationKey)) != null) {
                cachedDataSourceMap.put(dataSourceKey, dataSource);
                return dataSource;
            }
            // 根据生成的key遍历寻址DataSource
            for (String key : genJadeDataSourceKeys(dataSourceKey)) {
                dataSource = dataSourceMap.get(key);
                if (dataSource != null) {
                    cachedDataSourceMap.put(dataSourceKey, dataSource);
                    return dataSource;
                }
            }
        } else if (dataSourceKey == null && dataSourceContainer.isPresent()) {
            // 如果没有Key，说明不是mybatis调用的，直接获取
            return dataSourceContainer.get();
        }
        if (dataSource == null) {
            throw new IllegalStateException("datasource为空");
        }
        return dataSource;
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
