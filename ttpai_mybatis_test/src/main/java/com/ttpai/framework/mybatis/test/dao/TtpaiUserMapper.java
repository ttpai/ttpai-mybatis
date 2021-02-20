package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.TtpaiUserVO;
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
 * @since 2021-02-20
 */
@Mapper
public interface TtpaiUserMapper {

    String BASE_ALL_FIELDS = " ID, USER_NAME, AGE, EMAIL, MODIFY_TIME, CREATE_TIME ";

    /**
     * 分页查询（无条件）
     *
     * @param startIndex 起始位置
     * @param pageSize 页面大小
     * @return List<TtpaiUserVO>
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  TTPAI_USER LIMIT #{startIndex},#{pageSize}")
    List<TtpaiUserVO> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return TtpaiUserVO
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  TTPAI_USER WHERE ID = #{id} LIMIT 1")
    TtpaiUserVO selectById(@Param("id") Long id);

    /**
     * 根据id更新
     *
     * @param entity 实体对象TtpaiUserVO
     * @return 更新的条数
     */
    Long updateById(TtpaiUserVO entity);

    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象TtpaiUserVO
     * @return List<TtpaiUserVO>
     */
    List<TtpaiUserVO> selectByEntity(TtpaiUserVO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象TtpaiUserVO
     * @return 插入成功记录数
     */
    Long insertByEntity(TtpaiUserVO entity);
}
