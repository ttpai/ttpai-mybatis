package com.ttpai.framework.mybatis.test.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/18 16:30
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApmBizMapperTest {

    @Resource
    private ApmBizMapper apmBizMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void selectById() {
        System.out.println(apmBizMapper.selectById(10L));
    }

    @Test
    public void updateById() {
    }

    @Test
    public void selectByEntity() {
    }

    @Test
    public void insertByEntity() {
    }
}