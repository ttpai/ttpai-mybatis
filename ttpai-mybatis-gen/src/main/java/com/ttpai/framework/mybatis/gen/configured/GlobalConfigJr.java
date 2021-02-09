package com.ttpai.framework.mybatis.gen.configured;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.ttpai.framework.mybatis.gen.GenApp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/9 15:44
 **/

@Configuration
@ConfigurationProperties(prefix = "ttpai.mybatis.generator.global")
public class GlobalConfigJr extends GlobalConfig {

    public GlobalConfigJr() {
        // 默认在 target 目录下面
        super.setOutputDir(GenApp.class.getResource("/").getPath() + "gen");
        // 生成之后打开文件浏览器
        super.setOpen(true);
        // 开启 Swagger 注解
        super.setSwagger2(true);
        // 默认文件命名后缀
        super.setEntityName("%sVO");
        super.setMapperName("%sMapper");
        super.setXmlName("%sMapper");

        super.setBaseResultMap(true);
        super.setBaseColumnList(true);
    }

    @Override
    public String getAuthor() {
        if (StringUtils.isEmpty(super.getAuthor())) {
            String author = System.getProperty("user.name");
            if (author != null) {
                return author;
            }
        }
        return super.getAuthor();
    }
}
