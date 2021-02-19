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
@ApiModel(value="ApmTagsVO对象", description="")
public class ApmTagsVO {


    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "appId 项目id")
    private Integer appId;

    @ApiModelProperty(value = "标签key")
    private String tagKey;

    @ApiModelProperty(value = "区分当前TAG_KEY状态 :"1"(已修复) "2"(未修复) "3"(已忽略)")
    private String tagValue;

    @ApiModelProperty(value = "用于区分调用端，调用端可自定义:"1"(前端) "2"(IOS) "3"(Android) 其余业务方自定义")
    private String tagType;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    public static final String ID = "ID";

    public static final String APP_ID = "APP_ID";

    public static final String TAG_KEY = "TAG_KEY";

    public static final String TAG_VALUE = "TAG_VALUE";

    public static final String TAG_TYPE = "TAG_TYPE";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

}
