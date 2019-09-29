package com.iyysoft.msdp.common.security.app.passwd;

import com.iyysoft.msdp.common.core.constant.SecurityConstants;
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
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author mao.chi
 * @date 2018/8/5
 * 手机登录校验逻辑
 * 验证码登录、社交登录
 */
@Slf4j
public class UserPasswordAuthenticationProvider implements AuthenticationProvider {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker detailsChecker = new MsdpPreAuthenticationChecks();

    @Getter
    @Setter
    private MsdpUserDetailsService userDetailsService;

    @Override
    @SneakyThrows
    public Authentication authenticate(Authentication authentication) {
        UserPasswordAuthenticationToken userPasswordAuthenticationToken = (UserPasswordAuthenticationToken) authentication;

        String userName = userPasswordAuthenticationToken.getPrincipal().toString();
        String password = userPasswordAuthenticationToken.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            log.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.noopBindAccount",
                    "Noop Bind Account"));
        }
        // 检查账号状态
        detailsChecker.check(userDetails);
        String presentedPassword = authentication.getCredentials().toString();
        String oPassword = userDetails.getPassword().replace(SecurityConstants.BCRYPT, "");
        if (!ENCODER.matches(presentedPassword, oPassword)) {
            log.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        UserPasswordAuthenticationToken authenticationToken = new UserPasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationToken.setDetails(userPasswordAuthenticationToken.getDetails());
        return authenticationToken;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UserPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
