
## ttpai-mybatis-gen

MyBatis代码生成器，[接入方式](http://confluence.ttpai.cn/pages/viewpage.action?pageId=9592104)

## ttpai-mybatis-starter

### v1.0.0

- 修改MyBatis默认配置,参考 [MyBatis全局配置](http://confluence.ttpai.cn/pages/viewpage.action?pageId=9591766)
- 自定义 @MapperScan 行为， Mapper 上必须增加 @Mapper 注解

### v1.0.1

- 【TODO】如果 Mapper 返回值是集合类型，当查不到数据时，返回空集合，而不是 null
  - 新增配置 `ttpai.mybatis.plugin.result.empty-collection` 默认 `true`
- 【TODO】 多数据源自动配置
  - 支持 rose-jade 命名规范
  - 跨库事务 Service 的事务管理器 和 Mapper 的 DataSource 不匹配时，事务失效，而不是使用当前事务的 DataSource