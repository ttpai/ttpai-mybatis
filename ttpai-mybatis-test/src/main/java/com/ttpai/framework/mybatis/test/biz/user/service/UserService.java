package com.ttpai.framework.mybatis.test.biz.user.service;

import com.ttpai.framework.mybatis.test.biz.student.dao.StudentMapper;
import com.ttpai.framework.mybatis.test.biz.student.model.StudentVO;
import com.ttpai.framework.mybatis.test.biz.student.service.StudentService;
import com.ttpai.framework.mybatis.test.biz.user.dao.TtpaiUserMapper;
import com.ttpai.framework.mybatis.test.biz.user.model.TtpaiUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zichao.zhang@ttpai.cn
 * @since 2021/3/3
 */
@Service
public class UserService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TtpaiUserMapper userMapper;

    @Autowired
    private StudentService studentService;

    public void testUpdateA() {
        studentMapper.updateAge();
    }

    public void testFailA() {
        StudentVO studentVO = new StudentVO();
        studentMapper.updateById(studentVO);
    }

    public void testUpdateB() {
        userMapper.updateAge();
    }

    public void testFailB() {
        TtpaiUserVO ttpaiUserVO = new TtpaiUserVO();
        userMapper.updateById(ttpaiUserVO);
    }

    public void testSingleTransactionB1() {
        testUpdateA();
        testUpdateB();
    }

    public void testSingleTransactionB2() {
        testUpdateA();
        testFailB();
    }

    public void testSingleTransactionB3() {
        testUpdateB();
        testFailA();
    }

    public void testSingleTransactionB4() {
        testUpdateA();
        testUpdateB();
        testFailA();
    }

    public void testSingleTransactionB5() {
        testUpdateA();
        testUpdateB();
        testFailB();
    }

    public void testNestedTransactionW1() {
        testSingleTransactionB1();
        studentService.testSingleTransactionA1();
    }

    public void testNestedTransactionW2() {
        testSingleTransactionB1();
        studentService.testSingleTransactionA2();
    }

    public void testNestedTransactionW3() {
        testSingleTransactionB1();
        studentService.testSingleTransactionA3();
    }

    public void testNestedTransactionW4() {
        testSingleTransactionB1();
        studentService.testSingleTransactionA4();
    }

    public void testNestedTransactionW5() {
        testSingleTransactionB1();
        studentService.testSingleTransactionA5();
    }

}
