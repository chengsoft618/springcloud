package com.iyysoft.msdp.common.log.annotation;

import java.lang.annotation.*;

/**
 * @author mao.chi
 * @date 2018/6/28
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 描述
     *
     * @return {String}
     */
    String value();
}
