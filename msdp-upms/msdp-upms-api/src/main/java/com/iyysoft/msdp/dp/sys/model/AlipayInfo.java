package com.iyysoft.msdp.dp.sys.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AlipayInfo {
    @ApiModelProperty(value = "thirdId", required = false, dataType = "String", example = "", notes = "类型ID")
    private String thirdId;

    @ApiModelProperty(value = "openid", required = false, dataType = "String", example = "", notes = "支付宝ID")
    private String openid;

    @ApiModelProperty(value = "taobaoId", required = false, dataType = "String", example = "", notes = "淘宝ID")
    private String taobaoId;

    @ApiModelProperty(value = "sex", required = false, dataType = "Integer", example = "", notes = "性别")
    private Integer sex;

    @ApiModelProperty(value = "city", required = false, dataType = "String", example = "", notes = "城市")
    private String city;

    @ApiModelProperty(value = "province", required = false, dataType = "String", example = "", notes = "省份")
    private String province;

    @ApiModelProperty(value = "country", required = false, dataType = "String", example = "", notes = "国家")
    private String country;

    //String privilege=(String) oobj.get("privilege"); //数组

    @ApiModelProperty(value = "headImgUrl", required = false, dataType = "String", example = "", notes = "头像URL")
    private String headImgUrl;

    @ApiModelProperty(value = "userName", required = false, dataType = "String", example = "", notes = "用户名")
    private String userName;

    @ApiModelProperty(value = "userNick", required = false, dataType = "String", example = "", notes = "呢称")
    private String userNick;

    @ApiModelProperty(value = "userType", required = false, dataType = "String", example = "", notes = "用户类型")
    private String userType;

    @ApiModelProperty(value = "userStatus", required = false, dataType = "String", example = "", notes = "用户账户动态")
    private String userStatus;

    @ApiModelProperty(value = "email", required = false, dataType = "String", example = "", notes = "用户Email地址")
    private String email;

    @ApiModelProperty(value = "isCertified", required = false, dataType = "String", example = "", notes = "用户是否进行身份认证")
    private String isCertified;

    @ApiModelProperty(value = "isStudentCertified", required = false, dataType = "String", example = "", notes = "用户是否进行学生认证")
    private String isStudentCertified;


    public AlipayInfo(String thirdId, String openid, String taobaoId) {
        this.thirdId = thirdId;
        this.openid = openid;
        this.taobaoId = taobaoId;
    }
}
