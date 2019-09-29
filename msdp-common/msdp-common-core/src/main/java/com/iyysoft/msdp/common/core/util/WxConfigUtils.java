
package com.iyysoft.msdp.common.core.util;

import com.iyysoft.msdp.common.core.config.WxMiniProgConfig;
import com.iyysoft.msdp.common.core.config.WxMpConfig;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.ContextLoader;


@Slf4j
@UtilityClass
public class WxConfigUtils {
    // 微信小程序配置
    private static WxMiniProgConfig wxMiniProgConfig;
    // 微信公众号配置
    private static WxMpConfig wxMpConfig;

    private static void init() {
        if (wxMiniProgConfig == null) {
            wxMiniProgConfig = ContextLoader.getCurrentWebApplicationContext()
                    .getBean(WxMiniProgConfig.class);
        }
        if (wxMpConfig == null) {
            wxMpConfig = ContextLoader.getCurrentWebApplicationContext()
                    .getBean(WxMpConfig.class);
        }
    }

    public static WxMiniProgConfig getWxMiniProgConfig() {
        init();
        return wxMiniProgConfig;
    }

    public static WxMpConfig getWxMpConfig() {
        init();
        return wxMpConfig;
    }

}
