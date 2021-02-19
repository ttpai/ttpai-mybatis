package com.ttpai.framework.mybatis.test;

import com.ttpai.framework.mybatis.gen.GenApp;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/18 15:42
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(GenApp.class)
public class CodeGeneratorTest {

    @Test
    @Ignore
    public void Generator() {
        System.out.println("Generator");
    }

}
