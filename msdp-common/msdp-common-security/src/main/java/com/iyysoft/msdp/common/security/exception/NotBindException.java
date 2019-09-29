package com.iyysoft.msdp.common.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mao.chi
 * @date 2018/7/8
 */
public class NotBindException extends AuthenticationException {
    public NotBindException(String msg) {
        super(msg);
    }

    public NotBindException(String msg, Throwable t) {
        super(msg, t);
    }
}