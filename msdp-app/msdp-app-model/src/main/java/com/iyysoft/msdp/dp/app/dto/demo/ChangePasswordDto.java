package com.iyysoft.msdp.dp.app.dto.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改支付密码
 *
 * @author msdp code generator
 * @date 2019-10-19 15:03:23
 */
@Data
@ApiModel(value = "ChangePasswordDto", description = "修改支付密码Model")
public class ChangePasswordDto implements Serializable {

    @ApiModelProperty(value = "原密码", dataType="String", example="" )
    String oldPassword;
    @ApiModelProperty(value = "新密码", dataType="String", example="" )
    String newPassword;
    @ApiModelProperty(value = "重复密码", dataType="String", example="" )
    String rePassword;
}
