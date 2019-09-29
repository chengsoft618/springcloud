package com.iyysoft.msdp.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * <p class="detail">
 * 功能:  网关解决跨域问题
 * </p>
 *
 * @author cm
 * @ClassName Cors config.
 * @Version V1.0.
 * @date 2019.05.23 15:20:39
 */
@Configuration
public class CorsConfig {
    /**
     * <p class="detail">
     * 功能:
     * </p>
     *
     * @return cors web filter
     * @author cm
     * @date 2019.05.23 15:20:39
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

}
