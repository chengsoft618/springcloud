package com.iyysoft.msdp.dp.sys.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.iyysoft.msdp.dp.sys.service.VerifyService;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mao.chi
 * @date 2018/11/14
 * <p>
 * 手机验证码
 */
@RestController
@AllArgsConstructor
@RequestMapping("/verify")
@Api(value = "verify", tags = "校验码管理模块")
public class VerifyController {
    private final VerifyService verifyService;

    @GetMapping("/sms/{mobile}")
    public R sendSmsCode(@PathVariable String mobile) {
        return verifyService.sendSmsCode(mobile);
    }

    @GetMapping("/smscode/{mobile}")
    public R sendSmsCode1(@PathVariable String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return new R<>(REnum.CODE.MOBILE_ERROR);
        }
        return verifyService.smsCode(mobile);
    }

    @GetMapping("/mail/{mail}")
    public R sendMailCode(@PathVariable String mail) {
        return verifyService.sendMailCode(mail);
    }

}
