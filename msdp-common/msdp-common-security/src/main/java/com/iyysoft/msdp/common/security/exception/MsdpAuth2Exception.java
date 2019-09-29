package com.iyysoft.msdp.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iyysoft.msdp.common.security.component.MsdpAuth2ExceptionSerializer;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author mao.chi
 * @date 2018/7/8
 * 自定义OAuth2Exception
 */
@JsonSerialize(using = MsdpAuth2ExceptionSerializer.class)
public class MsdpAuth2Exception extends OAuth2Exception {
    @Getter
    private String errorCode;

    public MsdpAuth2Exception(String msg) {
        super(msg);
    }

    public MsdpAuth2Exception(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
