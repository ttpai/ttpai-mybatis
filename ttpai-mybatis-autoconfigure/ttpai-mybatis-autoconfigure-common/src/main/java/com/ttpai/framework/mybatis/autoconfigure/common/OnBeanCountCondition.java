package com.ttpai.framework.mybatis.autoconfigure.common;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.Map;

import static com.ttpai.framework.mybatis.autoconfigure.common.ConditionalOnBeanCount.ATTR_COUNT;
import static com.ttpai.framework.mybatis.autoconfigure.common.ConditionalOnBeanCount.ATTR_TYPE;

/**
 * @see org.springframework.boot.autoconfigure.condition.OnBeanCondition
 * @see ConditionalOnBeanCount
 */
public class OnBeanCountCondition extends SpringBootCondition implements ConfigurationCondition {

    private static final String ANNOTATION_NAME = ConditionalOnBeanCount.class.getName();

    /**
     * 在 @Configuration 解析之后执行
     */
    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (!metadata.isAnnotated(ANNOTATION_NAME)) {
            return ConditionOutcome.match();
        }

        //
        //
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ANNOTATION_NAME);
        Class<?>[] types = (Class<?>[]) annotationAttributes.get(ATTR_TYPE);
        ConditionalOnBeanCount.Count count = (ConditionalOnBeanCount.Count) annotationAttributes.get(ATTR_COUNT);

        //
        //
        for (Class<?> type : types) {
            String[] beanNames = context.getBeanFactory().getBeanNamesForType(type, true, false);
            boolean matches = count.matches(beanNames);
            if (!matches) {
                return ConditionOutcome.noMatch("" +
                        " @ConditionalOnBeanCount(" + type.getSimpleName() + "," + count.name() + ") " +
                        "find " + (null == beanNames ? 0 : beanNames.length) + " " + type.getSimpleName() +
                        ": " + (null == beanNames ? "" : Arrays.asList(beanNames)));
            }
        }

        return ConditionOutcome.match();
    }

}
