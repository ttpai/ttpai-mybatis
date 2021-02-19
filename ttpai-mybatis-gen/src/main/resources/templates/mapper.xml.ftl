<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">
<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>


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

<#if baseColumnList && table.fieldNames??>
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

    <!-- 通用【更新】条件  ！不包含主键、CREATE_TIME、MODIFY_TIME字段 -->
    <sql id="Base_Update">
        <#list table.fields as field>
        <#if !field.keyFlag && field.name != "CREATE_TIME" && field.name != "MODIFY_TIME">
        <if test="${field.propertyName} != null ">${field.name} = <#noparse>#</#noparse>{${field.propertyName}},</if>
        </#if>
        </#list>
    </sql>


<#list table.fields as field>
<#--主键-->
    <#if field.keyFlag>
        <#assign propertyName ="${field.propertyName}"/>
        <#assign primaryKeyName ="${field.name}"/>
    </#if>
    <#if field.name == "CREATE_TIME">
        <#assign createTime="${field.name}"/>
    </#if>
    <#if field.name == "MODIFY_TIME">
        <#assign modifyTime="${field.name}"/>
    </#if>
</#list>

<#--根据主键更新-->
    <#if primaryKeyName??>
    <update id="updateBy${propertyName?cap_first}" parameterType="${package.Entity}.${entity}">
        UPDATE ${table.name}
        <trim prefix="SET" prefixOverrides=","><include refid="Base_Update"/></trim>
        where ${primaryKeyName} = <#noparse>#</#noparse>{${propertyName}};
    </update>
    </#if>

    <insert id="insertByEntity" parameterType="${package.Entity}.${entity}">
        INSERT INTO  ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides="," >
        <#list table.fields as field><#if !field.keyFlag && field.name != "CREATE_TIME" && field.name != "MODIFY_TIME">${field.name},</#if></#list>
        </trim>

        <trim prefix="VALUES (" suffix=")" suffixOverrides="," >
        <#list table.fields as field><#if !field.keyFlag && field.name != "CREATE_TIME" && field.name != "MODIFY_TIME"> <#noparse>#</#noparse>{${field.propertyName}},</#if></#list>
        </trim>
    </insert>

    <select id="selectByEntity" resultMap="BaseResultMap" parameterType="${package.Entity}.${entity}">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${table.name}
        <trim prefix="WHERE" prefixOverrides="AND"><include refid="Base_Where"/></trim> ;
    </select>




</#if>
</mapper>
