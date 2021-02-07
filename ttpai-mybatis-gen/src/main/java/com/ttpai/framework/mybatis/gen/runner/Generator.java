package com.ttpai.framework.mybatis.gen.runner;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ttpai.framework.mybatis.gen.tool.InputTool.scanner;

/**
 * 代码生成器配置
 */
class Generator {

    /**
     * 全局配置
     */
    GlobalConfig newGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        // 默认在 target 目录下面
        globalConfig.setOutputDir(Generator.class.getResource("/").getPath() + "gen");
        //-Duser.name=lilin.tan@ttpai.cn
        String author = System.getProperty("user.name");
        if (author != null) {
            globalConfig.setAuthor(author);
        }
        // 生成之后打开文件浏览器
        globalConfig.setOpen(true);
        // 开启 Swagger 注解
        globalConfig.setSwagger2(true);
        //默认文件命名后缀
        globalConfig.setEntityName("%sVO");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");

        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);

        return globalConfig;
    }

    /**
     * 数据源配置
     */
    DataSourceConfig newDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://172.16.2.160:3306/tanlilin?useSSL=false&useUnicode=true&characterEncoding=utf8");
        dataSourceConfig.setUsername("admin");
        dataSourceConfig.setPassword("admin@123123");

        return dataSourceConfig;
    }

    /**
     * 表转换配置
     */
    StrategyConfig newTableConfig() {
        StrategyConfig tableConfig = new StrategyConfig();

        // 下划线转驼峰
        tableConfig.setNaming(NamingStrategy.underline_to_camel);
        // 使用 Lombok
        tableConfig.setEntityLombokModel(true);
        // /manager-user-action-history
        tableConfig.setControllerMappingHyphenStyle(true);
        // 生成字段注释
        tableConfig.setEntityTableFieldAnnotationEnable(true);
        //实体不生成 serialVersionUID
        tableConfig.setEntitySerialVersionUID(false);

        boolean isContinue = true;
        while (isContinue) {
            final String scanner = scanner("是否针对数据库下所有表生成代码。（Y:yes   N:no ）");

            //生成全部
            if ("Y".equalsIgnoreCase(scanner)) {
                isContinue = false;
            }
            //根据输入的 Table 生成代码
            if ("N".equalsIgnoreCase(scanner)) {
                // 过滤指定的表
                final String tableList = scanner("请输入需要生成代码的表名(支持 正则，多个用逗号分割 )");
                if (StringUtils.isNotBlank(tableList)) {
                    tableConfig.setEnableSqlFilter(false);
                    tableConfig.setInclude(tableList.split(","));
                }
                isContinue = false;
            }

        }


        return tableConfig;
    }

    /**
     * 包配置
     */
    PackageConfig newPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(scanner("请输入父包模块名"));
        packageConfig.setParent("com.ttpai");

        packageConfig.setEntity("model");
        packageConfig.setMapper("dao");
        // xml 与 mapper 放在一起
        packageConfig.setXml(packageConfig.getMapper());
        return packageConfig;
    }

    /**
     * 模板配置
     */

    TemplateConfig newTemplateConfig() {
        final TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.disable(TemplateType.CONTROLLER);
        templateConfig.disable(TemplateType.SERVICE);
        return templateConfig;
    }


    InjectionConfig newInjectionConfig(ConfigBuilder configBuilder) {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                //NOP
            }
        };
        FileOutConfig fileOutConfig = new FileOutConfig() {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return String.format((configBuilder.getPathInfo().get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + ".java"), tableInfo.getEntityName());
            }
        };
        fileOutConfig.setTemplatePath("templates/mapper.java.ftl");
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        fileOutConfigs.add(fileOutConfig);
        injectionConfig.setFileOutConfigList(fileOutConfigs);

        return injectionConfig;
    }
}
