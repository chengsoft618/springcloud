package com.iyysoft.msdp.dp.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.mapper.SysUserMapper;
import com.iyysoft.msdp.dp.sys.service.VerifyService;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.core.util.sms.SMSUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
@AllArgsConstructor
public class VerifyServiceImpl implements VerifyService {
    private final RedisTemplate redisTemplate;
    private final SysUserMapper userMapper;


    /**
     * 发送手机验证码
     * TODO: 调用短信网关发送验证码,测试返回前端
     *
     * @param mobile mobile
     * @return code
     */
    @Override
    public R<Boolean> sendSmsCode(String mobile) {
        List<SysUser> userList = userMapper.selectList(Wrappers
                .<SysUser>query().lambda()
                .eq(SysUser::getMobile, mobile));

        if (CollUtil.isEmpty(userList)) {
            log.info("手机号未注册:{}", mobile);
            return new R<>(REnum.CODE.MOBILE_NOT_EXIST);
        }

        Object codeObj = redisTemplate.opsForValue().get(CommonConstants.DEFAULT_CODE_KEY + mobile);
        if (codeObj != null) {
            redisTemplate.delete(CommonConstants.DEFAULT_CODE_KEY + mobile);
        }
        String vcode = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));


        String smsText = "【甬安租房注册】您的验证码是" + vcode + "。如非本人操作，请忽略本短信，有效期5分钟。";
        try {
            Integer rc = SMSUtils.sendMovek(mobile, smsText);
            if (rc.intValue() != 0) {
                return new R<>(REnum.CODE.SMS_FAILED);
            }
            redisTemplate.opsForValue().set(
                    CommonConstants.DEFAULT_CODE_KEY + mobile
                    , vcode, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
            log.debug("手机号生成验证码成功:{}", mobile);
            return new R<>(REnum.CODE.OK);
        } catch (Exception ex) {
            return new R<>(REnum.CODE.SMS_FAILED);
        }
    }

    /**
     * 发送手机验证码
     * TODO: 调用短信网关发送验证码,测试返回前端
     *
     * @param mobile mobile
     * @return code
     */
    @Override
    public R<Boolean> smsCode(String mobile) {

        Object codeObj = redisTemplate.opsForValue().get(CommonConstants.DEFAULT_CODE_KEY + mobile);
        if (codeObj != null) {
            redisTemplate.delete(CommonConstants.DEFAULT_CODE_KEY + mobile);
        }
        String vcode = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));


        String smsText = "【甬安租房】您的验证码是" + vcode + "。如非本人操作，请忽略本短信，有效期5分钟。";
        try {
            Integer rc = SMSUtils.sendMovek(mobile, smsText);
            if (rc.intValue() != 0) {
                return new R<>(REnum.CODE.SMS_FAILED);
            }
            redisTemplate.opsForValue().set(
                    CommonConstants.DEFAULT_CODE_KEY + mobile
                    , vcode, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
            log.debug("手机号生成验证码成功:{}", mobile);
            return new R<>(REnum.CODE.OK);
        } catch (Exception ex) {
            return new R<>(REnum.CODE.SMS_FAILED);
        }
    }

    /**
     * 发送邮件验证码
     * TODO: 调用邮件网关发送验证码,测试返回前端
     *
     * @param mail 地址
     * @return code
     */
    @Override
    public R<Boolean> sendMailCode(String mail) {
        List<SysUser> userList = userMapper.selectList(Wrappers
                .<SysUser>query().lambda()
                .eq(SysUser::getEmail, mail));

        if (CollUtil.isEmpty(userList)) {
            log.info("邮箱未注册:{}", mail);
            return new R<>(REnum.CODE.MAIL_NOT_EXIST);
        }

        Object codeObj = redisTemplate.opsForValue().get(CommonConstants.DEFAULT_CODE_KEY + mail);
        if (codeObj != null) {
            redisTemplate.delete(CommonConstants.DEFAULT_CODE_KEY + mail);
        }

        String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));
        log.debug("邮箱生成验证码成功:{},{}", mail, code);
        redisTemplate.opsForValue().set(
                CommonConstants.DEFAULT_CODE_KEY + mail
                , code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
        return new R<>(REnum.CODE.OK);
    }

}
