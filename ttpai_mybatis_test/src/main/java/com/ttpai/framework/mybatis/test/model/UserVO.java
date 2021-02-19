package com.ttpai.framework.mybatis.test.model;

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
@ApiModel(value="UserVO对象", description="")
public class UserVO {


    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "邮箱")
    private String email;


    public static final String ID = "ID";

    public static final String NAME = "NAME";

    public static final String AGE = "AGE";

    public static final String EMAIL = "EMAIL";

}
