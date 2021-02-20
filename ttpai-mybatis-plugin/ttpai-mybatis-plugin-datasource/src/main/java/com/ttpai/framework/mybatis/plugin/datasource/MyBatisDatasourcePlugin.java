package com.ttpai.framework.mybatis.plugin.datasource;

import com.ttpai.framework.mybatis.plugin.datasource.support.DataSourceContextHolder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zichao.zhang
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { //
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class //
        }),
        @Signature(type = Executor.class, method = "query", args = { //
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class //
        }),
        @Signature(type = Executor.class, method = "update", args = { //
                MappedStatement.class, Object.class //
        })
})
@Slf4j
public class MyBatisDatasourcePlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //
        // com.ttpai.framework.mybatis.test.dao.TtpaiUserMapper.selectByPage
        String id = mappedStatement.getId();

        try {
            DataSourceContextHolder.setDataSourceKey(resolveDataSourceKey(id));
            //
            return invocation.proceed();
        } finally {
            DataSourceContextHolder.clearDataSourceKey();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * com.ttpai.framework.mybatis.test.dao.TtpaiUserMapper.selectByPage
     */
    private String resolveDataSourceKey(String id) {
        return id;
    }

}
