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
@ApiModel(value="EsjobNamespaceVO对象", description="")
public class EsjobNamespaceVO {


    @ApiModelProperty(value = "主键ID")
    private Integer id;

    private String namespace;

    @ApiModelProperty(value = "默认是0，禁用的是1")
    private Boolean disable;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;


    public static final String ID = "ID";

    public static final String NAMESPACE = "NAMESPACE";

    public static final String DISABLE = "DISABLE";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String MODIFY_TIME = "MODIFY_TIME";

}
