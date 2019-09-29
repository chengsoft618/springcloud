package com.iyysoft.msdp.common.security.app.openid;

import com.iyysoft.msdp.common.security.component.MsdpPreAuthenticationChecks;
import com.iyysoft.msdp.common.security.service.MsdpUserDetailsService;
import lombok.Getter;
import lombok.Setter;
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
 * OpenId登录
 */
@Slf4j
public class OpenIdAuthenticationProvider implements AuthenticationProvider {
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
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OpenIdAuthenticationToken openIdAuthenticationToken = (OpenIdAuthenticationToken) authentication;

        OpenIdAuthentication principal = (OpenIdAuthentication) openIdAuthenticationToken.getPrincipal();
        String thirdId = principal.getThirdId();
        String openId = principal.getOpenId();
        String userName = principal.getUserName();

//		Set<String> providerUserIds = new HashSet<>();
//
//		providerUserIds.add((String) authenticationToken.getPrincipal());

//		Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(), providerUserIds);
//
//		if (CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
//			throw new InternalAuthenticationServiceException("无法获取用户信息");
//		}
//
//		String userId = userIds.iterator().next();

        UserDetails userDetails = userDetailsService.loadUserByOpenId(thirdId, openId, userName);

        if (userDetails == null) {
            log.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.noopBindAccount",
                    "Noop Bind Account"));
        }
        // 检查账号状态
        detailsChecker.check(userDetails);

        OpenIdAuthenticationToken authenticationToken = new OpenIdAuthenticationToken(userDetails, userDetails.getAuthorities());
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
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
