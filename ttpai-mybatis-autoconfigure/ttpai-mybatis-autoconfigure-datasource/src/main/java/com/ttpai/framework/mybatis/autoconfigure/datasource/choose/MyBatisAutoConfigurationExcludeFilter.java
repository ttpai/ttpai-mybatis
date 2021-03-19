package com.ttpai.framework.mybatis.autoconfigure.datasource.choose;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.*;
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
