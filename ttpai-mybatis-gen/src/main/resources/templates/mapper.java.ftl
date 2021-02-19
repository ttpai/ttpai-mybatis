package ${package.Mapper};

import ${package.Entity}.${entity};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} {
<#if table.fieldNames??>
    String BASE_ALL_FIELDS = " ${table.fieldNames} ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<${entity}>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  ${table.name} LIMIT <#noparse>#</#noparse>{startIndex},<#noparse>#</#noparse>{pageSize}")
    List<${entity}> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

<#list table.fields as field>
  <#if field.keyFlag><#--主键-->
    /**
     * 根据${field.propertyName}查询
     *
     * @param ${field.propertyName} 主键
     * @return ${entity}
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  ${table.name} WHERE ${field.annotationColumnName} = <#noparse>#</#noparse>{${field.propertyName}} LIMIT 1")
    ${entity} selectBy${field.propertyName?cap_first}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName});

    /**
     * 根据${field.propertyName}更新
     *
     * @param entity 实体对象${entity}
     * @return 更新的条数
     */
    long updateBy${field.propertyName?cap_first}(${entity} entity);
  </#if>
</#list>

    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象${entity}
     * @return List<${entity}>
     */
    List<${entity}> selectByEntity(${entity} entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象${entity}
     * @return 插入成功记录数
     */
    long insertByEntity(${entity} entity);
</#if>
}
</#if>
