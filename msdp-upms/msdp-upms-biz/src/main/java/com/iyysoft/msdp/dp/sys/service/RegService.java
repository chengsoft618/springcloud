package com.iyysoft.msdp.dp.sys.service;

import com.iyysoft.msdp.dp.sys.dto.RegisterDto;
import com.iyysoft.msdp.dp.sys.dto.ResetPassDto;
import com.iyysoft.msdp.dp.sys.dto.WxminiMobileDto;
import com.iyysoft.msdp.dp.sys.model.AlipayInfo;
import com.iyysoft.msdp.dp.sys.model.WechatInfo;
import com.iyysoft.msdp.dp.sys.model.WxMiniInfo;
import com.iyysoft.msdp.common.core.util.R;

/**
 * @author mao.chi
 * @date 2018/11/14
 */
public interface RegService {

    R register(RegisterDto regDto);

    R resetPassword(ResetPassDto passDto);

    R wxminiMobile(WxminiMobileDto mobileDto);

    R wxminiInfo(WxminiMobileDto mobileDto);

    R<WxMiniInfo> wxmini(String appId, String code);

    R<WechatInfo> wechat(String appId, String code);

    R<AlipayInfo> alipay(String appId, String code);

    R cacheClear();

    R resetMobile(String mobile, String smsCode, String reMobile);
}
