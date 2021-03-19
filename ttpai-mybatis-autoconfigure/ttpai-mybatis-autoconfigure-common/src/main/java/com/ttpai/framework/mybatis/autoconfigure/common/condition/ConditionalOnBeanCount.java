package com.ttpai.framework.mybatis.autoconfigure.common.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ttpai.framework.mybatis.autoconfigure.common.condition.OnBeanCountCondition;
import org.springframework.context.annotation.Conditional;

/**
 * 根据 Bean 的数量生效，不管是否是 Primary
 *
 * @author Kail
 * @since 1.0.2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnBeanCountCondition.class)
public @interface ConditionalOnBeanCount {

    String ATTR_TYPE = "type";

    String ATTR_COUNT = "count";

    /**
     * 根据该类型获取 Bean
     */
    Class<?>[] type();

    /**
     * 定义 Bean 的个数
     */
    Count count();

    enum Count {

        // 一个也没有
        NONE {

            @Override
            public boolean matches(String[] beanNames) {
                return null == beanNames || beanNames.length <= 0;
            }
        },
        // 只有一个
        SINGLE {

            @Override
            public boolean matches(String[] beanNames) {
                return null != beanNames && beanNames.length == 1;
            }

        },
        // 多个
        MULTI {

            @Override
            public boolean matches(String[] beanNames) {
                return null != beanNames && beanNames.length > 1;
            }

        };

        abstract boolean matches(String[] beanNames);

    }

}
