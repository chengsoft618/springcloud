package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaopingan
 * @version v1.0
 * @date 9:45 2019/5/30
 */
@Data
public class ResetMobileDto implements Serializable {

    @ApiModelProperty(value = "手机号", required = true, dataType = "String", example = "13505710000")
    private String mobile;

    @ApiModelProperty(value = "验证码", required = true, dataType = "String", example = "1125")
    private String smsCode;

    @ApiModelProperty(value = "新手机号", required = true, dataType = "String", example = "15116339233")
    private String reMobile;
}
