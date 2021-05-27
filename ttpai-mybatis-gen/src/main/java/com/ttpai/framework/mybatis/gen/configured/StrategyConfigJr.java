package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
 *
 * @author lilin.tan@ttpai.cn
 *         see
 *         {https://baomidou.com/config/generator-config.html#%E6%95%B0%E6%8D%AE%E5%BA%93%E8%A1%A8%E9%85%8D%E7%BD%AE}
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
