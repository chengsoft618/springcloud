package com.iyysoft.msdp.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iyysoft.msdp.common.security.component.MsdpAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author mao.chi
 * @date 2018/7/8
 */
@JsonSerialize(using = MsdpAuth2ExceptionSerializer.class)
public class MethodNotAllowedException extends MsdpAuth2Exception {

    public MethodNotAllowedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "method_not_allowed";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.value();
    }

}
