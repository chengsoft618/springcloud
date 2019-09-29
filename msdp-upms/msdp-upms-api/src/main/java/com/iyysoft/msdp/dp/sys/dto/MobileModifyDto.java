package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel("修改手机")
@Data
public class MobileModifyDto {

    @ApiModelProperty("验证码")
    @NotEmpty(message = "code不能为空")
    private String code;

    @ApiModelProperty("新手机号")
    @NotEmpty(message = "mobile不能为空")
    @Pattern(regexp = "1[34578]\\d{9}", message = "mobile格式错误")
    private String mobile;

}
