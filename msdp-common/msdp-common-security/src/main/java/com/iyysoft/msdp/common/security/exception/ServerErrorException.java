package com.iyysoft.msdp.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iyysoft.msdp.common.security.component.MsdpAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author mao.chi
 * @date 2018/7/8
 */
@JsonSerialize(using = MsdpAuth2ExceptionSerializer.class)
public class ServerErrorException extends MsdpAuth2Exception {

    public ServerErrorException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "server_error";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

}
