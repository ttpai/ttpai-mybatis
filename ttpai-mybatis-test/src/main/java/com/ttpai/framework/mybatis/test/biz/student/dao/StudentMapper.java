package com.ttpai.framework.mybatis.test.biz.student.dao;

import com.ttpai.framework.mybatis.test.biz.student.model.StudentVO;
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
 * @since 2021-03-03
 */
@Mapper
public interface StudentMapper {

    String BASE_ALL_FIELDS = " ID, STUDENT_NAME, STUDENT_AGE, MODIFY_TIME, CREATE_TIME ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<StudentVO>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  STUDENT LIMIT #{startIndex},#{pageSize}")
    List<StudentVO> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return StudentVO
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  STUDENT WHERE ID = #{id} LIMIT 1")
    StudentVO selectById(@Param("id") Long id);

    /**
     * 根据id更新
     *
     * @param entity 实体对象StudentVO
     * @return 更新的条数
     */
    Long updateById(StudentVO entity);
    
    void updateAge();
    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象StudentVO
     * @return List<StudentVO>
     */
    List<StudentVO> selectByEntity(StudentVO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象StudentVO
     * @return 插入成功记录数
     */
    Long insertByEntity(StudentVO entity);
}
