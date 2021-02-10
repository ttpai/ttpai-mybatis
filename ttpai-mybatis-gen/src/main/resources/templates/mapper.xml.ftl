<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>

<#list table.fields as field>
<#--主键-->
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
        <#assign keyFieldName="${field.name}"/>
    </#if>
</#list>

<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.columnName},
</#list>
        ${table.fieldNames}
    </sql>

    <!-- 通用【查询】条件  ！不包含主键字段 -->
    <sql id="Base_Where">
        <#list table.fields as field>
        <#if !field.keyFlag>
        <if test="${field.propertyName} != null "> AND ${field.name} = <#noparse>#</#noparse>{${field.propertyName}}</if>
        </#if>
        </#list>
    </sql>

    <!-- 通用【更新】条件  ！不包含主键字段 -->
    <sql id="Base_Update">
        <#list table.fields as field>
        <#if !field.keyFlag>
        <if test="${field.propertyName} != null ">${field.name} = <#noparse>#</#noparse>{${field.propertyName}},</if>
        </#if>
        </#list>
    </sql>


    <select id="selectByEntity" resultMap="BaseResultMap" parameterType="${package.Entity}.${entity}">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table.name}
        <trim prefix="WHERE" prefixOverrides="AND"><include refid="Base_Where"/></trim> ;
    </select>


<#if keyPropertyName??>
    <update id="updateBy${keyPropertyName?cap_first}" parameterType="${package.Entity}.${entity}">
        UPDATE ${table.name}
        <trim prefix="SET" prefixOverrides=","><include refid="Base_Update"/></trim>
        where ${keyFieldName} = <#noparse>#</#noparse>{${keyPropertyName}};
    </update>
</#if>



</#if>
</mapper>
