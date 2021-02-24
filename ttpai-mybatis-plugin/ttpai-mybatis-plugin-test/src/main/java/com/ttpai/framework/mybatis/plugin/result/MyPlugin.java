// package com.ttpai.framework.mybatis.plugin.result;
//
// import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
// import org.apache.ibatis.executor.resultset.ResultSetHandler;
// import org.apache.ibatis.mapping.MappedStatement;
// import org.apache.ibatis.mapping.ResultMap;
// import org.apache.ibatis.plugin.*;
// import org.apache.ibatis.reflection.MetaObject;
// import org.apache.ibatis.reflection.SystemMetaObject;
// import org.apache.ibatis.session.Configuration;
//
// import java.sql.ResultSet;
// import java.sql.ResultSetMetaData;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.LinkedHashMap;
// import java.util.List;
// import java.util.Properties;
//
// @Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
// public class MyPlugin implements Interceptor {
//
// /**
// * 这里是每次执行操作的时候，都会进行这个拦截器的方法内
// */
// public Object intercept(Invocation invocation) throws Throwable {
// List resList = new ArrayList();
// DefaultResultSetHandler defaultResultSetHandler = (DefaultResultSetHandler) invocation.getTarget();
// MetaObject metaStatementHandler = SystemMetaObject.forObject(defaultResultSetHandler);
// MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("mappedStatement");
//// 获取节点属性的集合
// List<ResultMap> resultMaps = mappedStatement.getResultMaps();
// Configuration configuration = (Configuration) metaStatementHandler.getValue("configuration");
// Class<?> resultType = resultMaps.get(0).getType();
//// 获取mybatis返回的实体类类型名
// int resultMapCount = resultMaps.size();
// if (resultMapCount > 0) {
// Statement statement = (Statement) invocation.getArgs()[0];
// ResultSet resultSet = statement.getResultSet();
// if (resultSet != null) {
//// 获得对应列名
// ResultSetMetaData rsmd = resultSet.getMetaData();
// List<String> columnList = new ArrayList<String>();
// for (int i = 1; i <= rsmd.getColumnCount(); i++) {
// columnList.add(rsmd.getColumnName(i));
// }
// while (resultSet.next()) {
// LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
// for (String colName : columnList) {
// resultMap.put(colName, resultSet.getString(colName));
//// 具体些要转换的码值这里就做个演示
// if (colName.equals("username")) {
// resultMap.put(colName, "iui");
// }
// }
// Object o = resultType.newInstance();
//// 将转换后的map转换为实体类中
// BeanUtils.populate(o, resultMap);
// resList.add(o);
// }
// return resList;
// }
// }
// return invocation.proceed();
// }
//
// /**
// * //主要是为了把这个拦截器生成一个代理放到拦截器链中
// * ^Description包装目标对象 为目标对象创建代理对象
// *
// * @Param target为要拦截的对象
// * @Return代理对象
// */
// public Object plugin(Object target) {
// System.out.println("将要包装的目标对象：" + target);
// return Plugin.wrap(target, this);
// }
//
// public void setProperties(Properties properties) {
// }
// }
