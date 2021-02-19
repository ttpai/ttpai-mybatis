package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.ApmQueryTemplateBak202005071VO;
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
public interface ApmQueryTemplateBak202005071Mapper {
    String BASE_ALL_FIELDS = " ID, BIZ_KEY, TEMPLATE_NAME, QUERY_METHOD, AGG_PARAM, SUB_AGG_NAME, SUB_AGG_PARAM, QUERY_PARAM, ROOT_PARAM, TOTAL_FROM, TOTAL_SIZE, MODIFY_TIME, CREATE_TIME, INSTANCE_WEIGHT, PROHIBIT_MERGE_RULE ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<ApmQueryTemplateBak202005071VO>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  APM_QUERY_TEMPLATE_BAK_20200507_1 LIMIT #{startIndex},#{pageSize}")
    List<ApmQueryTemplateBak202005071VO> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);


    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象ApmQueryTemplateBak202005071VO
     * @return List<ApmQueryTemplateBak202005071VO>
     */
    List<ApmQueryTemplateBak202005071VO> selectByEntity(ApmQueryTemplateBak202005071VO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象ApmQueryTemplateBak202005071VO
     * @return 插入成功记录数
     */
    long insertByEntity(ApmQueryTemplateBak202005071VO entity);
}
