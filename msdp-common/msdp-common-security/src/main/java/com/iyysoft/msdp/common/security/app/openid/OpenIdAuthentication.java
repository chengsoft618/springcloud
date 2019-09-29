
package com.iyysoft.msdp.common.security.app.openid;

import lombok.Getter;
import lombok.Setter;


/**
 * @author mao.chi
 * @date 2019/1/9
 * 登录令牌
 */
public class OpenIdAuthentication {
    @Getter
    @Setter
    String thirdId;

    @Getter
    @Setter
    String openId;

    @Getter
    @Setter
    String userName;


    public OpenIdAuthentication(String thirdId, String openId, String userName) {
        this.thirdId = thirdId;
        this.openId = openId;
        this.userName = userName;
    }
}
