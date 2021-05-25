package com.ttpai.framework.mybatis.autoconfigure.datasource.choose;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.ArrayList;
import java.util.Collections;

public class MyBatisAutoConfigurationExcludeTest {

    @Test
    public void test() {

        String key = "spring.autoconfigure.exclude";

        MyBatisAutoConfigurationExclude myBatisAutoConfigurationExclude = new MyBatisAutoConfigurationExclude(null, null);

        final StandardEnvironment environment = new StandardEnvironment();

        final MapPropertySource mapPropertySource = new MapPropertySource(this.getClass().getName(), Collections.singletonMap(key, "123"));

        environment.getPropertySources().addLast(mapPropertySource);

        Assert.assertEquals("123", environment.getProperty(key));
        myBatisAutoConfigurationExclude.environmentPrepared(environment);
        Assert.assertEquals("123,org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration", environment.getProperty(key));
        Assert.assertEquals("org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration", environment.getProperty(key, ArrayList.class).get(1));
    }

}