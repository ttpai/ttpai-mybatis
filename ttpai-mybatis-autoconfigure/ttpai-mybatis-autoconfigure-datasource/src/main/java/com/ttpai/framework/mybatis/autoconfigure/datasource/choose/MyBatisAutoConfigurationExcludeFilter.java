package com.ttpai.framework.mybatis.autoconfigure.datasource.choose;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * 排除 MybatisAutoConfiguration，
 *
 * @author kail
 * @see MyBatisAutoConfigurationImporter 根据自定义条件判断是否重新启用
 *      ---
 * @see AutoConfigurationImportSelector#selectImports(AnnotationMetadata) > 获取所有 自动配置配
 * @see AutoConfigurationImportSelector#filter(List, AutoConfigurationMetadata) > 从自动配置出删除指定的类
 *      ---
 * @see MybatisAutoConfiguration
 *      ----------------------------------------------------------------------------------------------------
 * @deprecated AutoConfigurationImportFilter Spring 4 & 5 不兼容，替换为系统变量的形式
 *             该类的本意是只在 Spring Boot 下生效，基于 AutoConfigurationImportSelector#selectImports 流程
 *             但是 Spring 5 之后，ConfigurationClassParser#processImports 多了 filter 参数，同样会对 @Import 生效
 *             从而导致 MyBatisAutoConfigurationImporter 失效
 */
public class MyBatisAutoConfigurationExcludeFilter implements AutoConfigurationImportFilter {

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < autoConfigurationClasses.length; i++) {
            matches[i] = !MybatisAutoConfiguration.class.getName().equals(autoConfigurationClasses[i]);
        }
        return matches;
    }

}
