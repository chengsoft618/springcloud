package com.iyysoft.msdp.dp.sys.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统社交登录账号表
 *
 * @author mao.chi
 * @date 2018-08-16 21:30:41
 */
@Data
public class ThirdDto implements Serializable {

    private String thirdId;
    /**
     * 类型
     */
    private String type;
    /**
     * 描述
     */
    private String remark;
    /**
     * appid
     */
    private String appId;
    /**
     * app_secret
     */
    private String appSecret;
    /**
     * 回调地址
     */
    private String redirectUrl;

    /**
     * 租户ID
     */
    private String tenantId;

}
