package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.UserVO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/19 14:49
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void selectByPage() {
        int pageSize = 2;
        List<UserVO> userVOS = userMapper.selectByPage(0, pageSize);
        Assert.assertNotNull(userVOS);
        Assert.assertTrue(userVOS.size() <= pageSize);
    }

    @Test
    public void selectById() {
        UserVO userVO = userMapper.selectById(1L);
        Assert.assertNotNull(userVO);
    }

    @Test
    public void updateById() {
        long id = 1;
        //old User
        UserVO userVO1 = userMapper.selectById(id);

        //update user
        UserVO userVO = new UserVO();
        userVO.setId(id);
        userVO.setAge(500);
        userVO.setEmail("lilin.tan@ttpai.cn");
        long updateNum = userMapper.updateById(userVO);

        //new User
        UserVO userVO2 = userMapper.selectById(id);

        Assert.assertTrue(updateNum > 0);
        assertNotEquals(userVO1.getEmail(), userVO2.getEmail());
        assertNotEquals(userVO1.getAge(), userVO2.getAge());

    }

    @Test
    public void selectByEntity() {
    }

    @Test
    public void insertByEntity() {
    }
}