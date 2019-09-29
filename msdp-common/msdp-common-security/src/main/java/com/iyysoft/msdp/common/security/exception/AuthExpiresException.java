package com.iyysoft.msdp.common.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mao.chi
 * @date 2018/7/8
 */
public class AuthExpiresException extends AuthenticationException {
    public AuthExpiresException(String msg) {
        super(msg);
    }

    public AuthExpiresException(String msg, Throwable t) {
        super(msg, t);
    }
}