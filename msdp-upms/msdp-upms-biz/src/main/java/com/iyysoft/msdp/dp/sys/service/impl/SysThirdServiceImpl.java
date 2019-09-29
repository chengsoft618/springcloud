package com.iyysoft.msdp.dp.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.common.security.util.MsdpSecurityUtils;
import com.iyysoft.msdp.dp.sys.dto.UserInfo;
import com.iyysoft.msdp.dp.sys.entity.SysThird;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.entity.UserThird;
import com.iyysoft.msdp.dp.sys.handler.LoginHandler;
import com.iyysoft.msdp.dp.sys.mapper.SysThirdMapper;
import com.iyysoft.msdp.dp.sys.mapper.SysUserMapper;
import com.iyysoft.msdp.dp.sys.mapper.UserThirdMapper;
import com.iyysoft.msdp.dp.sys.service.SysThirdService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author mao.chi
 * @date 2018年08月16日
 */
@Slf4j
@AllArgsConstructor
@Service("sysThirdService")
public class SysThirdServiceImpl extends ServiceImpl<SysThirdMapper, SysThird> implements SysThirdService {
    private final Map<String, LoginHandler> loginHandlerMap;
    private final CacheManager cacheManager;
    private final SysUserMapper sysUserMapper;
    private final UserThirdMapper userThirdMapper;

    /**
     * 绑定社交账号
     *
     * @param type type
     * @param code code
     * @return
     */
    @Override
    public Boolean bindThird(String type, String code) {
        String identify = loginHandlerMap.get(type).identify(code);

        SysUser sysUser = sysUserMapper.selectById(MsdpSecurityUtils.getUser().getUserId());
        UserThird userThird = new UserThird();
        userThird.setUserId(sysUser.getUserId());
        userThird.setThirdId("1");
        userThird.setOpenId(identify);
        userThirdMapper.insert(userThird);

        //sysUserMapper.updateById(sysUser);
        //更新緩存
        //cacheManager.getCache("user_details").evict(sysUser.getUsername());
        return Boolean.TRUE;
    }

    /**
     * 根据入参查询用户信息
     *
     * @param inStr TYPE@code
     * @return
     */
    @Override
    public UserInfo getUserInfo(String inStr) {
        String[] inStrs = inStr.split("@");
        String type = inStrs[0];
        String loginStr = inStrs[1];
        return loginHandlerMap.get(type).handle(loginStr);
    }

    @Override
    public SysThird getByTypeAppId(String type, String appId) {
        SysThird third = baseMapper.selectOne(
                Wrappers.<SysThird>query().lambda()
                        .eq(SysThird::getType, type)
                        .eq(SysThird::getAppId, appId));
        return third;
    }
}
