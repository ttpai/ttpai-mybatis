package com.ttpai.framework.mybatis.test.biz.student.model;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lilin.tan@ttpai
 * @since 2021-03-03
 */
@Getter
@Setter
@ToString
@ApiModel(value="StudentVO对象", description="")
public class StudentVO {


    @ApiModelProperty(value = "自增主键")
    private Long id;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "学生年龄")
    private Integer studentAge;

    @ApiModelProperty(value = "记录修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "记录创建时间")
    private Date createTime;


    public static final String ID = "ID";

    public static final String STUDENT_NAME = "STUDENT_NAME";

    public static final String STUDENT_AGE = "STUDENT_AGE";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_TIME = "CREATE_TIME";

}
