package com.iyysoft.msdp.common.data.norepeat;


import com.iyysoft.msdp.common.data.cache.RedisTemplateConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConditionalOnBean(RedisTemplateConfig.class)
public class NoRepeatSubmitConfig {

    @Bean
    @Order(-1)
    public NoRepeatSubmitAop noRepeatSubmitAop() {
        return new NoRepeatSubmitAop();
    }
}
