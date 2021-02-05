package com.ttpai.framework.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import static com.ttpai.framework.mybatis.tool.InputTool.scanner;


public class GenMain {

    /**
     * 全局配置
     */
    private static GlobalConfig newGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        // 默认在 target 目录下面
        globalConfig.setOutputDir(GenMain.class.getResource("/").getPath() + "gen");
        globalConfig.setAuthor("Kail");
        // 生成之后打开文件浏览器
        globalConfig.setOpen(true);
        // 开启 Swagger 注解
        globalConfig.setSwagger2(true);
        return globalConfig;
    }

    /**
     * 数据源配置
     */
    private static DataSourceConfig newDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://:3307/mysql?useSSL=false&useUnicode=true&characterEncoding=utf8");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");

        return dataSourceConfig;
    }

    /**
     * 表转换配置
     */
    private static StrategyConfig newTableConfig() {
        StrategyConfig tableConfig = new StrategyConfig();

        // 下划线转驼峰
        tableConfig.setNaming(NamingStrategy.underline_to_camel);
        // 使用 Lombok
        tableConfig.setEntityLombokModel(true);
        // /manager-user-action-history
        tableConfig.setControllerMappingHyphenStyle(true);
        // 生产字段注释
        tableConfig.setEntityTableFieldAnnotationEnable(true);

        // ❤
//        tableConfig.setNameConvert(new NameConvert());


        // 过滤指定的表
        final String tableList = scanner("请输入表名(支持 正则，多个用逗号分割 ，空代表所有表)");
        if (StringUtils.isNotBlank(tableList)) {
            tableConfig.setEnableSqlFilter(false);
            tableConfig.setInclude(tableList.split(","));
        }

        return tableConfig;
    }

    /**
     * 包配置
     */
    private static PackageConfig newPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(scanner("模块名"));
        packageConfig.setParent("com.ttpai.framework.mybatis.gen.dao");

        packageConfig.setEntity("model");
        packageConfig.setMapper("dao");

        // xml 与 mapper 放在一起
        packageConfig.setXml(packageConfig.getMapper());
        return packageConfig;
    }

    /**
     * 模板配置
     */

    private static TemplateConfig newTemplateConfig() {
        final TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.disable(TemplateType.CONTROLLER);
        templateConfig.disable(TemplateType.SERVICE);
        return templateConfig;
    }


    public static void main(String[] args) {

        AutoGenerator generator = new AutoGenerator();

        // 设置模板引擎
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        generator.setGlobalConfig(newGlobalConfig());

        // 数据源
        generator.setDataSource(newDataSourceConfig());
        // 表配置
        generator.setStrategy(newTableConfig());

        // 包配置
        generator.setPackageInfo(newPackageConfig());

        // 项目结构配置
        generator.setTemplate(newTemplateConfig());

        // 执行
        generator.execute();
    }

}
