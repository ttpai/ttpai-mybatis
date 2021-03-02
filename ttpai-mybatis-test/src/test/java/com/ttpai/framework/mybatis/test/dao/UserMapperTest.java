package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.biz.user.dao.TtpaiUserMapper;
import com.ttpai.framework.mybatis.test.biz.user.model.TtpaiUserVO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/19 14:49
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserMapperTest {

    @Resource
    private TtpaiUserMapper userMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void selectByPage() {
        int pageSize = 2;
        List<TtpaiUserVO> userVOS = userMapper.selectByPage(0, pageSize);
        Assert.assertNotNull(userVOS);
        Assert.assertTrue(userVOS.size() <= pageSize);
    }

    @Test
    public void selectById() {
        TtpaiUserVO userVO = userMapper.selectById(1L);
        Assert.assertNotNull(userVO);
    }

    @Test
    public void updateById() {
        long id = 1;
        // old User
        TtpaiUserVO userVO1 = userMapper.selectById(id);

        // update user
        TtpaiUserVO userVO = new TtpaiUserVO();
        userVO.setId(id);
        userVO.setAge(500);
        userVO.setEmail("lilin.tan@ttpai.cn");
        long updateNum = userMapper.updateById(userVO);

        // new User
        TtpaiUserVO userVO2 = userMapper.selectById(id);

        Assert.assertTrue(updateNum > 0);
        Assert.assertNotEquals(userVO1.getEmail(), userVO2.getEmail());
        Assert.assertNotEquals(userVO1.getAge(), userVO2.getAge());

    }

    @Test
    public void selectByEntity() {
        String name = "Sandy";
        TtpaiUserVO userVO = new TtpaiUserVO();
        userVO.setUserName(name);
        List<TtpaiUserVO> userVOS = userMapper.selectByEntity(userVO);
        Assert.assertNotNull(userVOS);
        for (TtpaiUserVO vo : userVOS) {
            Assert.assertEquals(vo.getUserName(), name);
        }

    }

    @Test
    public void insertByEntity() {
        List<TtpaiUserVO> userVOS = userMapper.selectByEntity(new TtpaiUserVO());

        TtpaiUserVO userVO = new TtpaiUserVO();
        userVO.setUserName("lilin");
        userVO.setEmail("lilin.tan@ttpai.cn");
        userVO.setAge(100);
        Long aLong = userMapper.insertByEntity(userVO);

        Assert.assertEquals(1, (long) aLong);

        List<TtpaiUserVO> userVOS1 = userMapper.selectByEntity(new TtpaiUserVO());

        Assert.assertNotEquals(userVOS.size(), userVOS1.size());

    }
}
