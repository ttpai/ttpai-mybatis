package com.ttpai.framework.mybatis.test.model;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lilin.tan@ttpai
 * @since 2021-02-19
 */
@Getter
@Setter
@ToString
@ApiModel(value="ApmArchiveVO对象", description="")
public class ApmArchiveVO {


    @ApiModelProperty(value = "自增主键")
    private Long id;

    @ApiModelProperty(value = "配置名")
    private String name;

    @ApiModelProperty(value = "对应biz表，方便对es索引管理")
    private String bizKey;

    @ApiModelProperty(value = "源表 表名= 源表字段_时间格式")
    private String source;

    @ApiModelProperty(value = "目的表 表名=目的表字段	")
    private String target;

    private Integer offsetDay;

    private String dsl;

    @ApiModelProperty(value = "查询的dsl")
    private String queryDsl;

    @ApiModelProperty(value = "聚合的dsl")
    private String aggregationsDsl;

    @ApiModelProperty(value = "归档区间")
    private Integer timeInterval;

    @ApiModelProperty(value = "偏移量")
    private Integer timeOffset;

    @ApiModelProperty(value = "当前配置是否禁用  0代表false，1代表true")
    private Integer disabled;

    @ApiModelProperty(value = "hour/day   区分当前归档配置按天或按小时执行")
    private String timeUnit;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "最后修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "数据创建时间")
    private Date createTime;


    public static final String ID = "ID";

    public static final String NAME = "NAME";

    public static final String BIZ_KEY = "BIZ_KEY";

    public static final String SOURCE = "SOURCE";

    public static final String TARGET = "TARGET";

    public static final String OFFSET_DAY = "OFFSET_DAY";

    public static final String DSL = "DSL";

    public static final String QUERY_DSL = "QUERY_DSL";

    public static final String AGGREGATIONS_DSL = "AGGREGATIONS_DSL";

    public static final String TIME_INTERVAL = "TIME_INTERVAL";

    public static final String TIME_OFFSET = "TIME_OFFSET";

    public static final String DISABLED = "DISABLED";

    public static final String TIME_UNIT = "TIME_UNIT";

    public static final String REMARK = "REMARK";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

}
