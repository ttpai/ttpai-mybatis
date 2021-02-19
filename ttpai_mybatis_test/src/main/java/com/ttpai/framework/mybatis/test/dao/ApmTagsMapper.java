package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.ApmTagsVO;
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
public interface ApmTagsMapper {
    String BASE_ALL_FIELDS = " ID, APP_ID, TAG_KEY, TAG_VALUE, TAG_TYPE, MODIFY_TIME, CREATE_TIME ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<ApmTagsVO>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  APM_TAGS LIMIT #{startIndex},#{pageSize}")
    List<ApmTagsVO> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return ApmTagsVO
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  APM_TAGS WHERE ID = #{id} LIMIT 1")
    ApmTagsVO selectById(@Param("id") Long id);

    /**
     * 根据id更新
     *
     * @param entity 实体对象ApmTagsVO
     * @return 更新的条数
     */
    long updateById(ApmTagsVO entity);

    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象ApmTagsVO
     * @return List<ApmTagsVO>
     */
    List<ApmTagsVO> selectByEntity(ApmTagsVO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象ApmTagsVO
     * @return 插入成功记录数
     */
    long insertByEntity(ApmTagsVO entity);
}
