package com.iyysoft.msdp.common.security.wxmini;

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
 * @date 2019/8/5
 * 第三方OPENID登录配置入口
 */
@Getter
@Setter
@Component
public class WxMiniAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationEventPublisher defaultAuthenticationEventPublisher;
    private AuthenticationSuccessHandler wxMiniLoginSuccessHandler;
    private AuthenticationFailureHandler wxMiniLoginFailureHandler;
    private MsdpUserDetailsService userDetailsService;

//	@Autowired
//	public WxMiniAuthenticationSecurityConfig(AuthenticationSuccessHandler wxMiniLoginSuccessHandler, AuthenticationFailureHandler wxMiniLoginFailureHandler, MsdpUserDetailsService userDetailsService) {
//		this.wxMiniLoginSuccessHandler = wxMiniLoginSuccessHandler;
//		this.wxMiniLoginFailureHandler = wxMiniLoginFailureHandler;
//		this.userDetailsService = userDetailsService;
//	}

    /**
     * Configure.
     *
     * @param http the http
     */
    @Override
    public void configure(HttpSecurity http) {

        WxMiniAuthenticationFilter wxMiniAuthenticationFilter = new WxMiniAuthenticationFilter();
        wxMiniAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        wxMiniAuthenticationFilter.setAuthenticationSuccessHandler(wxMiniLoginSuccessHandler);
        wxMiniAuthenticationFilter.setAuthenticationFailureHandler(wxMiniLoginFailureHandler);
        wxMiniAuthenticationFilter.setEventPublisher(defaultAuthenticationEventPublisher);
        wxMiniAuthenticationFilter.setAuthenticationEntryPoint(new ResourceAuthExceptionEntryPoint(objectMapper));


        WxMiniAuthenticationProvider wxMiniAuthenticationProvider = new WxMiniAuthenticationProvider();
        wxMiniAuthenticationProvider.setUserDetailsService(userDetailsService);
        //wxMiniAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        http.authenticationProvider(wxMiniAuthenticationProvider)
                .addFilterAfter(wxMiniAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
