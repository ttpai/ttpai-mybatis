package com.ttpai.framework.mybatis.gen;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kail
 */
@SpringBootApplication
public class GenApp {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(GenApp.class);
        application.setBannerMode(Banner.Mode.OFF);

        // 【无需设置】 该方法 Spring Boot 1 和 2 不兼容
        // 1 application.setWebEnvironment(false);
        // 2 application.setWebApplicationType(WebApplicationType.NONE);

        application.setLogStartupInfo(false);
        application.run(args);
    }

}
