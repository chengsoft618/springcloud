package com.iyysoft.msdp.common.data.tenant;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mao.chi
 * @date 2018/9/14
 * feign 租户信息拦截
 */
@Configuration
public class MsdpFeignTenantConfiguration {

    @Bean
    public RequestInterceptor msdpFeignTenantInterceptor() {
        return new MsdpFeignTenantInterceptor();
    }
}
