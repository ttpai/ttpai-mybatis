## CHANGE LOG



### v1.0.0

- 修改MyBatis默认配置,参考 [MyBatis全局配置](http://confluence.ttpai.cn/pages/viewpage.action?pageId=9591766)
- 自定义 @MapperScan 行为， Mapper 上必须增加 @Mapper 注解



### v1.0.1 2021-03-08

- 如果 Mapper 返回值是集合类型，当查不到数据时，返回空集合，而不是 null，【MyBatis 默认就是这样的，无需处理】
- 【增加默认配置】
    - `ReturnInstanceForEmptyRow(true)` 行的所有列都是空时，MyBatis 默认返回 null，**这里应该设置为 true**
        - 因为能查到数据，只是数据列为 null，不能因为字段没值就认为当前数据不存在
        - 根据 ID 查询特定的一些字段的时候很容易出现这些字段都是 null，但是这行数据还是存在的
    - `CallSettersOnNulls(true)` 当数据库查到列为 null 时，是否应该调用 VO 的 setter 方法，**这里设置为应该**
        - 因为 null 也是一种数据状态，查到的字段不存在时，应该覆盖掉 VO 中的值，而不应该什么也不做
        - 同时也保证 VO 中的对象都是包装类型对象，不能是 基本数据类型
- 【fix】修复默认配置覆盖，仅支持 驼峰配置的问题；修复后支持 Spring Boot 的多种格式配置
- 多数据源自动配置 [接入方式](http://confluence.ttpai.cn/pages/viewpage.action?pageId=9591768)
    - 支持 rose-jade 命名规范
    - 当跨库事务时，Service 的事务管理器 和 Mapper 的 DataSource 不匹配时，事务失效，而不是使用当前事务的 DataSource，即采用 rose-jade 和 jdbcTemplate 的方式



### v1.0.2 2021-03-25

- `ttpai-mybatis-starter` 默认依赖 `ttpai-mybatis-autoconfigure-datasource`

- 单数据源时，使 `ttpai-mybatis-autoconfigure-datasource` 功能失效，默认 使用 mybatis 官方的 starter

- 多数据源时，无需配置 `spring.datasource.initialize=false` 来解决报错问题，会把该属性自动设置为 false 



### v1.0.3 2021-05-27

- 【fix】解决 `v1.0.2` 在 Spring Boot 1 &  2 的兼容性问题
- 去掉对 Spring 的传递依赖，屏蔽掉 Spring 版本
- Maven 坐标 `com.ttpai` 改为 `cn.ttpai` 便于发布中央仓库
