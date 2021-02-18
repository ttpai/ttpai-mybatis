package com.ttpai.framework.mybatis.test.dao;

import com.ttpai.framework.mybatis.test.model.ApmBizVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * <p>
 * 业务类型注册表 Mapper 接口
 * </p>
 *
 * @author lilin.tan@ttpai
 * @since 2021-02-18
 */
@Mapper
public interface ApmBizMapper {

    String BASE_ALL_FIELDS = " ID, BIZ_NAME, BIZ_KEY, REMAIN_DAY, REMAIN_COUNT, ES_INDEX, ROLL_STRATEGY, MODIFY_TIME, CREATE_TIME, REMARK ";

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return ApmBizVO
     */
    @Select("SELECT" + BASE_ALL_FIELDS + " FROM  APM_BIZ WHERE ID = #{id} LIMIT 1")
    ApmBizVO selectById(@Param("id") Long id);

    /**
     * 根据id更新
     *
     * @param entity 实体对象ApmBizVO
     * @return 更新的条数
     */
    long updateById(ApmBizVO entity);

    /**
     * 根据传入实体对象查询
     *
     * @param entity 实体对象ApmBizVO
     * @return List<ApmBizVO>
     */
    List<ApmBizVO> selectByEntity(ApmBizVO entity);


    /**
     * 新增（不包括主键、createDate、modifyDate）
     *
     * @param entity 实体对象ApmBizVO
     * @return 插入成功记录数
     */
    long insertByEntity(ApmBizVO entity);
}
