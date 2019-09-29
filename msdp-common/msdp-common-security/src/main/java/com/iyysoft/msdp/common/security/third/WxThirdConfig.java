
package com.iyysoft.msdp.common.security.third;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mao.chi
 * @date 2018/8/16
 * 微信登录配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "third.wx")
public class WxThirdConfig {
    private String appid;
    private String secret;
}
