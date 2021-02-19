package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.ApmArchiveVO;
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
public interface ApmArchiveMapper {
    String BASE_ALL_FIELDS = " ID, NAME, BIZ_KEY, SOURCE, TARGET, OFFSET_DAY, DSL, QUERY_DSL, AGGREGATIONS_DSL, TIME_INTERVAL, TIME_OFFSET, DISABLED, TIME_UNIT, REMARK, MODIFY_TIME, CREATE_TIME ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<ApmArchiveVO>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  APM_ARCHIVE LIMIT #{startIndex},#{pageSize}")
    List<ApmArchiveVO> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return ApmArchiveVO
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  APM_ARCHIVE WHERE ID = #{id} LIMIT 1")
    ApmArchiveVO selectById(@Param("id") Long id);

    /**
     * 根据id更新
     *
     * @param entity 实体对象ApmArchiveVO
     * @return 更新的条数
     */
    long updateById(ApmArchiveVO entity);

    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象ApmArchiveVO
     * @return List<ApmArchiveVO>
     */
    List<ApmArchiveVO> selectByEntity(ApmArchiveVO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象ApmArchiveVO
     * @return 插入成功记录数
     */
    long insertByEntity(ApmArchiveVO entity);
}
