
package com.iyysoft.msdp.common.gateway.annotation;

import com.iyysoft.msdp.common.gateway.configuration.DynamicRouteAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author mao.chi
 * @date 2018/11/5
 * <p>
 * 开启iyysoft 动态路由
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DynamicRouteAutoConfiguration.class)
public @interface EnableMsdpDynamicRoute {
}
