package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 取微信手机号
 */
@Data
public class WxminiMobileDto implements Serializable {
    @ApiModelProperty(value = "小程序获取的加密数据", required = true, dataType = "String", example = "", notes = "encryptedData 值由前端通过 wx.getUserInfo(object) 返回值中 encryptedData 字段获取")
    private String encryptedData;

    @ApiModelProperty(value = "小程序获取的向量IV", required = true, dataType = "String", example = "", notes = "iv 值由前端通过 wx.getUserInfo(object) 返回值中 iv 字段获取")
    private String iv;

    @ApiModelProperty(value = "小程序获取的KEY", required = true, dataType = "String", example = "", notes = "sessionKey 值可通过 https://api.weixin.qq.com/sns/jscode2session?appid=... 中的 session_key 获取")
    private String sessionKey;
}
