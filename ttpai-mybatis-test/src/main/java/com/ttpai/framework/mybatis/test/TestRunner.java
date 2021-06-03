package com.ttpai.framework.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttpai.framework.mybatis.test.biz.student.dao.StudentMapper;
import com.ttpai.framework.mybatis.test.biz.student.model.StudentVO;
import com.ttpai.framework.mybatis.test.biz.user.dao.TtpaiUserMapper;
import com.ttpai.framework.mybatis.test.biz.user.model.TtpaiUserVO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

        /* 查询所有 */
        final List<StudentVO> studentAll = studentMapper.selectByEntity(new StudentVO());
        System.out.println(studentAll.size());
        System.out.println(studentAll);

        /* 分页插件 */
        final PageInfo<StudentVO> students = PageHelper.startPage(1, 2)
                .doSelectPageInfo(() -> studentMapper.selectByEntity(new StudentVO()));

        System.out.println(students.getTotal());
        System.out.println(students.getList());

    }
}
