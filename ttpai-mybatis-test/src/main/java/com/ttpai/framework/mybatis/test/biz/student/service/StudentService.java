package com.ttpai.framework.mybatis.test.biz.student.service;

import com.ttpai.framework.mybatis.test.biz.student.dao.StudentMapper;
import com.ttpai.framework.mybatis.test.biz.user.dao.TtpaiUserMapper;
import com.ttpai.framework.mybatis.test.biz.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public void testSingleTransactionA1(){
        
    }
    public void testSingleTransactionA2(){

    }
    public void testSingleTransactionA3(){

    }
    public void testSingleTransactionA4(){

    }
    public void testSingleTransactionA5(){

    }
    
    public void testNestedTransactionQ1(){
        
    }
    public void testNestedTransactionQ2(){

    }
    public void testNestedTransactionQ3(){

    }
    public void testNestedTransactionQ4(){

    }
    public void testNestedTransactionQ5(){

    }
}
