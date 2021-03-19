package com.ttpai.framework.mybatis.test.biz.rose.controllers;

import com.ttpai.framework.mybatis.test.biz.student.dao.StudentMapper;
import com.ttpai.framework.rose.boot.autoconfigure.filter.RoseBootFilter;
import net.paoding.rose.web.annotation.Path;

import javax.annotation.Resource;

/**
 * 测试依赖关系，解决一下问题
 * <p>
 * Consider defining a bean named 'SqlSessionFactory.com.ttpai.framework.mybatis.test.biz.student.dao' in your
 * configuration.
 * <p>
 * 原因：RoseAutoConfiguration#roseBootFilterRegistration(RoseBootFilter) 最终对 RoseModulesFinder 的依赖会
 * 使 StudentMapper 先加载，这时其对应的 SqlSessionFactory 还没有被 MyBatisSqlSessionFactoryInit 初始化
 *
 * @see com.ttpai.framework.mybatis.autoconfigure.datasource.support.MyBatisSqlSessionFactoryInit
 * @see com.ttpai.framework.rose.boot.autoconfigure.RoseAutoConfiguration#roseBootFilterRegistration(RoseBootFilter)
 */
@Path("/")
public class RoseController {

    @Resource
    private StudentMapper mapper;
}
