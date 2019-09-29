package com.iyysoft.msdp.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.security.app.openid.OpenIdAuthenticationSecurityConfig;
import com.iyysoft.msdp.common.security.app.passwd.UserPasswordAuthenticationSecurityConfig;
import com.iyysoft.msdp.common.security.handler.MobileLoginFailureHandler;
import com.iyysoft.msdp.common.security.handler.MobileLoginSuccessHandler;
import com.iyysoft.msdp.common.security.handler.OpenIdLoginFailureHandler;
import com.iyysoft.msdp.common.security.handler.OpenIdLoginSuccessHandler;
import com.iyysoft.msdp.common.security.mobile.MobileAuthenticationSecurityConfig;
import com.iyysoft.msdp.common.security.service.MsdpUserDetailsService;
import com.iyysoft.msdp.common.security.wxmini.WxMiniAuthenticationSecurityConfig;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author mao.chi
 * @date 2018/6/22
 * 认证相关配置
 */
@Primary
@Order(90)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private MsdpUserDetailsService userDetailsService;
    @Lazy
    @Autowired
    private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

//	@Autowired
//    protected AuthenticationSuccessHandler iyysoftAuthenticationSuccessHandler;
//    @Autowired
//    protected AuthenticationFailureHandler iyysoftAuthenctiationFailureHandler;

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http.logout().permitAll()
                .logoutSuccessUrl("/token/logout")
                .and()
                .formLogin()
                .loginPage("/token/login")
                .loginProcessingUrl("/token/form")
//            .successHandler(iyysoftAuthenticationSuccessHandler)
//            .failureHandler(iyysoftAuthenctiationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/actuator/**",
                        SecurityConstants.DEFAULT_REFRESH_TOKEN,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_LOGIN,
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,
                        SecurityConstants.DEFAULT_THIRD_USER_INFO_URL,
                        "/payCallback/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .apply(userPasswordAuthenticationSecurityConfig())
                .and()
                .apply(mobileAuthenticationSecurityConfig())
                .and()
                .apply(wxMiniAuthenticationSecurityConfig())
                .and()
                .apply(openIdAuthenticationSecurityConfig());
    }

    /**
     * 不拦截静态资源
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**");
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
        return MobileLoginSuccessHandler.builder()
                .objectMapper(objectMapper)
                .clientDetailsService(clientDetailsService)
                .passwordEncoder(passwordEncoder())
                .defaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices).build();
    }

    @Bean
    public AuthenticationSuccessHandler openIdLoginSuccessHandler() {
        return OpenIdLoginSuccessHandler.builder()
                .objectMapper(objectMapper)
                .clientDetailsService(clientDetailsService)
                .passwordEncoder(passwordEncoder())
                .defaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices).build();
    }

    @Bean
    public AuthenticationFailureHandler mobileLoginFailureHandler() {
        return new MobileLoginFailureHandler();
    }

    @Bean
    public AuthenticationFailureHandler openIdLoginFailureHandler() {
        return new OpenIdLoginFailureHandler();
    }

    @Bean
    public MobileAuthenticationSecurityConfig mobileAuthenticationSecurityConfig() {
        MobileAuthenticationSecurityConfig mobileSecurityConfigurer = new MobileAuthenticationSecurityConfig();
        mobileSecurityConfigurer.setMobileLoginSuccessHandler(mobileLoginSuccessHandler());
        mobileSecurityConfigurer.setMobileLoginFailureHandler(mobileLoginFailureHandler());
        mobileSecurityConfigurer.setUserDetailsService(userDetailsService);
        return mobileSecurityConfigurer;
    }


    @Bean
    public OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig() {
        OpenIdAuthenticationSecurityConfig openIdSecurityConfigurer = new OpenIdAuthenticationSecurityConfig();
        openIdSecurityConfigurer.setOpenIdLoginSuccessHandler(openIdLoginSuccessHandler());
        openIdSecurityConfigurer.setOpenIdLoginFailureHandler(openIdLoginFailureHandler());
        openIdSecurityConfigurer.setUserDetailsService(userDetailsService);
        return openIdSecurityConfigurer;
    }

    @Bean
    public WxMiniAuthenticationSecurityConfig wxMiniAuthenticationSecurityConfig() {
        WxMiniAuthenticationSecurityConfig wxMiniSecurityConfigurer = new WxMiniAuthenticationSecurityConfig();
        wxMiniSecurityConfigurer.setWxMiniLoginSuccessHandler(mobileLoginSuccessHandler());
        wxMiniSecurityConfigurer.setWxMiniLoginFailureHandler(mobileLoginFailureHandler());
        wxMiniSecurityConfigurer.setUserDetailsService(userDetailsService);
        return wxMiniSecurityConfigurer;
    }

    @Bean
    public UserPasswordAuthenticationSecurityConfig userPasswordAuthenticationSecurityConfig() {
        UserPasswordAuthenticationSecurityConfig userPasswordAuthenticationSecurityConfig = new UserPasswordAuthenticationSecurityConfig();
        userPasswordAuthenticationSecurityConfig.setUserPasswordLoginSuccessHandler(mobileLoginSuccessHandler());
        userPasswordAuthenticationSecurityConfig.setUserPasswordLoginFailureHandler(mobileLoginFailureHandler());
        userPasswordAuthenticationSecurityConfig.setUserDetailsService(userDetailsService);
        return userPasswordAuthenticationSecurityConfig;
    }

    /**
     * https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-updated
     * Encoded password does not look like BCrypt
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
