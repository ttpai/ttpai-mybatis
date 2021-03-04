package com.ttpai.framework.mybatis.config;

import lombok.Getter;
import org.apache.ibatis.session.Configuration;

import static org.apache.ibatis.session.AutoMappingUnknownColumnBehavior.FAILING;
import static org.apache.ibatis.session.LocalCacheScope.STATEMENT;

/**
 * 默认配置处理
 * <p>
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
public enum DefaultMyBatisConfig {
    /**
     * 是否开启驼峰命名自动映射，即从经典数据库列名 A_COLUMN 映射到经典 Java 属性名 aColumn。
     */
    MAP_UNDERSCORE_TO_CAMELCASE("mybatis.configuration.mapUnderscoreToCamelCase") {

        @Override
        public void setConfig(Configuration configuration) {
            configuration.setMapUnderscoreToCamelCase(true);
        }
    },
    /**
     * MyBatis 利用本地缓存机制（Local Cache）防止循环引用和加速重复的嵌套查询。 默认值为 SESSION，会缓存一个会话中执行的所有查询。
     */
    LOCAL_CACHE_SCOPE("mybatis.configuration.localCacheScope") {

        @Override
        public void setConfig(Configuration configuration) {
            configuration.setLocalCacheScope(STATEMENT);
        }
    },
    /**
     * 列映射不匹配时，改为失败
     */
    AUTO_MAPPING_UNKNOWN_COLUMN_BEHAVIOR("mybatis.configuration.autoMappingUnknownColumnBehavior") {

        @Override
        public void setConfig(Configuration configuration) {
            configuration.setAutoMappingUnknownColumnBehavior(FAILING);
        }
    },

    /**
     * 因为能查到数据，只是数据列为 null，不能因为字段没值就认为当前数据不存在
     * 根据 ID 查询特定的一些字段的时候很容易出现这些字段都是 null，但是这行数据还是存在的
     */
    RETURN_INSTANCE_FOR_EMPTY_ROW("mybatis.configuration.returnInstanceForEmptyRow") {

        @Override
        public void setConfig(Configuration configuration) {
            configuration.setReturnInstanceForEmptyRow(true);
        }
    },

    /**
     * 因为 null 也是一种数据状态，查到的字段不存在时，应该覆盖掉 VO 中的值，而不应该什么也不做
     * 同时也保证 VO 中的对象都是包装类型对象，不能是 基本数据类型
     */
    CALL_SETTERS_ON_NULLS("mybatis.configuration.callSettersOnNulls") {

        @Override
        public void setConfig(Configuration configuration) {
            configuration.setCallSettersOnNulls(true);
        }
    },

    ;

    /**
     * 配置 key
     */
    @Getter
    private final String key;

    DefaultMyBatisConfig(String key) {
        this.key = key;
    }

    abstract void setConfig(Configuration configuration);
}
