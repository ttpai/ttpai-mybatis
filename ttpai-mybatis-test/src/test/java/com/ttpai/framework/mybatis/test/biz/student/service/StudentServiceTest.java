package com.ttpai.framework.mybatis.test.biz.student.service;

import com.ttpai.framework.mybatis.test.biz.student.model.StudentVO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/3/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void selectStudentTest() {
        StudentVO studentVO = studentService.selectStudent();
        System.out.println(studentVO);
    }
}
