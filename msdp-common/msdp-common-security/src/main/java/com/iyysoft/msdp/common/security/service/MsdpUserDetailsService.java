package com.iyysoft.msdp.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author mao.chi
 * @date 2018/8/15
 */
public interface MsdpUserDetailsService extends UserDetailsService {

    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;

    UserDetails loadUserByWxMini(String appId, String code, String userName) throws UsernameNotFoundException;

    UserDetails loadUserByOpenId(String thirdId, String openId, String userName) throws UsernameNotFoundException;
}
