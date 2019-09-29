package com.iyysoft.msdp.dp.sys.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "微信用户信息", description = "解密获取用户信息时返回")
public class WechatUserInfo {
    @ApiModelProperty(value = "用户的唯一标识", required = false, dataType = "String", example = "", notes = "")
    private String openid;//用户的唯一标识
    @ApiModelProperty(value = "用户昵称", required = false, dataType = "String", example = "", notes = "")
    private String nickname;//用户昵称
    @ApiModelProperty(value = "用户的性别", required = false, dataType = "String", example = "1", notes = "值为1时是男性，值为2时是女性，值为0时是未知")
    private Integer sex;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    @ApiModelProperty(value = "省份", required = false, dataType = "String", example = "浙江", notes = "用户个人资料填写的省份")
    private String province;//用户个人资料填写的省份
    @ApiModelProperty(value = "城市", required = false, dataType = "String", example = "杭州", notes = "普通用户个人资料填写的城市")
    private String city;//普通用户个人资料填写的城市
    @ApiModelProperty(value = "国家", required = false, dataType = "String", example = "CN", notes = "国家，如中国为CN")
    private String country;//国家，如中国为CN
    @ApiModelProperty(value = "用户头像", required = false, dataType = "String", example = "", notes = "用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。")
    private String headimgurl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    @ApiModelProperty(value = "用户特权信息", required = false, dataType = "String", example = "", notes = "用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）")
    private String privilege;//用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    @ApiModelProperty(value = "放平台帐号ID", required = false, dataType = "String", example = "", notes = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
    private String unionid;//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    @ApiModelProperty(value = "key", required = false, dataType = "String", example = "", notes = "")
    private String sessionKey;
    @ApiModelProperty(value = "unionId", required = false, dataType = "String", example = "", notes = "")
    private String unionId;

    @Override
    public String toString() {
        return "WechatUserInfoVO{" +
                "openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", privilege='" + privilege + '\'' +
                ", unionid='" + unionid + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", unionId='" + unionId + '\'' +
                '}';
    }
}
