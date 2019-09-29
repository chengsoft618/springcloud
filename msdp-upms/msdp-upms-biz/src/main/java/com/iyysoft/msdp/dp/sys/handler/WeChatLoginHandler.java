package com.iyysoft.msdp.dp.sys.handler;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.dp.sys.dto.UserInfo;
import com.iyysoft.msdp.dp.sys.entity.SysThird;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.entity.UserThird;
import com.iyysoft.msdp.dp.sys.mapper.SysThirdMapper;
import com.iyysoft.msdp.dp.sys.service.SysUserService;
import com.iyysoft.msdp.dp.sys.service.UserThirdService;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.LoginTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mao.chi
 * @date 2018/11/18
 */
@Slf4j
@Component("WX")
@AllArgsConstructor
public class WeChatLoginHandler extends AbstractLoginHandler {

    private final UserThirdService userThirdService;
    private final SysUserService sysUserService;
    private final SysThirdMapper sysThirdMapper;

    /**
     * 微信登录传入code
     * <p>
     * 通过code 调用qq 获取唯一标识
     *
     * @param code
     * @return
     */
    @Override
    public String identify(String code) {
        SysThird condition = new SysThird();
        condition.setType(LoginTypeEnum.WECHAT.getType());
        SysThird third = sysThirdMapper.selectOne(new QueryWrapper<>(condition));

        String url = String.format(SecurityConstants.WX_AUTHORIZATION_CODE_URL
                , third.getAppId(), third.getAppSecret(), code);
        String result = HttpUtil.get(url);
        log.debug("微信响应报文:{}", result);

        Object obj = JSONUtil.parseObj(result).get("openid");
        return obj.toString();
    }

    /**
     * openId 获取用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public UserInfo info(String openId) {
        UserThird userThird = userThirdService
                .getOne(Wrappers.<UserThird>query()
                        .lambda().eq(UserThird::getThirdId, "1").eq(UserThird::getThirdId, openId));

        if (userThird == null) {
            log.info("微信未绑定:{}", openId);
            return null;
        }

        SysUser user = sysUserService
                .getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getUserId, userThird.getUserId()));
        if (user == null) {
            log.info("微信用户不存在:{}", openId);
            return null;
        }
        return sysUserService.findUserInfo(user);
    }
}
