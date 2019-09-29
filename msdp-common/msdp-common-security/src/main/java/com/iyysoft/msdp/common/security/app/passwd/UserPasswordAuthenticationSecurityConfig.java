package com.iyysoft.msdp.common.security.app.passwd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyysoft.msdp.common.security.component.ResourceAuthExceptionEntryPoint;
import com.iyysoft.msdp.common.security.service.MsdpUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author mao.chi
 * @date 2018/8/5
 * 手机号登录配置入口
 */
@Getter
@Setter
@Component
public class UserPasswordAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthenticationEventPublisher defaultAuthenticationEventPublisher;
    private AuthenticationSuccessHandler userPasswordLoginSuccessHandler;
    private AuthenticationFailureHandler userPasswordLoginFailureHandler;
    private MsdpUserDetailsService userDetailsService;

//	public UserPasswordAuthenticationSecurityConfig(AuthenticationSuccessHandler userPasswordLoginSuccessHandler, AuthenticationFailureHandler userPasswordLoginFailureHandler, MsdpUserDetailsService userDetailsService) {
//		this.userPasswordLoginSuccessHandler = userPasswordLoginSuccessHandler;
//		this.userPasswordLoginFailureHandler = userPasswordLoginFailureHandler;
//		this.userDetailsService = userDetailsService;
//	}

    @Override
    public void configure(HttpSecurity http) throws Exception {
        UserPasswordAuthenticationFilter userPasswordAuthenticationFilter = new UserPasswordAuthenticationFilter();
        userPasswordAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        userPasswordAuthenticationFilter.setAuthenticationSuccessHandler(userPasswordLoginSuccessHandler);
        userPasswordAuthenticationFilter.setEventPublisher(defaultAuthenticationEventPublisher);
        userPasswordAuthenticationFilter.setAuthenticationEntryPoint(new ResourceAuthExceptionEntryPoint(objectMapper));

        UserPasswordAuthenticationProvider userPasswordAuthenticationProvider = new UserPasswordAuthenticationProvider();
        userPasswordAuthenticationProvider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(userPasswordAuthenticationProvider)
                .addFilterAfter(userPasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
