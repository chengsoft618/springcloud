package com.iyysoft.msdp.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.iyysoft.msdp.dp.sys.dto.UserInfo;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.feign.RemoteUserService;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.security.exception.BindServerErrorException;
import com.iyysoft.msdp.common.security.exception.NotBindException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户详细信息
 * @author mao.chi
 */
@Slf4j
@Service
@AllArgsConstructor
public class MsdpUserDetailsServiceImpl implements MsdpUserDetailsService {

    private final RemoteUserService remoteUserService;

    /**
     * 用户密码登录
     *
     * @param userName 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String userName) {
//		Cache cache = cacheManager.getCache("user_details");
//		if (cache != null && cache.get(userName) != null) {
//			return (MsdpUser) cache.get(userName).get();
//		}
        R<UserInfo> result = remoteUserService.info(userName, SecurityConstants.FROM_IN);
        UserDetails userDetails = getUserDetails(result);
//		cache.put(userName, userDetails);
        return userDetails;
    }

    /**
     * 根据微信code 登录
     * @param code
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByWxMini(String appId, String code, String userName) {
        return getUserDetails(remoteUserService.loadUserByWxMini(appId, code, userName, SecurityConstants.FROM_IN));
    }

    /**
     * 根据微信code 登录
     * @param thirdId
     * @param openId
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByOpenId(String thirdId, String openId, String userName) {
        return getUserDetails(remoteUserService.loadUserByOpenId(thirdId, openId, userName, SecurityConstants.FROM_IN));
    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return
     */
    private UserDetails getUserDetails(R<UserInfo> result) {
        if (result == null) {
            throw new UsernameNotFoundException("调用错误");
        } else if (result.getData() == null || result.getCode() != 0) {
            if (result.getCode() == REnum.CODE.NOT_BINDING.getCode()) {
                throw new NotBindException(result.getMsg());
            } else if (result.getCode() == REnum.CODE.NETWORK_ERROR.getCode()) {
                throw new BindServerErrorException(result.getMsg());
            } else {
                throw new UsernameNotFoundException(result.getMsg());
            }
        }
        UserInfo info = result.getData();
        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(info.getRoles())) {
            // 获取角色
            Arrays.stream(info.getRoles()).forEach(roleId -> dbAuthsSet.add(SecurityConstants.ROLE + roleId));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

        }
        Collection<? extends GrantedAuthority> authorities
                = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUser user = info.getSysUser();
        boolean enabled = StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL);
        // 构造security用户
        return new MsdpUser(user.getUserId(), user.getDeptId(), user.getTenantId(), user.getUserName(), user.getMobile(), user.getMobile(), SecurityConstants.BCRYPT + user.getPassword(), enabled,
                true, true, !CommonConstants.STATUS_LOCK.equals(user.getLockFlag()), authorities);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
//		Cache cache = cacheManager.getCache("user_details");
//		if (cache != null && cache.get(mobile) != null) {
//			return (MsdpUser) cache.get(mobile).get();
//		}
        log.info("loadUserByMobile==================");
        R<UserInfo> result = remoteUserService.info(mobile, SecurityConstants.FROM_IN);
        UserDetails userDetails = getUserDetails(result);
//		cache.put(mobile, userDetails);
        return userDetails;
    }

}
