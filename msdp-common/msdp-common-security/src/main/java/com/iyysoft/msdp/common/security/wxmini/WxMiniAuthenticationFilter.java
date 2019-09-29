package com.iyysoft.msdp.common.security.wxmini;

import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.security.exception.HystrixException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
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
 * WxMini登录验证filter
 */
public class WxMiniAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    // ~ Static fields/initializers
    // =====================================================================================

    @Getter
    @Setter
    private boolean postOnly = true;

    private static final String POST = "POST";
    @Getter
    @Setter
    private AuthenticationEventPublisher eventPublisher;
    @Getter
    @Setter
    private AuthenticationEntryPoint authenticationEntryPoint;


    WxMiniAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_WXMINI, "POST"));
    }

    /**
     * Attempt authentication authentication.
     *
     * @param request  the request
     * @param response the response
     * @return the authentication
     * @throws AuthenticationException the authentication exception
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly && !POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String userName = StringUtils.defaultIfEmpty(request.getParameter("userName"), "");
        String code = StringUtils.defaultIfEmpty(request.getParameter("code"), "");
        String appId = StringUtils.defaultIfEmpty(request.getParameter("appId"), "");
        userName = userName.trim();
        code = code.trim();
        WxMiniAuthentication wxMiniAuthentication = new WxMiniAuthentication(appId, code, userName);


        WxMiniAuthenticationToken authRequest = new WxMiniAuthenticationToken(wxMiniAuthentication);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        Authentication authResult = null;
        try {
            authResult = this.getAuthenticationManager().authenticate(authRequest);

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


    /**
     * Provided so that subclasses may configure what is put into the
     * authentication request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details            set
     */
    protected void setDetails(HttpServletRequest request, WxMiniAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
