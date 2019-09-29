package com.iyysoft.msdp.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iyysoft.msdp.common.security.component.MsdpAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author mao.chi
 * @date 2019/7/8
 */
@JsonSerialize(using = MsdpAuth2ExceptionSerializer.class)
public class UnauthorizedException extends MsdpAuth2Exception {

    public UnauthorizedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "unauthorized";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

}
