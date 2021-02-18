package com.ttpai.framework.mybatis.test.model;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 业务类型注册表
 * </p>
 *
 * @author lilin.tan@ttpai
 * @since 2021-02-18
 */
@Getter
@Setter
@ToString
@ApiModel(value="ApmBizVO对象", description="业务类型注册表")
public class ApmBizVO {


    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "业务名")
    private String bizName;

    @ApiModelProperty(value = "业务KEY")
    private String bizKey;

    @ApiModelProperty(value = "日志保留天数")
    private Integer remainDay;

    @ApiModelProperty(value = "日志保留数据量")
    private Integer remainCount;

    @ApiModelProperty(value = "ES的索引名称")
    private String esIndex;

    @ApiModelProperty(value = "日志滚动策略：0：不滚动 1：按天 2：按数据量")
    private Integer rollStrategy;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;


    public static final String ID = "ID";

    public static final String BIZ_NAME = "BIZ_NAME";

    public static final String BIZ_KEY = "BIZ_KEY";

    public static final String REMAIN_DAY = "REMAIN_DAY";

    public static final String REMAIN_COUNT = "REMAIN_COUNT";

    public static final String ES_INDEX = "ES_INDEX";

    public static final String ROLL_STRATEGY = "ROLL_STRATEGY";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String REMARK = "REMARK";

}
