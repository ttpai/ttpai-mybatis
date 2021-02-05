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
        application.setWebEnvironment(false);
        application.setLogStartupInfo(false);
        application.run(args);
    }

}
