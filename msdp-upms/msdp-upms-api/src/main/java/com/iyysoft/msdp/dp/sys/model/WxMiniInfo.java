package com.iyysoft.msdp.dp.sys.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WxMiniInfo {
    @ApiModelProperty(value = "thirdId", required = false, dataType = "String", example = "", notes = "")
    private String thirdId;

    @ApiModelProperty(value = "openid", required = false, dataType = "String", example = "", notes = "")
    private String openid;

    @ApiModelProperty(value = "sessionKey", required = false, dataType = "String", example = "", notes = "")
    private String sessionKey;

    public WxMiniInfo(String thirdId, String openid, String sessionKey) {
        this.thirdId = thirdId;
        this.openid = openid;
        this.sessionKey = sessionKey;
    }
}
