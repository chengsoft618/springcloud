package com.iyysoft.msdp.auth.handler;

import cn.hutool.core.util.CharsetUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.security.exception.BindServerErrorException;
import com.iyysoft.msdp.common.security.exception.NotBindException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mao.chi
 * @date 2018/10/8
 */
@Slf4j
@Component
public class MsdpAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding(CharsetUtil.UTF_8);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        R result = new R<>(REnum.CODE.AUTH_FAILED);
        if (authException != null) {
            if (authException instanceof NotBindException) {
                result = new R<>(REnum.CODE.NOT_BINDING, authException.getMessage());
            } else if (authException instanceof BindServerErrorException) {
                result = new R<>(REnum.CODE.NETWORK_ERROR, authException.getMessage());
            } else if (authException instanceof UsernameNotFoundException) {
                result = new R<>(REnum.CODE.USER_PASSWORD_ERROR, authException.getMessage());
            }
        }

        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }


}
