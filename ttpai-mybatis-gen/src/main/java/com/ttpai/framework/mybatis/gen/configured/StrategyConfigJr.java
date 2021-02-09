package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库表配置
 * 
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/9 15:28
 **/
@Configuration
@ConfigurationProperties(prefix = "ttpai.mybatis.generator.strategy")
public class StrategyConfigJr extends StrategyConfig {

    public StrategyConfigJr() {
        // 下划线转驼峰
        super.setNaming(NamingStrategy.underline_to_camel);
        // 使用 Lombok
        super.setEntityLombokModel(true);
        // /manager-user-action-history
        super.setControllerMappingHyphenStyle(true);
        // 生成字段注释
        super.setEntityTableFieldAnnotationEnable(true);
        // 实体不生成 serialVersionUID
        super.setEntitySerialVersionUID(false);
        // 生成字段常量
        super.setEntityColumnConstant(true);

    }

}
