
package com.iyysoft.msdp.common.security.wxmini;

import lombok.Getter;
import lombok.Setter;


/**
 * @author mao.chi
 * @date 2018/1/9
 * 登录令牌
 */
public class WxMiniAuthentication {
    @Getter
    @Setter
    String appId;

    @Getter
    @Setter
    String code;

    @Getter
    @Setter
    String userName;


    public WxMiniAuthentication(String appId, String code, String userName) {
        this.appId = appId;
        this.code = code;
        this.userName = userName;
    }
}
