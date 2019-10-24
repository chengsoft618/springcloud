package com.iyysoft.msdp.dp.app.dto.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 修改支付密码
 *
 * @author msdp code generator
 * @date 2019-10-19 15:03:23
 */
@Data
@ApiModel(value = "ResetPasswordDto", description = "重置支付密码Model")
public class ResetPasswordDto implements Serializable {

    @ApiModelProperty(value = "姓名", dataType="String", example="张三" )
    String userName;
    @ApiModelProperty(value = "证件号码", dataType="String", example="33012219700101001" )
    String idno;
    @ApiModelProperty(value = "短信验证码", dataType="String", example="1125" )
    String smsCode;
    @ApiModelProperty(value = "新密码", dataType="String", example="123456" )
	@Pattern (regexp = "\\d{6}",message = "newPassword格式错误")
    String newPassword;
    @ApiModelProperty(value = "重复密码", dataType="String", example="123456" )
	@Pattern (regexp = "\\d{6}",message = "rePassword格式错误")
    String rePassword;
}
