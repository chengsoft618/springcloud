package com.iyysoft.msdp.common.transaction.tx;

import com.iyysoft.msdp.common.transaction.tx.springcloud.feign.TransactionRestTemplateInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mao.chi on 2018/1/18
 * @author mao.chi
 * @since 4.1.0
 */
@Configuration
public class RequestInterceptorConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new TransactionRestTemplateInterceptor();
    }
}
