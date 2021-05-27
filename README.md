# ttpai-mybatis-starter



## 默认配置

| 版本   | 配置名                             | 说明                                                         | 官方默认    | 公司修改      |
| :----- | :--------------------------------- | :----------------------------------------------------------- | :---------- | :------------ |
| v1.0.0 | `autoMappingUnknownColumnBehavior` | 指定发现自动映射目标未知列（或未知属性类型）的行为。**NONE**：不做任何处理 ，**WARNING** ：打印告警日志，**FAILING**：抛出异常 | **NONE**    | `**FAILING**` |
| v1.0.0 | `mapUnderscoreToCamelCase`         | 否开启驼峰命名自动映射，即从经典数据库列名 A_COLUMN 映射到经典 Java 属性名 aColumn。 | **False**   | **True**      |
| v1.0.0 | `localCacheScope`                  | MyBatis 利用本地缓存机制（Local Cache）防止循环引用和加速重复的嵌套查询。 默认值为 SESSION，会缓存一个会话中执行的所有查询。 | **SESSION** | **STATEMENT** |
| v1.0.1 | `returnInstanceForEmptyRow`        | 当返回行的所有列都是空时，MyBatis 默认返回 `null`，**设置为 true 时返回空对象**，因为能查到数据，只是数据列为 null，不能因为字段没值就认为当前数据不存在，如：根据 ID 查询特定的一些字段的时候容易出现被查的字段都是 null，但是这行数据还是存在的 | false       | **true**      |
| v1.0.1 | `callSettersOnNulls`               | 指定当查到字段值为 null 的时候是否调用映射对象的 setter，**设置为 true 调用**因为 null 也是一种数据状态，查到的字段不存在时，应该覆盖掉 VO 中的值，而不应该什么也不做；同时也保证 VO 中的对象都是包装类型对象，不能是 基本数据类型 | false       | **true**      |



## 默认行为

- 自定义 @MapperScan 行为， Mapper 上必须增加 @Mapper 注解



## 多数据源支持

- 支持 rose-jade 命名规范
- 多数据源时，无需配置 `spring.datasource.initialize=false` 来解决报错问题，会把该属性自动设置为 `false`



## 命名规范



## 方式 1 符合

1. 无论是通过**spring xml文件** 还是 通过 **java config (@Bean)**配置 DataSource Bean ，只要 bean的命名符合 **jade.dataSource.包名** 、**mybatis.dataSource.包名、** **ttpai.mybatis.dataSource.包名。**就会扫描**包名**下的 mapper 和这个 DataSource Bean关联。
2. 或者配置DataSource Bean 的别名，只要别名符合上述规范，也会做扫描。



## 方式 2

1. 
   1. 无论是通过**spring xml文件** 还是 通过 **java config (@Bean)**配置 DataSource Bean ，只要 bean的命名符合 **jade.dataSource.包名** 、**mybatis.dataSource.包名、** **ttpai.mybatis.dataSource.包名。**就会扫描**包名**下的 mapper 和这个 DataSource Bean关联。
   2. 或者配置DataSource Bean 的别名，只要别名符合上述规范，也会做扫描。
2. 
3. 在配置文件中配置DataSource名称和package的映射关系 
   1. 在配置文件中配置 **ttpai.mybatis.datasource.mapping.DataSourceBean名称 = 包名 ，**会扫描**包名**下的 mapper，和DataSource Bean关联
   2. 配置文件要加载到Spring的Environment中才会生效 (SpringBoot的application.properties配置默认会加载到spring 的 Environment 中)
