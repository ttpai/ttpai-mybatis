package com.ttpai.framework.mybatis.gen.runner;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GenRunner implements ApplicationRunner {

    @Resource
    private Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Generator gen = getGenInstance();
        execute(gen);
    }

    /**
     * 实例化 genClass
     */
    private Generator getGenInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        final String genClass = env.getProperty("genClass");
        if (StringUtils.isBlank(genClass)) {
            return new Generator();
        }

        return (Generator) Class.forName(genClass).newInstance();
    }

    /**
     * 生成代码
     */
    public void execute(Generator gen) {
        AutoGenerator generator = new AutoGenerator();

        // 设置模板引擎
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        generator.setGlobalConfig(gen.newGlobalConfig());

        // 数据源
        generator.setDataSource(gen.newDataSourceConfig());

        // 表配置
        generator.setStrategy(gen.newTableConfig());

        // 包配置
        generator.setPackageInfo(gen.newPackageConfig());

        // 项目结构配置
        generator.setTemplate(gen.newTemplateConfig());

        // 执行
        generator.execute();
    }
}
