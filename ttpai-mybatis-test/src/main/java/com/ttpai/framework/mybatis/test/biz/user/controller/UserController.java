package com.ttpai.framework.mybatis.test.biz.user.controller;

import com.ttpai.framework.mybatis.test.biz.user.dao.TtpaiUserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/3/2
 */
@RequestMapping
@Controller
public class UserController {

    @Autowired
    private TtpaiUserMapper ttpaiUserMapper;
}
