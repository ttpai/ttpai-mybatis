package com.ttpai.framework.mybatis.test.biz.user.controller;

import com.ttpai.framework.mybatis.test.biz.student.service.StudentService;
import com.ttpai.framework.mybatis.test.biz.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/3/2
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

}
