package com.iyysoft.msdp.common.job.annotation;

import com.iyysoft.msdp.common.job.ElasticJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author mao.chi
 * @date 2018/7/24
 * 开启iyysoft job
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ElasticJobAutoConfiguration.class})
public @interface EnableMsdpJob {
}
