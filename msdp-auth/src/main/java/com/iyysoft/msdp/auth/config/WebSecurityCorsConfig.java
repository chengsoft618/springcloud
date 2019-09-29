package com.iyysoft.msdp.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author mao.chi on 2019/9/17
 */
@Configuration
public class WebSecurityCorsConfig {

    /**
     * 跨域过滤器
     *
     * @return
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    // 允许的域名白名单，可以* ,http://localhost:8081
        corsConfiguration.addAllowedHeader("*");    // 允许任何头
        corsConfiguration.addAllowedMethod("*");    // 允许哪些类型请求，可以*
        //corsConfiguration.setAllowCredentials(true);    //是否允许携带cookie
        //corsConfiguration.addExposedHeader("my-header");     //自定义请求头
        return corsConfiguration;
    }


    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
