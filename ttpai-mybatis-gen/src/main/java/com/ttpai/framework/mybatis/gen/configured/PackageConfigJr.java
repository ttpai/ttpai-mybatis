package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.generator.config.PackageConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 包名配置，通过该配置，指定生成代码的包路径
 *
 * @author lilin.tan@ttpai.cn
 * @link {https://baomidou.com/config/generator-config.html#%E5%8C%85%E5%90%8D%E9%85%8D%E7%BD%AE}
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
