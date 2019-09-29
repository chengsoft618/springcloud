package com.iyysoft.msdp.common.security.component;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.security.exception.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author mao.chi
 * @date 2018/7/8
 * 客户端异常处理
 * 1. 可以根据 AuthenticationException 不同细化异常处理
 */
@Slf4j
@Component
@AllArgsConstructor
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        response.setCharacterEncoding(CommonConstants.UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        R<String> result = new R<>(REnum.CODE.VALID_FAILED);
        //InsufficientAuthenticationException
        //ForbiddenException
        if (authException != null) {
            if (authException instanceof NotBindException) {//没有绑定
                result = new R<>(REnum.CODE.NOT_BINDING, authException.getMessage());
            } else if (authException instanceof HystrixException) {//远程调用失败
                result = new R<>(REnum.CODE.FEIGN_FAILED, authException.getMessage());
            } else if (authException instanceof InsufficientAuthenticationException) {//授权过期
                result = new R<>(REnum.CODE.AUTH_EXPIRES, authException.getMessage());
            } else if (authException instanceof NotAuthException) {//无权
                result = new R<>(REnum.CODE.FEIGN_FAILED, authException.getMessage());
            } else if (authException instanceof BindServerErrorException) {//绑定服务器错误
                result = new R<>(REnum.CODE.NETWORK_ERROR, authException.getMessage());
            } else if (authException instanceof UsernameNotFoundException) {//用户和名密码错误
                result = new R<>(REnum.CODE.USER_PASSWORD_ERROR, authException.getMessage());
            }
        }
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
