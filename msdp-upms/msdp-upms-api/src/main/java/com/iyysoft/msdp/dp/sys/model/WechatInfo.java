package com.iyysoft.msdp.dp.sys.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WechatInfo {
    @ApiModelProperty(value = "thirdId", required = false, dataType = "String", example = "", notes = "")
    private String thirdId;

    @ApiModelProperty(value = "openid", required = false, dataType = "String", example = "", notes = "")
    private String openid;

    @ApiModelProperty(value = "accessToken", required = false, dataType = "String", example = "", notes = "")
    private String accessToken;

    public WechatInfo(String thirdId, String openid, String accessToken) {
        this.thirdId = thirdId;
        this.openid = openid;
        this.accessToken = accessToken;
    }
}
