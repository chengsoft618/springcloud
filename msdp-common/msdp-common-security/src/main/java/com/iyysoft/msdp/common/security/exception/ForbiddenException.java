package com.iyysoft.msdp.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iyysoft.msdp.common.security.component.MsdpAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author mao.chi
 * @date 2018/7/8
 */
@JsonSerialize(using = MsdpAuth2ExceptionSerializer.class)
public class ForbiddenException extends MsdpAuth2Exception {

    public ForbiddenException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "access_denied";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.FORBIDDEN.value();
    }

}

