package com.ttpai.framework.mybatis.test;

import com.ttpai.framework.mybatis.test.dao.ApmBizMapper;
import com.ttpai.framework.mybatis.test.model.ApmBizVO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/18 14:10
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
class ApmBizMapperTest {
    @Resource
    private ApmBizMapper apmBizMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void selectById() {
        ApmBizVO apmBizVO = apmBizMapper.selectById(10L);
        System.out.println(apmBizVO.toString());
    }

    @Test
    void updateById() {
    }

    @Test
    void selectByEntity() {
    }

    @Test
    void insertByEntity() {
    }
}