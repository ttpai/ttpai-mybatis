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
@ApiModel(value="ApmQueryTemplateBak202005071VO对象", description="")
public class ApmQueryTemplateBak202005071VO {


    @ApiModelProperty(value = "自增主键")
    private Integer id;

    @ApiModelProperty(value = "关联的BIZ_KEY")
    private String bizKey;

    @ApiModelProperty(value = "模板名")
    private String templateName;

    @ApiModelProperty(value = "查询方法  1. GET 2.MGET 3.SEARCH ")
    private String queryMethod;

    @ApiModelProperty(value = "聚合参数")
    private String aggParam;

    @ApiModelProperty(value = "子聚合名称，用于对应父聚合")
    private String subAggName;

    @ApiModelProperty(value = "子聚合参数")
    private String subAggParam;

    @ApiModelProperty(value = "查询参数")
    private String queryParam;

    @ApiModelProperty(value = "body根下面的参数")
    private String rootParam;

    @ApiModelProperty(value = "总from")
    private Integer totalFrom;

    @ApiModelProperty(value = "总大小")
    private Integer totalSize;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "实例的权重")
    private Integer instanceWeight;

    @ApiModelProperty(value = "禁止合并分支的规则")
    private String prohibitMergeRule;


    public static final String ID = "ID";

    public static final String BIZ_KEY = "BIZ_KEY";

    public static final String TEMPLATE_NAME = "TEMPLATE_NAME";

    public static final String QUERY_METHOD = "QUERY_METHOD";

    public static final String AGG_PARAM = "AGG_PARAM";

    public static final String SUB_AGG_NAME = "SUB_AGG_NAME";

    public static final String SUB_AGG_PARAM = "SUB_AGG_PARAM";

    public static final String QUERY_PARAM = "QUERY_PARAM";

    public static final String ROOT_PARAM = "ROOT_PARAM";

    public static final String TOTAL_FROM = "TOTAL_FROM";

    public static final String TOTAL_SIZE = "TOTAL_SIZE";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String INSTANCE_WEIGHT = "INSTANCE_WEIGHT";

    public static final String PROHIBIT_MERGE_RULE = "PROHIBIT_MERGE_RULE";

}
