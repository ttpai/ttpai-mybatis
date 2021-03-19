/**
 * 该包主要用于选择是否使用 MybatisAutoConfiguration
 *
 * @see com.ttpai.framework.mybatis.autoconfigure.datasource.choose.MyBatisAutoConfigurationExcludeFilter
 *      默认排除对 MybatisAutoConfiguration 的自动配置，后续会根据条件选择是否重新导入
 *      <p>
 * @see com.ttpai.framework.mybatis.autoconfigure.datasource.choose.MyBatisAutoConfigurationImporter
 *      如果 DataSource 的实例只有一个，则重新导入 MybatisAutoConfiguration
 */
package com.ttpai.framework.mybatis.autoconfigure.datasource.choose;
