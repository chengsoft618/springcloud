package com.iyysoft.msdp.dp.sys.service;

import com.iyysoft.msdp.common.core.util.R;

import java.io.IOException;

/**
 * @author mao.chi
 * @date 2018/11/14
 */
public interface MobileService {
    /**
     * 发送手机验证码
     *
     * @param mobile mobile
     * @return code
     */
    R<Boolean> sendSmsCode(String mobile) throws IOException;
}
