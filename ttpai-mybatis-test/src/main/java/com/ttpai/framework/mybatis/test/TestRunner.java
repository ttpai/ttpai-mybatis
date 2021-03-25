package com.ttpai.framework.mybatis.test;

import com.ttpai.framework.mybatis.test.biz.student.dao.StudentMapper;
import com.ttpai.framework.mybatis.test.biz.student.model.StudentVO;
import com.ttpai.framework.mybatis.test.biz.user.dao.TtpaiUserMapper;
import com.ttpai.framework.mybatis.test.biz.user.model.TtpaiUserVO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestRunner implements ApplicationRunner {

    @Resource
    private TtpaiUserMapper userMapper;

    @Resource
    private StudentMapper studentMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TtpaiUserVO ttpaiUserVO = userMapper.selectById(1L);
        System.out.println(ttpaiUserVO);

        StudentVO studentVO = studentMapper.selectById(1L);
        System.out.println(studentVO);

    }
}
