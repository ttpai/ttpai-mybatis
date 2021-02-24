package com.ttpai.framework.mybatis.plugin.result;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * @author zichao.zhang
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "query", args = { //
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class //
        }),
})
@Slf4j
public class MyBatisResultPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

}
