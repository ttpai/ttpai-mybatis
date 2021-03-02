package com.ttpai.framework.mybatis.config;

import org.junit.Test;
import org.springframework.boot.bind.RelaxedNames;

public class DefaultMyBatisConfigTest {

    @Test
    public void configName() {
        RelaxedNames relaxedNames = RelaxedNames.forCamelCase("aaa.bbb.asdAsdAsd");
        for (String relaxedName : relaxedNames) {
            System.out.println(relaxedName);
        }
    }

}
