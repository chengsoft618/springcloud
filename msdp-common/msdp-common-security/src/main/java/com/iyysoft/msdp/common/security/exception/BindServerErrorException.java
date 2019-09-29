package com.iyysoft.msdp.common.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mao.chi
 * @date 2018/7/8
 */
public class BindServerErrorException extends AuthenticationException {
    public BindServerErrorException(String msg) {
        super(msg);
    }

    public BindServerErrorException(String msg, Throwable t) {
        super(msg, t);
    }
}