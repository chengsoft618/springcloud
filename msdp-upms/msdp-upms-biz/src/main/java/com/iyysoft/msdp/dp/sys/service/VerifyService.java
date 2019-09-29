package com.iyysoft.msdp.dp.sys.service;

import com.iyysoft.msdp.common.core.util.R;

/**
 * @author mao.chi
 * @date 2018/11/14
 */
public interface VerifyService {
    /**
     * 发送手机验证码
     *
     * @param mobile mobile
     * @return code
     */
    R<Boolean> sendSmsCode(String mobile);

    /**
     * 发送邮件验证码
     *
     * @param mail mail地址
     * @return code
     */
    R<Boolean> sendMailCode(String mail);

    /**
     * 发送手机验证码
     *
     * @param mobile mobile
     * @return code
     */
    R<Boolean> smsCode(String mobile);

}
