package com.iyysoft.msdp.dp.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.mapper.SysUserMapper;
import com.iyysoft.msdp.dp.sys.service.MobileService;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.core.util.sms.SMSUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public class MobileServiceImpl implements MobileService {

    private final RedisTemplate msdpRedisTemplate;
    private final SysUserMapper userMapper;


    /**
     * 发送手机验证码
     * TODO: 调用短信网关发送验证码,测试返回前端
     *
     * @param mobile mobile
     * @return code
     */
    @Override
    public R<Boolean> sendSmsCode(String mobile) throws IOException {
        List<SysUser> userList = userMapper.selectList(Wrappers
                .<SysUser>query().lambda()
                .eq(SysUser::getMobile, mobile));

        if (CollUtil.isEmpty(userList)) {
            log.info("手机号未注册:{}", mobile);
            return new R<>(REnum.CODE.MOBILE_NOT_REGISTER);
        }

        Object codeObj = msdpRedisTemplate.opsForValue().get(CommonConstants.DEFAULT_CODE_KEY + mobile);

        if (codeObj != null) {
            return new R<>(REnum.CODE.OK);
            //iyysoftRedisTemplate.delete(CommonConstants.DEFAULT_CODE_KEY + mobile);
        }

        String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));
        log.debug("手机号生成验证码成功:{},{}", mobile, code);
        msdpRedisTemplate.opsForValue().set(
                CommonConstants.DEFAULT_CODE_KEY + mobile
                , code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
        SMSUtils.sendMovek(mobile, "【微悦科技】验证码是" + code);
        return new R<>(REnum.CODE.OK);
    }
}
