package com.iyysoft.msdp.dp.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.sys.entity.UserThird;
import com.iyysoft.msdp.dp.sys.service.UserThirdService;
import com.iyysoft.msdp.common.core.config.WxMiniProgConfig;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.core.util.WxConfigUtils;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import com.iyysoft.msdp.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * 第三方登录
 *
 * @author mao.chi
 * @date 2019-04-17 15:52:27
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/userthird")
@Api(value = "userthird", tags = "第三方登录管理模块")
public class UserThirdController {

    private final UserThirdService userThirdService;

    /**
     * 分页查询
     *
     * @param page      分页对象
     * @param userThird 第三方登录
     * @return
     */
    @GetMapping("/page")
    public R getUserThirdPage(Page page, UserThird userThird) {
        return new R<>(userThirdService.page(page, Wrappers.query(userThird)));
    }


    /**
     * 通过id查询第三方登录
     *
     * @param thirdType id
     * @return R
     */
    @GetMapping("/{thirdType}")
    public R getById(@PathVariable("thirdType") Integer thirdType) {
        return new R<>(userThirdService.getById(thirdType));
    }

    /**
     * 获取指定用户全部信息
     *
     * @return 用户信息
     */
    @Inner
    @GetMapping("/bind")
    public R thirdBind(@PathVariable String thirdId, @PathVariable String openId, @PathVariable String userId) {
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(openId)
                || com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(thirdId))
            return new R<>(REnum.CODE.PARAM_ERROR);
        UserThird userThird = userThirdService.getUserInfo(thirdId, openId);
        if (userThird == null) {
            userThird.setThirdId(thirdId);
            userThird.setOpenId(openId);
            userThird.setUserId(userId);
            userThirdService.save(userThird);
        } else {
            userThird.setThirdId(thirdId);
            userThird.setOpenId(openId);
            userThird.setUserId(userId);
            userThirdService.updateById(userThird);
        }
        return new R<>();
    }


    /**
     * 新增第三方登录
     *
     * @param userThird 第三方登录
     * @return R
     */
    @SysLog("新增第三方登录")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('UserThird_userthird_add')")
    public R save(@RequestBody UserThird userThird) {
        return new R<>(userThirdService.save(userThird));
    }

    /**
     * 修改第三方登录
     *
     * @param userThird 第三方登录
     * @return R
     */
    @SysLog("修改第三方登录")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('UserThird_userthird_edit')")
    public R updateById(@RequestBody UserThird userThird) {
        return new R<>(userThirdService.updateById(userThird));
    }

    /**
     * 通过id删除第三方登录
     *
     * @param thirdType id
     * @return R
     */
    @SysLog("删除第三方登录")
    @DeleteMapping("/{thirdType}")
    @PreAuthorize("@pms.hasPermission('UserThird_userthird_del')")
    public R removeById(@PathVariable Integer thirdType) {
        return new R<>(userThirdService.removeById(thirdType));
    }


    /**
     * 根据CODE获取微信令牌
     *
     * @param code
     * @return
     */
    @GetMapping("/wxcode/{code}")
    public R wxcode(@PathVariable String code) {

        String thirdType = "104";

        log.info("==============小程序登录方法开始================");
        WxMiniProgConfig properties = WxConfigUtils.getWxMiniProgConfig();
        String url = properties.getInterfaceUrl() + "/sns/jscode2session?appid="
                + properties.getAppId() + "&secret=" + properties.getAppSecret()
                + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject message;
        try {
// RestTemplate是Spring封装好的, 挺好用, 可做成单例模式
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            message = JSON.parseObject(response);
        } catch (Exception e) {
            log.error("微信 服务器 请求错误", e);
            message = new JSONObject();
        }
        log.info("message：" + message.toString());
        log.info("==============小程序登录方法结束================");
        if (message.get("errcode") != null) {
            return new R<>(REnum.CODE.FAILED, String.format("CODE错误 %s", message.toString()));
        }
        String sessionKey = (String) message.get("session_key").toString();

        Integer sex = (Integer) message.get("sex");
        String openid = message.get("openid").toString();
        String language = message.get("language").toString();
        String city = message.get("city").toString();
        String province = message.get("province").toString();
        String country = message.get("country").toString();
        //String privilege = message.get("privilege").toString(); //数组

        String headimgurl = message.get("headimgurl").toString();
        String unionid = message.get("unionid").toString();
        String username = message.get("nickname").toString();
        String weixinId = unionid;//第三方平台的id

        if (StringUtils.isEmpty(weixinId)) {
            weixinId = openid;
            unionid = openid;
        }
        Map<String, String> map = new HashMap<String, String>();
        UserThird userThird = userThirdService.getUserInfo(thirdType, weixinId);
        map.put("thirdId", weixinId);
        map.put("thirdType", String.valueOf(thirdType));
        if (userThird == null) {
            map.put("userFlag", "0");
            //return new R<>(Boolean.FALSE, String.format("用户信息为空 %s", username));
        }
        map.put("userFlag", "1");
        return new R<>(map);
    }


}
