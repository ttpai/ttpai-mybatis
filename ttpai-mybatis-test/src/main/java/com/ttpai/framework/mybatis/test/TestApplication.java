package com.ttpai.framework.mybatis.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/18 15:00
 **/
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws IOException {
        final Enumeration<URL> systemResources = ClassLoader
                .getSystemResources("META-INF/spring-autoconfigure-metadata.properties");
        for (; systemResources.hasMoreElements();) {
            System.out.println(systemResources.nextElement());
        }

        SpringApplication.run(TestApplication.class, args);
    }

}
