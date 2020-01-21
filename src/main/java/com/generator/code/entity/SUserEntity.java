package com.generator.code.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
* @Description:SUserEntity
* @Param:
* @return:
* @Author: wangliang
* @Date: 2020-01-20
*/
@Entity
@Table(name = "s_user", schema = "spring_cloud_demo", catalog = "")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class SUserEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    @ApiModelProperty(value = "主键")
    private Long id; //主键

    @Basic
    @Column(name = "national")
    @ApiModelProperty(value = "国家")
    private String national; //国家

    @Basic
    @Column(name = "name")
    @NotNull
    @ApiModelProperty(value = "姓名")
    private String name; //姓名

    @Basic
    @Column(name = "password")
    @NotNull
    @ApiModelProperty(value = "密码")
    private String password; //密码

    @Basic
    @Column(name = "age")
    @NotNull
    @ApiModelProperty(value = "年龄", example = "0")
    private Integer age; //年龄

    @Basic
    @Column(name = "sex")
    @NotNull
    @ApiModelProperty(value = "性别：0为男，1为女")
    private Boolean sex; //性别：0为男，1为女

    @Basic
    @Column(name = "address")
    @ApiModelProperty(value = "地址")
    private String address; //地址

    @Basic
    @Column(name = "qq")
    @ApiModelProperty(value = "QQ号", example = "0")
    private Integer qq; //QQ号

    @Basic
    @Column(name = "wechat")
    @ApiModelProperty(value = "微信号")
    private String wechat; //微信号

    @Basic
    @Column(name = "phone")
    @NotNull
    @ApiModelProperty(value = "手机号")
    private String phone; //手机号

    @Basic
    @Column(name = "email")
    @ApiModelProperty(value = "邮箱")
    private String email; //邮箱

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}