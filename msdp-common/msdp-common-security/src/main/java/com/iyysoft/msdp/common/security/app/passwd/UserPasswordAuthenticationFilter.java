package com.iyysoft.msdp.common.security.app.passwd;

import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.security.exception.HystrixException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mao.chi
 * @date 2019/1/9
 * 手机号登录验证filter
 */
public class UserPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Getter
    @Setter
    private boolean postOnly = true;
    @Getter
    @Setter
    private AuthenticationEventPublisher eventPublisher;
    @Getter
    @Setter
    private AuthenticationEntryPoint authenticationEntryPoint;


    public UserPasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_LOGIN, "POST"));
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username = StringUtils.defaultIfEmpty(request.getParameter("username"), "");
        username = username.trim();
        String password = StringUtils.defaultIfEmpty(request.getParameter("password"), "");
        password = password.trim();

        UserPasswordAuthenticationToken userPasswordAuthenticationToken = new UserPasswordAuthenticationToken(username, password);

        setDetails(request, userPasswordAuthenticationToken);

        Authentication authResult = null;
        try {
            authResult = this.getAuthenticationManager().authenticate(userPasswordAuthenticationToken);

            logger.debug("Authentication success: " + authResult);
            SecurityContextHolder.getContext().setAuthentication(authResult);

        } catch (AuthenticationException authException) {
            SecurityContextHolder.clearContext();
            logger.debug("Authentication request failed: " + authException);

            eventPublisher.publishAuthenticationFailure(new BadCredentialsException(authException.getMessage(), authException),
                    new PreAuthenticatedAuthenticationToken("access-token", "N/A"));

            try {
                authenticationEntryPoint.commence(request, response, authException);
            } catch (Exception e) {
                logger.error("authenticationEntryPoint handle error:{}", authException);
            }
        } catch (Exception failed) {
            SecurityContextHolder.clearContext();
            logger.debug("Authentication request failed: " + failed);

            eventPublisher.publishAuthenticationFailure(new BadCredentialsException(failed.getMessage(), failed),
                    new PreAuthenticatedAuthenticationToken("access-token", "N/A"));

            try {
                if (failed instanceof HystrixRuntimeException) {
                    authenticationEntryPoint.commence(request, response,
                            new HystrixException(failed.getMessage(), failed));
                } else {
                    authenticationEntryPoint.commence(request, response,
                            new UsernameNotFoundException(failed.getMessage(), failed));
                }

            } catch (Exception e) {
                logger.error("authenticationEntryPoint handle error:{}", failed);
            }
        }


        return authResult;
    }


    private void setDetails(HttpServletRequest request,
                            UserPasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}

