package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.UserVO;
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
public interface UserMapper {

    String BASE_ALL_FIELDS = " ID, NAME, AGE, EMAIL ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<UserVO>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  USER LIMIT #{startIndex},#{pageSize}")
    List<UserVO> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return UserVO
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  USER WHERE ID = #{id} LIMIT 1")
    UserVO selectById(@Param("id") Long id);

    /**
     * 根据id更新
     *
     * @param entity 实体对象UserVO
     * @return 更新的条数
     */
    Long updateById(UserVO entity);

    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象UserVO
     * @return List<UserVO>
     */
    List<UserVO> selectByEntity(UserVO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象UserVO
     * @return 插入成功记录数
     */
    Long insertByEntity(UserVO entity);
}
