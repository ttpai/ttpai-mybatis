package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.ApmQueryTemplateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lilin.tan@ttpai
 * @since 2021-02-19
 */
@Mapper
public interface ApmQueryTemplateMapper {
    String BASE_ALL_FIELDS = " ID, BIZ_KEY, TEMPLATE_NAME, QUERY_METHOD, AGG_PARAM, SUB_AGG_NAME, SUB_AGG_PARAM, QUERY_PARAM, ROOT_PARAM, TOTAL_FROM, TOTAL_SIZE, MODIFY_TIME, CREATE_TIME, REMARK ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<ApmQueryTemplateVO>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  APM_QUERY_TEMPLATE LIMIT #{startIndex},#{pageSize}")
    List<ApmQueryTemplateVO> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return ApmQueryTemplateVO
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  APM_QUERY_TEMPLATE WHERE ID = #{id} LIMIT 1")
    ApmQueryTemplateVO selectById(@Param("id") Integer id);

    /**
     * 根据id更新
     *
     * @param entity 实体对象ApmQueryTemplateVO
     * @return 更新的条数
     */
    long updateById(ApmQueryTemplateVO entity);

    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象ApmQueryTemplateVO
     * @return List<ApmQueryTemplateVO>
     */
    List<ApmQueryTemplateVO> selectByEntity(ApmQueryTemplateVO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象ApmQueryTemplateVO
     * @return 插入成功记录数
     */
    long insertByEntity(ApmQueryTemplateVO entity);
}
