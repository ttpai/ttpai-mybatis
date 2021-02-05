package com.ttpai.framework.mybatis;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * @author Kail
 */
public class Main {

    /**
     * 实例化 genClass
     */
    private static Generator getGenInstance(SimpleCommandLinePropertySource propertySource) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        final String genClass = propertySource.getProperty("genClass");
        if (StringUtils.isBlank(genClass)) {
            return new Generator();
        }


        return (Generator) Class.forName(genClass).newInstance();
    }


    public static void main(String[] args) throws Exception {

        SimpleCommandLinePropertySource propertySource = new SimpleCommandLinePropertySource(args);

        Generator gen = getGenInstance(propertySource);

        execute(gen);
    }

    public static void execute(Generator gen) {
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
