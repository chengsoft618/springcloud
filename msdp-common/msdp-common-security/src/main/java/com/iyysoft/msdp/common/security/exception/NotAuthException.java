package com.iyysoft.msdp.common.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mao.chi
 * @date 2018/7/8
 */
public class NotAuthException extends AuthenticationException {
    public NotAuthException(String msg) {
        super(msg);
    }

    public NotAuthException(String msg, Throwable t) {
        super(msg, t);
    }
}