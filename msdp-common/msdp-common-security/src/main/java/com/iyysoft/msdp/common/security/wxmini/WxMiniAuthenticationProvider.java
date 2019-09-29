package com.iyysoft.msdp.common.security.wxmini;

import com.iyysoft.msdp.common.security.component.MsdpPreAuthenticationChecks;
import com.iyysoft.msdp.common.security.service.MsdpUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @author mao.chi
 * @date 2018/8/5
 * 登录校验逻辑
 * WxMini登录
 */
@Slf4j
public class WxMiniAuthenticationProvider implements AuthenticationProvider {
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker detailsChecker = new MsdpPreAuthenticationChecks();

    @Getter
    @Setter
    private MsdpUserDetailsService userDetailsService;

//	 @Getter
//	 @Setter
//	private UsersConnectionRepository usersConnectionRepository;

    /**
     * Authenticate authentication.
     *
     * @param authentication the authentication
     * @return the authentication
     * @throws AuthenticationException the authentication exception
     */
    @Override
    @SneakyThrows
    public Authentication authenticate(Authentication authentication) {

        WxMiniAuthenticationToken wxMiniAuthenticationToken = (WxMiniAuthenticationToken) authentication;

        WxMiniAuthentication principal = (WxMiniAuthentication) wxMiniAuthenticationToken.getPrincipal();
        String userName = principal.getUserName();
        String code = principal.getCode();
        String appId = principal.getAppId();

        UserDetails userDetails;
        userDetails = userDetailsService.loadUserByWxMini(appId, code, userName);

        if (userDetails == null) {
            log.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.noopBindAccount",
                    "Noop Bind Account"));
        }
        // 检查账号状态
        detailsChecker.check(userDetails);

        WxMiniAuthenticationToken authenticationToken = new WxMiniAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(authenticationToken.getDetails());

        return authenticationToken;
    }

    /**
     * Supports boolean.
     *
     * @param authentication the authentication
     * @return the boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return WxMiniAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
