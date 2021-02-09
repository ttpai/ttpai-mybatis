package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lilin.tan@ttpai.cn
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
