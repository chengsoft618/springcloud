package com.iyysoft.msdp.dp.sys.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mao.chi
 * @date 2017/10/29
 */
@Data
public class ThirdInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * thirdId
     */
    private String thirdId;
    /**
     * appId
     */
    private String appId;
    /**
     * appSecret
     */
    private String appSecret;
    /**
     * URL
     */
    private String redirectUrl;

    /**
     * 名称
     */
    private String name;

    /**
     * 原始ID
     */
    private String rid;

    /**
     * componentAppid
     */
    private String componentAppid;

    /**
     * refreshToken
     */
    private String refreshToken;


}
