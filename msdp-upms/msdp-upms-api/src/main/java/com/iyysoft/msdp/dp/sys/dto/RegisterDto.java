package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册
 */
@Data
public class RegisterDto implements Serializable {
    @ApiModelProperty(value = "手机号", required = true, dataType = "String", example = "13505710000")
    private String mobile;
    @ApiModelProperty(value = "用户名", required = true, dataType = "String", example = "张三")
    private String userName;
    @ApiModelProperty(value = "邮箱", required = false, dataType = "String", example = "zs@iyysoft.com", notes = "为空时默认为手机号加@iyysoft.com，如13505710000@iyysoft.com")
    private String email;
    @ApiModelProperty(value = "登录名", required = false, dataType = "String", example = "zs8888", notes = "为空时默认为jc加手机号，如jc13505710000")
    private String loginName;
    @ApiModelProperty(value = "密码", required = true, dataType = "String", example = "123456")
    private String password;
    @ApiModelProperty(value = "重复密码", required = true, dataType = "String", example = "123456")
    private String rePassword;
    @ApiModelProperty(value = "手机验证码", required = true, dataType = "String", example = "1125")
    private String smsCode;
}
