package com.ttpai.framework.mybatis.gen.runner;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private void execute(Generator gen) {
        AutoGenerator generator = new AutoGenerator();

        // 设置模板引擎
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        ConfigBuilder configBuilder = new ConfigBuilder(
                // 包配置
                gen.newPackageConfig(),
                // 数据源
                gen.newDataSourceConfig(),
                // 表配置
                gen.newTableConfig(),
                // 项目结构配置
                gen.newTemplateConfig(),
                // 全局配置
                gen.newGlobalConfig());

        //自定义模板
        configBuilder.setInjectionConfig(gen.newInjectionConfig(configBuilder));

        generator.setConfig(configBuilder);

        // 执行
        generator.execute();
    }



}
