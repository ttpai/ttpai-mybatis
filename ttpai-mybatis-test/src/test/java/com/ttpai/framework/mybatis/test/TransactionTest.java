package com.ttpai.framework.mybatis.test;

import com.ttpai.framework.mybatis.test.biz.student.service.StudentService;
import com.ttpai.framework.mybatis.test.biz.user.service.UserService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author zichao.zhang@ttpai.cn
 * @since 2021/3/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Test
    public void testSingleTransactionA1Test() {
        studentService.testSingleTransactionA1();
    }

    @Test
    public void testSingleTransactionA2Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> studentService.testSingleTransactionA2());
    }

    @Test
    public void testSingleTransactionA3Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> studentService.testSingleTransactionA3());
    }

    @Test
    public void testSingleTransactionA4Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> studentService.testSingleTransactionA4());
    }

    @Test
    public void testSingleTransactionA5Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> studentService.testSingleTransactionA5());
    }

    @Test
    public void testSingleTransactionB1Test() {
        userService.testSingleTransactionB1();
    }

    @Test
    public void testSingleTransactionB2Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> userService.testSingleTransactionB2());
    }

    @Test
    public void testSingleTransactionB3Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> userService.testSingleTransactionB3());
    }

    @Test
    public void testSingleTransactionB4Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> userService.testSingleTransactionB4());

    }

    @Test
    public void testSingleTransactionB5Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> userService.testSingleTransactionB5());
    }

    @Test
    public void testSingleTransactionQ1Test() {
        studentService.testNestedTransactionQ1();
    }

    @Test
    public void testSingleTransactionQ2Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> studentService.testNestedTransactionQ2());
    }

    @Test
    public void testSingleTransactionQ3Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> studentService.testNestedTransactionQ3());
    }

    @Test
    public void testSingleTransactionQ4Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> studentService.testNestedTransactionQ4());
    }

    @Test
    public void testSingleTransactionQ5Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> studentService.testNestedTransactionQ5());
    }

    @Test
    public void testSingleTransactionW1Test() {
        userService.testNestedTransactionW1();
    }

    @Test
    public void testSingleTransactionW2Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> userService.testNestedTransactionW2());
    }

    @Test
    public void testSingleTransactionW3Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> userService.testNestedTransactionW3());
    }

    @Test
    public void testSingleTransactionW4Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> userService.testNestedTransactionW4());
    }

    @Test
    public void testSingleTransactionW5Test() {
        Assert.assertThrows(BadSqlGrammarException.class, () -> userService.testNestedTransactionW5());
    }
}
