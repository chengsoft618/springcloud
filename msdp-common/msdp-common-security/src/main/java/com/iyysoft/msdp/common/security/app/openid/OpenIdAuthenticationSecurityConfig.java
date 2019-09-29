package com.iyysoft.msdp.common.security.app.openid;

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
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationEventPublisher defaultAuthenticationEventPublisher;
    private AuthenticationSuccessHandler openIdLoginSuccessHandler;
    private AuthenticationFailureHandler openIdLoginFailureHandler;
    private MsdpUserDetailsService userDetailsService;

//	public OpenIdAuthenticationSecurityConfig(AuthenticationSuccessHandler openIdLoginSuccessHandler, AuthenticationFailureHandler openIdLoginFailureHandler, MsdpUserDetailsService userDetailsService) {
//		this.openIdLoginSuccessHandler = openIdLoginSuccessHandler;
//		this.openIdLoginFailureHandler = openIdLoginFailureHandler;
//		this.userDetailsService = userDetailsService;
//	}

    /**
     * Configure.
     *
     * @param http the http
     */
    @Override
    public void configure(HttpSecurity http) {

        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(openIdLoginSuccessHandler);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(openIdLoginFailureHandler);
        openIdAuthenticationFilter.setEventPublisher(defaultAuthenticationEventPublisher);
        openIdAuthenticationFilter.setAuthenticationEntryPoint(new ResourceAuthExceptionEntryPoint(objectMapper));

        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        //openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        http.authenticationProvider(openIdAuthenticationProvider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
