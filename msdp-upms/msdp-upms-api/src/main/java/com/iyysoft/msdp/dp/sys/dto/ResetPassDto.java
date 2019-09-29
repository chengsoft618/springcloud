package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 重置密码
 */
@Data
public class ResetPassDto implements Serializable {
    @ApiModelProperty(value = "手机号", required = true, dataType = "String", example = "13505710000")
    private String mobile;
    @ApiModelProperty(value = "验证码", required = true, dataType = "String", example = "1125")
    private String smsCode;
    @ApiModelProperty(value = "密码", required = true, dataType = "String", example = "123456")
    private String password;
    @ApiModelProperty(value = "重复密码", required = true, dataType = "String", example = "123456")
    private String rePassword;
}
