package com.ttpai.framework.mybatis.test.model;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lilin.tan@ttpai
 * @since 2021-02-20
 */
@Getter
@Setter
@ToString
@ApiModel(value = "TtpaiUserVO对象", description = "")
public class TtpaiUserVO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "记录修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "记录创建时间")
    private Date createTime;

    public static final String ID = "ID";

    public static final String USER_NAME = "USER_NAME";

    public static final String AGE = "AGE";

    public static final String EMAIL = "EMAIL";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

}
