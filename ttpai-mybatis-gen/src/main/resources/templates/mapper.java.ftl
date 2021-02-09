package ${package.Mapper};

import ${package.Entity}.${entity};
import org.apache.ibatis.annotations.Mapper;
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


}
</#if>
