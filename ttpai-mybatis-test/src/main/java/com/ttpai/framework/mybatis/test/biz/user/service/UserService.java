package com.ttpai.framework.mybatis.test.biz.user.service;

import com.ttpai.framework.mybatis.test.biz.student.dao.StudentMapper;
import com.ttpai.framework.mybatis.test.biz.student.service.StudentService;
import com.ttpai.framework.mybatis.test.biz.user.dao.TtpaiUserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/3/3
 */
@Service
public class UserService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TtpaiUserMapper userMapper;
    
    @Autowired
    private StudentService studentService;

    public void testSingleTransactionB1(){

    }
    public void testSingleTransactionB2(){

    }
    public void testSingleTransactionB3(){

    }
    public void testSingleTransactionB4(){

    }
    public void testSingleTransactionB5(){

    }

    public void testNestedTransactionW1(){

    }
    public void testNestedTransactionW2(){

    }
    public void testNestedTransactionW3(){

    }
    public void testNestedTransactionW4(){

    }
    public void testNestedTransactionW5(){

    }
    
    
}
