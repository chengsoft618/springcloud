package com.iyysoft.msdp.common.core.exception;

/**
 * @author mao.chi
 * @date 2019年06月22日16:22:15
 */
public class ValidateCodeException extends Exception {
    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException() {
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
