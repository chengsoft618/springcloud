package com.iyysoft.msdp.auth.config;

import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.data.tenant.TenantContextHolder;
import com.iyysoft.msdp.common.security.component.MsdpWebResponseExceptionTranslator;
import com.iyysoft.msdp.common.security.service.MsdpClientDetailsService;
import com.iyysoft.msdp.common.security.service.MsdpUser;
import com.iyysoft.msdp.common.security.service.MsdpUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mao.chi
 * @date 2018/6/22
 * 认证服务器配置
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final MsdpUserDetailsService msdpUserDetailsService;
    private final AuthenticationManager authenticationManagerBean;
    private final RedisConnectionFactory redisConnectionFactory;
    //private final FormatAccessTokenConverter formatAccessTokenConverter;

    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        MsdpClientDetailsService clientDetailsService = new MsdpClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .userDetailsService(msdpUserDetailsService)
                .authenticationManager(authenticationManagerBean)
                .reuseRefreshTokens(false)
                //.accessTokenConverter(formatAccessTokenConverter)
                .exceptionTranslator(new MsdpWebResponseExceptionTranslator());
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(SecurityConstants.MSDP_PREFIX + SecurityConstants.OAUTH_PREFIX);
        tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                return super.extractKey(authentication) + ":" + TenantContextHolder.getTenantId();
            }
        });
        return tokenStore;
    }

    /**
     * token增强，客户端模式不增强。
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (SecurityConstants.CLIENT_CREDENTIALS
                    .equals(authentication.getOAuth2Request().getGrantType())) {
                return accessToken;
            }

            final Map<String, Object> additionalInfo = new HashMap<>(8);
            MsdpUser MsdpUser = (MsdpUser) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put(SecurityConstants.DETAILS_USER_ID, MsdpUser.getUserId());
            // todo 新版需要修改UserUid
            additionalInfo.put(SecurityConstants.DETAILS_USER_UID, MsdpUser.getUserId());
            additionalInfo.put(SecurityConstants.DETAILS_USERNAME, MsdpUser.getUserName());
            additionalInfo.put(SecurityConstants.DETAILS_ORG_ID, MsdpUser.getDeptId());
            additionalInfo.put(SecurityConstants.DETAILS_TENANT_ID, MsdpUser.getTenantId());
            additionalInfo.put(SecurityConstants.DETAILS_MOBILE, MsdpUser.getMobile());
            additionalInfo.put(SecurityConstants.DETAILS_ID_TYPE, MsdpUser.getIdType());
            additionalInfo.put(SecurityConstants.DETAILS_UID, MsdpUser.getUid());
            additionalInfo.put(SecurityConstants.DETAILS_AREA_ID, MsdpUser.getAreaId());
            //additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.IYYSOFT_LICENSE);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
