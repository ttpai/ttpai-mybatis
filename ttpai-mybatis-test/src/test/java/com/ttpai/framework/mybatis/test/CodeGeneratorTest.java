package com.ttpai.framework.mybatis.test;

import com.ttpai.framework.mybatis.gen.GenApp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/18 15:42
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(GenApp.class)
public class CodeGeneratorTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${ttpai.mybatis.generator.strategy.include:TTPAI_USER}")
    private String tableName;

    @Test
    public void Generator() {
        System.out.println("Generator");
    }

//    @Before
    public void createTable() {

        jdbcTemplate.execute("DROP TABLE IF EXISTS " + tableName + ";");

        String createTable = "CREATE TABLE " + tableName +
                "(\n" +
                "    ID          BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n" +
                "    USER_NAME   VARCHAR(30) NULL     DEFAULT NULL COMMENT '姓名',\n" +
                "    AGE         INT(11)     NULL     DEFAULT NULL COMMENT '年龄',\n" +
                "    EMAIL       VARCHAR(50) NULL     DEFAULT NULL COMMENT '邮箱',\n" +
                "    MODIFY_TIME datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',\n"
                +
                "    CREATE_TIME datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',\n" +
                "    PRIMARY KEY (ID)\n" +
                ")";

        String insert = "INSERT INTO " + tableName + " (`USER_NAME`, `AGE`, `EMAIL`) VALUES " +
                "( 'Sandy', 13, 'adaa@xx.com')," +
                "( '张三', 50, '123@xx.com')," +
                "( '李四', 10, 'qw3@xx.com')," +
                "( 'Billie', 13, 'qaaaaw3@xx.com')," +
                "( 'Jone', 20, 'qw3@xx.com');";

        jdbcTemplate.execute(createTable);
        jdbcTemplate.execute(insert);
    }

}
