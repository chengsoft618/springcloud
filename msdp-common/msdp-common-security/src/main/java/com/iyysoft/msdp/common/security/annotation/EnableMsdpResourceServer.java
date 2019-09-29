package com.iyysoft.msdp.common.security.annotation;

import com.iyysoft.msdp.common.security.component.MsdpResourceServerAutoConfiguration;
import com.iyysoft.msdp.common.security.component.MsdpSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @author mao.chi
 * @date 2018/11/10
 * <p>
 * 资源服务注解
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({MsdpResourceServerAutoConfiguration.class, MsdpSecurityBeanDefinitionRegistrar.class})
public @interface EnableMsdpResourceServer {

}
