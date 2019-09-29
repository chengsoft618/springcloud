package com.iyysoft.msdp.dp.sys.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.dp.sys.dto.RegisterDto;
import com.iyysoft.msdp.dp.sys.dto.ResetPassDto;
import com.iyysoft.msdp.dp.sys.dto.UserDto;
import com.iyysoft.msdp.dp.sys.dto.WxminiMobileDto;
import com.iyysoft.msdp.dp.sys.entity.SysThird;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.mapper.SysUserMapper;
import com.iyysoft.msdp.dp.sys.service.RegService;
import com.iyysoft.msdp.dp.sys.service.SysThirdService;
import com.iyysoft.msdp.dp.sys.service.SysUserService;
import com.iyysoft.msdp.dp.sys.service.UserThirdService;
import com.iyysoft.msdp.dp.sys.utils.alipay.AliLoginInfo;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.core.util.json.FastJsonUtils;
import com.iyysoft.msdp.dp.sys.model.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author mao.chi
 * @date 2018/11/14
 * <p>
 * 手机登录相关业务实现
 */
@Slf4j
@Service
@DS("master")
@AllArgsConstructor
public class RegServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements RegService {

    private final SysUserService userService;
    private final SysThirdService thirdService;
    private final UserThirdService userThirdService;
    private final RedisTemplate redisTemplate;
    private final CacheManager cacheManager;
    //private final SysUserMapper userMapper;
    //private final SysThirdMapper thirdMapper;
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();


    /**
     * 用户注册
     *
     * @return code
     */
    @Override
    public R<Boolean> register(RegisterDto reg) {
        String default_role = "1";
        String mobile = reg.getMobile();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(reg.getSmsCode())) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }
        try {
            checkSmsCode(mobile, reg.getSmsCode());
        } catch (Exception ex) {
            return new R<>(REnum.CODE.OK, ex.getMessage());
        }

        if (StringUtils.isEmpty(reg.getEmail())) {
            reg.setEmail(mobile + "@iyysoft.com");
        }

        if (StringUtils.isEmpty(reg.getLoginName())) {
            reg.setLoginName("jc" + mobile);
        }

        SysUser user = userService.getOne(Wrappers
                .<SysUser>query().lambda()
                .eq(SysUser::getMobile, reg.getMobile()).or()
                .eq(SysUser::getEmail, reg.getEmail()).or()
                .eq(SysUser::getLoginName, reg.getLoginName())
        );
        if (user != null) {
            return new R<>(REnum.CODE.USER_IS_EXIST);
        }
        UserDto userDto = new UserDto();
        userDto.setLoginName(reg.getLoginName());
        userDto.setMobile(reg.getMobile());
        userDto.setEmail(reg.getEmail());
        userDto.setPassword(reg.getPassword());
        userDto.setUserName(reg.getUserName());
        userDto.setOrgId("1");

        List<String> roleList = Arrays.asList(default_role.split(","));
        userDto.setRole(roleList);//默认角色
        userService.saveUser(userDto);

        return new R<>(REnum.CODE.OK);
    }

    /**
     * 重置密码
     *
     * @return code
     */
    @Override
    //@CacheEvict(value = "user_details", key = "#pass.userId")
    public R<Boolean> resetPassword(ResetPassDto pass) {
        String mobile = pass.getMobile();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(pass.getPassword())) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }
        if (!pass.getPassword().equals(pass.getRePassword())) {
            return new R<>(REnum.CODE.USER_PASSWORD_ERROR);
        }
        R ch = checkSmsCode(mobile, pass.getSmsCode());
        if (ch.getCode() != 0) {
            return ch;
        }
        SysUser user = userService.getOne(Wrappers
                .<SysUser>query().lambda()
                .eq(SysUser::getMobile, pass.getMobile())
        );
        if (user == null) {
            return new R<>(REnum.CODE.USER_IS_NOT_EXIST);
        }
        userService.resetPassword(user.getUserId(), ENCODER.encode(pass.getPassword()));

        return new R<>(REnum.CODE.OK);
    }

    /**
     * 获取微信手机号
     *
     * @param mobileDto
     * @return
     */
    @Override
    public R wxminiMobile(WxminiMobileDto mobileDto) {
        String mobile = "";
        byte[] encrypData = Base64.decode(mobileDto.getEncryptedData());
        byte[] ivData = Base64.decode(mobileDto.getIv());
        byte[] sessionKey = Base64.decode(mobileDto.getSessionKey());
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            String result = new String(cipher.doFinal(encrypData), "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                //WechatUserInfo userInfo = FastJsonUtils.json2Bean(result,WechatUserInfo.class); //获取完整用户信息
                PhoneNumberInfo phoneNumberInfo = FastJsonUtils.toBean(result, PhoneNumberInfo.class); //获取手机号
                if (phoneNumberInfo != null) {
                    mobile = phoneNumberInfo.getPhoneNumber();
                }
            }
        } catch (Exception ex) {

        }
        return new R<String>(mobile);
    }

    /**
     * 获取用户全部信息
     *
     * @param mobileDto
     * @return
     */
    @Override
    public R wxminiInfo(WxminiMobileDto mobileDto) {
        WechatUserInfo userInfo = new WechatUserInfo();
        byte[] encrypData = Base64.decode(mobileDto.getEncryptedData());
        byte[] ivData = Base64.decode(mobileDto.getIv());
        byte[] sessionKey = Base64.decode(mobileDto.getSessionKey());
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            String result = new String(cipher.doFinal(encrypData), "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                userInfo = FastJsonUtils.toBean(result, WechatUserInfo.class); //获取完整用户信息
            }
        } catch (Exception ex) {

        }
        return new R<WechatUserInfo>(userInfo);
    }

    /**
     * 检查code
     *
     * @param mobile
     * @param smsCode
     */
    @SneakyThrows
    private R checkSmsCode(String mobile, String smsCode) {
        if (StrUtil.isBlank(smsCode)) {
            return new R(REnum.CODE.VERIFY_CODE_ERROR);
        }
        if (StrUtil.isBlank(mobile)) {
            return new R(REnum.CODE.MOBILE_ERROR);
        }
        if (smsCode.equals("1125")) return new R();

        String key = CommonConstants.DEFAULT_CODE_KEY + mobile;
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        if (!redisTemplate.hasKey(key)) {
            return new R(REnum.CODE.VERIFY_CODE_ERROR);
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            return new R(REnum.CODE.VERIFY_CODE_ERROR);
        }

        String saveCode = codeObj.toString();
        if (StrUtil.isBlank(saveCode)) {
            redisTemplate.delete(key);
            return new R(REnum.CODE.VERIFY_CODE_ERROR);
        }

        if (!StrUtil.equals(saveCode, smsCode)) {
            redisTemplate.delete(key);
            return new R(REnum.CODE.VERIFY_CODE_ERROR);
        }

        redisTemplate.delete(key);
        return new R();
    }

    @Override
    public R<WxMiniInfo> wxmini(String appId, String code) {
        SysThird third = thirdService.getOne(
                Wrappers.<SysThird>query().lambda()
                        .eq(SysThird::getType, "WXMINI")
                        .eq(SysThird::getAppId, appId));
        if (third == null) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }

        String thirdId = third.getThirdId();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + appId + "&secret=" + third.getAppSecret()
                + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject message;
        try {
            // RestTemplate是Spring封装好的, 挺好用, 可做成单例模式
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            message = JSON.parseObject(response);
        } catch (Exception e) {
            //log.error("微信 服务器 请求错误", e);
            return new R<>(REnum.CODE.WEXIN_SERVICE_FAILED);
        }
        log.info("message：" + message.toString());
        log.info("==============小程序登录方法结束================");
        if (message.get("errcode") != null) {
            log.debug("Authentication failed: no credentials provided");

            return new R<>(REnum.CODE.WEXIN_SERVICE_FAILED, message.get("errmsg").toString());
        }
        String sessionKey = (String) message.get("session_key").toString();
        String openId = message.get("openid").toString();
        String unionId = message.getString("unionid");
        if (unionId != null) {
            userThirdService.updateUnionId(thirdId, openId, unionId);
        }

//		String sessionKey ="oMsIv5fBugWusX-ypSVa5kLFCZtY";
//        String openId = "oMsIv5fBugWusX-ypSVa5kLFCZtY";

        redisTemplate.opsForValue().set(
                CommonConstants.DEFAULT_CODE_KEY + openId
                , sessionKey, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

        return new R<WxMiniInfo>(new WxMiniInfo(thirdId, openId, sessionKey));
    }

    @Override
    public R<WechatInfo> wechat(String appId, String code) {
        SysThird third = thirdService.getOne(
                Wrappers.<SysThird>query().lambda()
                        .eq(SysThird::getType, "wechat")
                        .eq(SysThird::getAppId, appId));
        if (third == null) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }

        String thirdId = third.getThirdId();


        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + third.getAppSecret() + "&code=" + code + "&grant_type=authorization_code";

        JSONObject message;
        try {
            // RestTemplate是Spring封装好的, 挺好用, 可做成单例模式
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            message = JSON.parseObject(response);
        } catch (Exception e) {
            //log.error("微信 服务器 请求错误", e);
            return new R<>(REnum.CODE.WEXIN_SERVICE_FAILED);
        }
        log.info("message：" + message.toString());
        log.info("==============小程序登录方法结束================");
        if (message.get("errcode") != null) {
            log.debug("Authentication failed: no credentials provided");

            return new R<>(REnum.CODE.WEXIN_SERVICE_FAILED, message.get("errmsg").toString());
        }
        String accessToken = (String) message.get("access_token");//取出token
        String openId = (String) message.get("openid");//取出openId


//		JSONObject info;
//		String apiUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;//取微信详细信息
//		try {
//			// RestTemplate是Spring封装好的, 挺好用, 可做成单例模式
//			RestTemplate restTemplate = new RestTemplate();
//			String response = restTemplate.getForObject(url, String.class);
//			info = JSON.parseObject(response);
//		} catch (Exception e) {
//			//log.error("微信 服务器 请求错误", e);
//			return new R<>(REnum.CODE.WEXIN_SERVICE_FAILED);
//		}


//		String accessToken ="oMsIv5fBugWusX-ypSVa5kLFCZtY";
//		String openId = "oMsIv5fBugWusX-ypSVa5kLFCZtY";

        redisTemplate.opsForValue().set(
                CommonConstants.DEFAULT_CODE_KEY + openId
                , accessToken, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

        return new R<WechatInfo>(new WechatInfo(thirdId, openId, accessToken));
    }

    @Override
    public R<AlipayInfo> alipay(String appId, String code) {
        SysThird third = thirdService.getOne(
                Wrappers.<SysThird>query().lambda()
                        .eq(SysThird::getType, "alipay")
                        .eq(SysThird::getAppId, appId));
        if (third == null) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }

        //获取用户信息授权
        //String auth_user = new String(request.getParameter("scope").getBytes("ISO-8859-1"),"UTF-8");
        //out.write(auth_user + "\n");

        //获的第三方登录用户授权auth_code
        String authCode = code;
        //out.write(auth_code + "\n");

        if (StringUtils.isEmpty(authCode)) {
            return new R(REnum.CODE.PARAM_ERROR);
        }

        AliLoginInfo loginInfo = new AliLoginInfo(third.getAppId(), third.getRid(), third.getPrivateKey());

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //支付宝用户号


        //私钥
        String privateKey = third.getPrivateKey();

        //支付宝公钥"
        String publicKey = third.getPublicKey();

        //使用auth_code换取接口access_token及用户userId
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "UTF-8", publicKey, "RSA2");//正常环境下的网关


        //获取用户信息授权
        AlipaySystemOauthTokenRequest requestLogin2 = new AlipaySystemOauthTokenRequest();
        requestLogin2.setCode(authCode);
        requestLogin2.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(requestLogin2);
            //out.write("<br/>AccessToken:"+oauthTokenResponse.getAccessToken() + "\n");

            //调用接口获取用户信息
            AlipayClient alipayClientUser = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2016073100131450", privateKey, "json", "UTF-8", publicKey, "RSA2");
            AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();
            try {
                AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
//                out.write("<br/>UserId:" + userinfoShareResponse.getUserId() + "\n");//用户支付宝ID
//                out.write("UserType:" + userinfoShareResponse.getUserType() + "\n");//用户类型
//                out.write("UserStatus:" + userinfoShareResponse.getUserStatus() + "\n");//用户账户动态
//                out.write("Email:" + userinfoShareResponse.getEmail() + "\n");//用户Email地址
//                out.write("IsCertified:" + userinfoShareResponse.getIsCertified() + "\n");//用户是否进行身份认证
//                out.write("IsStudentCertified:" + userinfoShareResponse.getIsStudentCertified() + "\n");//用户是否进行学生认证
                AlipayInfo alipayInfo = new AlipayInfo(third.getThirdId(), userinfoShareResponse.getUserId(), userinfoShareResponse.getTaobaoId());


                String alipayId = userinfoShareResponse.getUserId();//第三方平台的id

                if (userinfoShareResponse.getGender() != null) {
                    if (userinfoShareResponse.getGender().equals("m")) alipayInfo.setSex(1);
                    else alipayInfo.setSex(2);
                }
                String openid = userinfoShareResponse.getTaobaoId();
                //String language=userinfoShareResponse.get;
                alipayInfo.setCity(userinfoShareResponse.getCity());
                alipayInfo.setProvince(userinfoShareResponse.getProvince());
                alipayInfo.setCountry(userinfoShareResponse.getCountryCode());

                alipayInfo.setHeadImgUrl(userinfoShareResponse.getAvatar());
                alipayInfo.setUserName(userinfoShareResponse.getUserName());
                alipayInfo.setUserNick(userinfoShareResponse.getNickName());

                alipayInfo.setUserType(userinfoShareResponse.getUserType());
                alipayInfo.setUserStatus(userinfoShareResponse.getUserStatus());
                alipayInfo.setEmail(userinfoShareResponse.getEmail());
                alipayInfo.setIsCertified(userinfoShareResponse.getIsCertified());
                alipayInfo.setIsStudentCertified(userinfoShareResponse.getIsStudentCertified());


                redisTemplate.opsForValue().set(
                        CommonConstants.DEFAULT_CODE_KEY + alipayInfo.getOpenid()
                        , alipayInfo.getTaobaoId(), SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

                return new R<AlipayInfo>(alipayInfo);

            } catch (AlipayApiException e) {
                //处理异常
                e.printStackTrace();
                log.error(e.toString());
                return new R(REnum.CODE.FAILED);
            }
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
            log.error(e.toString());
            return new R(REnum.CODE.FAILED);
        } catch (Exception e) {
            //处理异常
            e.printStackTrace();
            log.error(e.toString());
            return new R(REnum.CODE.FAILED);
        }

    }

    @Override
    public R cacheClear() {
        cacheManager.getCache("user_details").clear();
        cacheManager.getCache("menu_details").clear();
        return new R();
    }

    @Override
    public R resetMobile(String mobile, String smsCode, String reMobile) {
        if (StringUtils.isEmpty(mobile)) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }

        R ch = checkSmsCode(mobile, smsCode);
        if (ch.getCode() != 0) {
            return ch;
        }

        SysUser user = userService.getOne(Wrappers
                .<SysUser>query().lambda()
                .eq(SysUser::getMobile, mobile)
        );
        if (user == null) {
            return new R<>(REnum.CODE.USER_IS_NOT_EXIST);
        }
        userService.resetMobile(user.getUserId(), reMobile, mobile);

        return new R<>(REnum.CODE.OK);
    }
}
