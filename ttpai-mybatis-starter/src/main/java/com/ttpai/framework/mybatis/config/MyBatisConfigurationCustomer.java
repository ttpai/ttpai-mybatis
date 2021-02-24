package com.ttpai.framework.mybatis.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.bind.RelaxedNames;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * MyBatis 修改默认配置
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 * @see DefaultMyBatisConfig
 */
public class MyBatisConfigurationCustomer implements ConfigurationCustomizer {

    private final Environment environment;

    public MyBatisConfigurationCustomer(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void customize(Configuration configuration) {

        for (DefaultMyBatisConfig value : DefaultMyBatisConfig.values()) {
            // key 必须是驼峰形式
            String configKey = value.getKey();

            // 根据驼峰形式的 Key 先找一下
            String defConfig = environment.getProperty(configKey);
            // 如果能找到，说明已经人为设置，跳过
            if (!StringUtils.isEmpty(defConfig)) {
                continue;
            }

            // region 支持 Spring 配置的各种格式

            boolean found = false;
            // 获取的结果不包含自己
            RelaxedNames relaxedNames = RelaxedNames.forCamelCase(configKey);
            for (String relaxedName : relaxedNames) {
                String property = environment.getProperty(relaxedName);
                // 其中一种格式找到了配置，则跳过
                if (!StringUtils.isEmpty(property)) {
                    found = true;
                    break;
                }
            }
            // 如果最终仍然没有找到，则进行设置
            if (!found) {
                value.setConfig(configuration);
            }
            // endregion

        }
    }
}
