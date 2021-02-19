package com.ttpai.framework.mybatis.gen.runner;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.ttpai.framework.mybatis.gen.configured.DataSourceConfigJr;
import com.ttpai.framework.mybatis.gen.configured.GlobalConfigJr;
import com.ttpai.framework.mybatis.gen.configured.PackageConfigJr;
import com.ttpai.framework.mybatis.gen.configured.StrategyConfigJr;
import com.ttpai.framework.mybatis.gen.configured.TemplateConfigJr;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * @author lilin.tan@ttpai.cn
 */
@Component
public class GenRunner implements ApplicationRunner {

    @Resource
    private DataSourceConfigJr dataSourceConfigJr;

    @Resource
    private PackageConfigJr packageConfigJr;

    @Resource
    private StrategyConfigJr strategyConfigJr;

    @Resource
    private TemplateConfigJr templateConfigJr;

    @Resource
    private GlobalConfigJr globalConfigJr;

    @Override
    public void run(ApplicationArguments args) {
        execute();
    }

    /**
     * 生成代码
     */
    private void execute() {
        AutoGenerator generator = new AutoGenerator();

        // 设置模板引擎
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        ConfigBuilder configBuilder = new ConfigBuilder(
                // 包配置
                packageConfigJr,
                // 数据源
                dataSourceConfigJr,
                // 表配置
                strategyConfigJr,
                // 项目结构配置
                templateConfigJr,
                // 全局配置
                globalConfigJr);

        // 注入配置信息 - 自定义模板
        configBuilder.setInjectionConfig(newInjectionConfig(configBuilder));

        generator.config(configBuilder);

        // 执行
        generator.execute();
    }

    private InjectionConfig newInjectionConfig(ConfigBuilder configBuilder) {
        InjectionConfig injectionConfig = new InjectionConfig() {

            @Override
            public void initMap() {
                // NOP
            }
        };
        FileOutConfig fileOutConfig = new FileOutConfig() {

            @Override
            public File outputFile(TableInfo tableInfo) {
                return new File(String.format((configBuilder.getPathInfo().get(ConstVal.MAPPER_PATH) + File.separator
                                                       + tableInfo.getMapperName() + ".java"), tableInfo.getEntityName()));
            }
        };
        fileOutConfig.setTemplatePath("templates/mapper.java.ftl");
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        fileOutConfigs.add(fileOutConfig);
        injectionConfig.setFileOutConfigList(fileOutConfigs);

        return injectionConfig;
    }

}
