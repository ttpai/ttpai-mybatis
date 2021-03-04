package com.ttpai.framework.mybatis.test.biz.student.service;

import com.ttpai.framework.mybatis.test.biz.student.dao.StudentMapper;
import com.ttpai.framework.mybatis.test.biz.student.model.StudentVO;
import com.ttpai.framework.mybatis.test.biz.user.dao.TtpaiUserMapper;
import com.ttpai.framework.mybatis.test.biz.user.model.TtpaiUserVO;
import com.ttpai.framework.mybatis.test.biz.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/3/3
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TtpaiUserMapper userMapper;
    
    @Autowired
    private UserService userService;
    
    public StudentVO selectStudent(){
        return studentMapper.selectById(3L);
    }
    public void testUpdateA(){
        studentMapper.updateAge();
    }
    public void testFailA(){
        StudentVO studentVO = new StudentVO();
        studentMapper.updateById(studentVO);
    }
    public void testUpdateB(){
       
        userMapper.updateAge();
    }
    public void testFailB(){
        TtpaiUserVO ttpaiUserVO = new TtpaiUserVO();
        userMapper.updateById(ttpaiUserVO);
    }
    
    public void testSingleTransactionA1(){
        testUpdateA();
        testUpdateB();
    }
    public void testSingleTransactionA2(){
        testUpdateA();
        testFailB();
    }
    public void testSingleTransactionA3(){
        testUpdateB();
        testFailA();
    }
    public void testSingleTransactionA4(){
        testUpdateA();
        testUpdateB();
        testFailA();
    }
    public void testSingleTransactionA5(){
        testUpdateA();
        testUpdateB();
        testFailB();
    }
    
    public void testNestedTransactionQ1(){
        testSingleTransactionA1();
        userService.testSingleTransactionB1();
    }
    public void testNestedTransactionQ2(){
        testSingleTransactionA1();
        userService.testSingleTransactionB2();
    }
    public void testNestedTransactionQ3(){
        testSingleTransactionA1();
        userService.testSingleTransactionB3();
    }
    public void testNestedTransactionQ4(){
        testSingleTransactionA1();
        userService.testSingleTransactionB4();
    }
    public void testNestedTransactionQ5(){
        testSingleTransactionA1();
        userService.testSingleTransactionB5();
    }
}
