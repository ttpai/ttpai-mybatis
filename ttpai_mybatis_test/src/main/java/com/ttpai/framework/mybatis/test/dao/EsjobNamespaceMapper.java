package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.EsjobNamespaceVO;
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
public interface EsjobNamespaceMapper {
    String BASE_ALL_FIELDS = " ID, NAMESPACE, DISABLE, CREATE_TIME, MODIFY_TIME ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<EsjobNamespaceVO>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  ESJOB_NAMESPACE LIMIT #{startIndex},#{pageSize}")
    List<EsjobNamespaceVO> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return EsjobNamespaceVO
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  ESJOB_NAMESPACE WHERE ID = #{id} LIMIT 1")
    EsjobNamespaceVO selectById(@Param("id") Integer id);

    /**
     * 根据id更新
     *
     * @param entity 实体对象EsjobNamespaceVO
     * @return 更新的条数
     */
    long updateById(EsjobNamespaceVO entity);

    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象EsjobNamespaceVO
     * @return List<EsjobNamespaceVO>
     */
    List<EsjobNamespaceVO> selectByEntity(EsjobNamespaceVO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象EsjobNamespaceVO
     * @return 插入成功记录数
     */
    long insertByEntity(EsjobNamespaceVO entity);
}
