package com.ttpai.framework.mybatis.config;

import org.apache.ibatis.session.Configuration;

import lombok.Getter;

import static org.apache.ibatis.session.AutoMappingUnknownColumnBehavior.FAILING;
import static org.apache.ibatis.session.LocalCacheScope.STATEMENT;

/**
 * 默认值处理
 *
 * @author zichao.zhang@ttpai.cn
 * @date 2021/2/5
 */
public enum DefaultMybatisConfig {
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
    AUTO_MAPPING_UNKNOWN_COLUMN_BEHAVIOR("mybatis.configuration.autoMappingUnknownColumnBehavior") {

        @Override
        public void setConfig(Configuration configuration) {
            configuration.setAutoMappingUnknownColumnBehavior(FAILING);
        }
    };

    /**
     * 配置 key
     */
    @Getter
    private final String key;

    DefaultMybatisConfig(String key) {
        this.key = key;
    }

    abstract void setConfig(Configuration configuration);
}
