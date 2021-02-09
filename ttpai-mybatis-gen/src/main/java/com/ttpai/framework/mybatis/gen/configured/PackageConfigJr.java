package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.generator.config.PackageConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/9 15:05
 **/
@Configuration
@ConfigurationProperties(prefix = "ttpai.mybatis.generator.package")
public class PackageConfigJr extends PackageConfig {

    public PackageConfigJr() {
        super.setParent("com.ttpai");
        super.setEntity("model");
        super.setMapper("dao");
        super.setXml("dao");
    }

}
