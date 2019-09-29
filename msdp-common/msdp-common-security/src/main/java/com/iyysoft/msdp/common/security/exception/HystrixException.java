package com.iyysoft.msdp.common.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mao.chi
 * @date 2018/7/8
 */
public class HystrixException extends AuthenticationException {
    public HystrixException(String msg) {
        super(msg);
    }

    public HystrixException(String msg, Throwable t) {
        super(msg, t);
    }
}