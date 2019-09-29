package com.iyysoft.msdp.dp.sys.controller;

import com.iyysoft.msdp.dp.sys.dto.RegisterDto;
import com.iyysoft.msdp.dp.sys.dto.ResetPassDto;
import com.iyysoft.msdp.dp.sys.dto.WxminiMobileDto;
import com.iyysoft.msdp.dp.sys.model.AlipayInfo;
import com.iyysoft.msdp.dp.sys.model.WechatInfo;
import com.iyysoft.msdp.dp.sys.model.WechatUserInfo;
import com.iyysoft.msdp.dp.sys.model.WxMiniInfo;
import com.iyysoft.msdp.dp.sys.service.RegService;
import com.iyysoft.msdp.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author mao.chi
 * @date 2018/11/14
 * <p>
 * 登录前处理
 */
@RestController
@AllArgsConstructor
@RequestMapping("/reg")
@Api(value = "reg", tags = "登录前处理模块，不过授权")
public class RegController {
    private final RegService regService;

    /**
     * 用户注册
     *
     * @param regDto
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "")
    public R register(@RequestBody RegisterDto regDto) {
        return regService.register(regDto);
    }

    /**
     * 密码重置
     *
     * @param passDto
     * @return
     */
    @PostMapping("/resetPassword")
    @ApiOperation(value = "密码重置", notes = "")
    public R resetPassword(@RequestBody ResetPassDto passDto) {
        return regService.resetPassword(passDto);
    }

    /**
     * 获取微信手机号
     *
     * @param mobileDto
     * @return
     */
    @PostMapping("/wxminiMobile")
    @ApiOperation(value = "获取微信手机号", notes = "")
    public R wxminiMobile(@RequestBody WxminiMobileDto mobileDto) {
        return regService.wxminiMobile(mobileDto);
    }

    /**
     * 获取微信用户信息
     *
     * @param mobileDto
     * @return
     */
    @PostMapping("/wxminiInfo")
    @ApiOperation(value = "获取微信用户信息", notes = "", response = WechatUserInfo.class)
    public R wxminiInfo(@RequestBody WxminiMobileDto mobileDto) {
        return regService.wxminiInfo(mobileDto);
    }

    /**
     * 获取微信小程序openId
     *
     * @param appId
     * @param code
     * @return
     */
    @ApiOperation(value = "获取微信小程序openId", notes = "", response = WechatUserInfo.class)
    @GetMapping("/wxmini/{appId}/{code}")
    public R<WxMiniInfo> wxmini(@PathVariable("appId") String appId, @PathVariable("code") String code) {
        return regService.wxmini(appId, code);
    }

    /**
     * 获取微信登录openId
     *
     * @param appId
     * @param code
     * @return
     */
    @ApiOperation(value = "获取微信登录openId", notes = "", response = WechatUserInfo.class)
    @GetMapping("/wechat/{appId}/{code}")
    public R<WechatInfo> wechat(@PathVariable("appId") String appId, @PathVariable("code") String code) {
        return regService.wechat(appId, code);
    }

    /**
     * 获取支付宝登录openId
     *
     * @param appId
     * @param code
     * @return
     */
    @ApiOperation(value = "获取支付宝登录openId", notes = "", response = WechatUserInfo.class)
    @GetMapping("/alipay/{appId}/{code}")
    public R<AlipayInfo> alipay(@PathVariable("appId") String appId, @PathVariable("code") String code) {
        return regService.alipay(appId, code);
    }

    /**
     * 清理缓存
     *
     * @return
     */
    @DeleteMapping("/cacheClear")
    public R cacheClear() {
        return regService.cacheClear();
    }

    @PostMapping("/resetMobile")
    @ApiOperation(value = "解绑手机号", notes = "")
    public R resetMobile(String mobile, String smsCode, String reMobile) {
        return regService.resetMobile(mobile, smsCode, reMobile);
    }
}
