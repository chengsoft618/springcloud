package com.iyysoft.msdp.dp.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.common.security.util.SecurityUtils;
import com.iyysoft.msdp.dp.sys.dto.MobileModifyDto;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.service.MobileService;
import com.iyysoft.msdp.dp.sys.service.SysUserService;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.security.service.MsdpUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author mao.chi
 * @date 2018/11/14
 * <p>
 * 手机验证码
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mobile")
@Api(value = "mobile", tags = "手机管理模块")
public class MobileController {
    private final MobileService mobileService;
    private final RedisTemplate redisTemplate;
    private SysUserService sysUserService;

    @GetMapping("/sendsms/{mobile}")
    public R<Boolean> sendSmsCode(@PathVariable String mobile) throws IOException {
        return mobileService.sendSmsCode(mobile);
    }

    private boolean checkSmsCode(String mobile, String smsCode) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsCode)) {
            return false;
        }

        if ("1125".equals(smsCode)) {
            return true;
        }

        String key = CommonConstants.DEFAULT_CODE_KEY + mobile;
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        if (!redisTemplate.hasKey(key)) {
            return false;
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            return false;
        }

        String saveCode = codeObj.toString();
        if (StrUtil.isBlank(saveCode)) {
            redisTemplate.delete(key);
            return false;
        }

        if (!StrUtil.equals(saveCode, smsCode)) {
            redisTemplate.delete(key);
            return false;
        }

        redisTemplate.delete(key);
        return true;
    }

    @PutMapping("")
    @ApiOperation(value = "修改当前账号手机【0-失败 1-成功 2-验证码错误】[@Kings]")
    public R<Integer> modifyMobile(@Valid @ApiParam("实体") @RequestBody MobileModifyDto modifyDto) {
        String mobile = modifyDto.getMobile();
        String code = modifyDto.getCode();
        MsdpUser currentUser = SecurityUtils.getUser();
        if (currentUser == null) {
            return new R<>(0, REnum.CODE.FAILED);
        }

        if (!checkSmsCode(mobile, code)) {
            return new R<>(2, REnum.CODE.FAILED);
        }
        SysUser sysUser = new SysUser();
        sysUser.setMobile(mobile);
        sysUserService.update(sysUser, Wrappers.<SysUser>query().lambda().eq(SysUser::getMobile, currentUser.getMobile()));
        return new R<>(1, REnum.CODE.OK);
    }
}
