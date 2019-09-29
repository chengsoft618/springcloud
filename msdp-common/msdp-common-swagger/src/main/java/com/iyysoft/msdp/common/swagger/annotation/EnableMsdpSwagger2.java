
package com.iyysoft.msdp.common.swagger.annotation;

import com.iyysoft.msdp.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author mao.chi
 * @date 2018/7/21
 * 开启iyysoft swagger
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableMsdpSwagger2 {
}
