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

    String BASE_ALL_FIELDS = " ${table.fieldNames} ";

<#list table.fields as field>
  <#if field.keyFlag><#--主键-->
    /**
     * 根据${field.propertyName}查询
     *
     * @param ${field.propertyName} 主键
     * @return ${entity}
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  ${table.name} WHERE ${field.annotationColumnName} = <#noparse>#</#noparse>{${field.propertyName}}")
    ${entity} selectAllBy${field.propertyName?cap_first}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName});
  </#if><#else>
</#list>

    /**
     * 查询所有记录
     *
     * @return List<${entity}>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  ${table.name} ")
    List<${entity}> selectAll();
}
</#if>
