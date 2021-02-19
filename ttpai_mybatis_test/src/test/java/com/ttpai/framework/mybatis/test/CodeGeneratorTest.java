package com.ttpai.framework.mybatis.test;

import com.ttpai.framework.mybatis.gen.GenApp;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lilin.tan@ttpai.cn
 * @since 2021/2/18 15:42
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(GenApp.class)
public class CodeGeneratorTest {

    @Test
    public void Generator() {
        System.out.println("Generator");
    }

    @Test
    public void testGetTableNames() throws SQLException {
        Connection conn = DriverManager.getConnection(
                "jdbc:p6spy:h2:file:F:\\ideaWorkSpace\\ttpai_mybatis\\ttpai_mybatis_test\\src\\test\\resources\\mybatisplus;TRACE_LEVEL_FILE=0;IFEXISTS=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE",
                "root",
                "test");
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES ");
        while (resultSet.next()) {
            System.out.println(resultSet.getObject("TABLE_NAME"));
        }
        // add application code here
        resultSet = stmt.executeQuery("show table status WHERE 1=1  AND NAME IN ('USER')");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        conn.close();
    }

}
