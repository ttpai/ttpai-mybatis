package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 模板配置，可自定义代码生成的模板，实现个性化操作
 *
 * @author lilin.tan@ttpai.cn
 * @link {https://baomidou.com/config/generator-config.html#%E6%A8%A1%E6%9D%BF%E9%85%8D%E7%BD%AE}
 * @since 2021/2/9 15:41
 **/
@Configuration
@ConfigurationProperties(prefix = "ttpai.mybatis.generator.template")
public class TemplateConfigJr extends TemplateConfig {

    public TemplateConfigJr() {
        // 默认不生成 controller
        super.disable(TemplateType.CONTROLLER);
        // 默认不生成 service
        super.disable(TemplateType.SERVICE);
    }
}
