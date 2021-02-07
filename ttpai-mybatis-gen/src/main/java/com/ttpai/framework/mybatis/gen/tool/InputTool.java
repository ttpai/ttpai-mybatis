package com.ttpai.framework.mybatis.gen.tool;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Scanner;

public class InputTool {

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println( tip + "：");

        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
