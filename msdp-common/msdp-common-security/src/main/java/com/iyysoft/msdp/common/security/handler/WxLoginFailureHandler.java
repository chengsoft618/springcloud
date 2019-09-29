package com.iyysoft.msdp.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.security.exception.BindServerErrorException;
import com.iyysoft.msdp.common.security.exception.NotBindException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mao.chi
 * @date 2018/8/5
 * 手机号登录成功，返回oauth token
 */
@Slf4j
public class WxLoginFailureHandler implements AuthenticationFailureHandler {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败");

        // 记录失败次数 和原因 ip等信息 5次登录失败,锁定用户, 不允许在此登录

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        if (exception instanceof NotBindException) {
            response.getWriter().write(objectMapper.writeValueAsString(new R<>(exception.getMessage(), REnum.CODE.NOT_BINDING)));
        } else if (exception instanceof BindServerErrorException) {
            response.getWriter().write(objectMapper.writeValueAsString(new R<>(exception.getMessage(), REnum.CODE.NETWORK_ERROR)));
        } else {
            response.getWriter().write(objectMapper.writeValueAsString(new R<>(exception.getMessage(), REnum.CODE.VALID_FAILED)));
        }
    }
}
