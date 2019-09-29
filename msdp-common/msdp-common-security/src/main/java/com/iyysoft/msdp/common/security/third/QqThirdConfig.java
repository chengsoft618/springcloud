
package com.iyysoft.msdp.common.security.third;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mao.chi
 * @date 2018/8/16
 * qq登录配置信息
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "third.qq")
public class QqThirdConfig {
    private String appid;
    private String secret;
}
