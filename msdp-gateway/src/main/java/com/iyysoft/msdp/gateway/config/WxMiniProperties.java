package com.iyysoft.msdp.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

// 微信小程序配置
@Configuration
@RefreshScope
@Data
@ConfigurationProperties(prefix = "msdp.wx-mini-prog")
public class WxMiniProperties {

    private String appId;
    private String appSecret;
    private String interfaceUrl;

    // 请求的网址
    //public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    // 固定参数
    //public static final String WX_LOGIN_GRANT_TYPE = "authorization_code";
}
