package com.iyysoft.msdp.auth.endpoint;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.common.core.constant.PaginationConstants;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.data.tenant.TenantContextHolder;
import com.iyysoft.msdp.common.security.annotation.Inner;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mao.chi
 * @date 2019/6/24
 * 删除token端点
 */
@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class MsdpTokenEndpoint {

    private static final String MSDP_OAUTH_ACCESS = SecurityConstants.MSDP_PREFIX + SecurityConstants.OAUTH_PREFIX + "auth_to_access:";
    private final RedisTemplate msdpRedisTemplate;
    private final TokenStore tokenStore;
    private final CacheManager cacheManager;

    /**
     * 认证页面
     *
     * @return ModelAndView
     */

    //public ModelAndView require() {
    @GetMapping("/login")
    public R login(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return new R(REnum.CODE.LOGIN_FAILED);
    }

    /**
     * 退出token
     *
     * @param authHeader Authorization
     */
    @DeleteMapping("/logout")
    public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StrUtil.isBlank(authHeader)) {
            return new R(REnum.CODE.ERROR_HEAD);
        }

        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
            return new R(REnum.CODE.TOKEN_IS_NOT_EXIST);
        }

        OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);
        cacheManager.getCache("user_details")
                .evict(auth2Authentication.getName());
        tokenStore.removeAccessToken(accessToken);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 令牌管理调用
     *
     * @param token token
     * @return
     */
    @Inner
    @DeleteMapping("/{token}")
    public R<Boolean> delToken(@PathVariable("token") String token) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new R<>(Boolean.TRUE);
    }


    /**
     * 查询token
     *
     * @param params 分页参数
     * @return
     */
    @Inner
    @PostMapping("/page")
    public R<Page> tokenList(@RequestBody Map<String, Object> params) {
        //根据分页参数获取对应数据
        String key = String.format("%s*:%s", MSDP_OAUTH_ACCESS, TenantContextHolder.getTenantId());
        List<String> pages = findKeysForPage(key, MapUtil.getInt(params, PaginationConstants.CURRENT)
                , MapUtil.getInt(params, PaginationConstants.SIZE));

        msdpRedisTemplate.setKeySerializer(new StringRedisSerializer());
        msdpRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        Page result = new Page(MapUtil.getInt(params, PaginationConstants.CURRENT), MapUtil.getInt(params, PaginationConstants.SIZE));
        result.setRecords(msdpRedisTemplate.opsForValue().multiGet(pages));
        result.setTotal(Long.valueOf(msdpRedisTemplate.keys(key).size()));
        return new R<>(result);
    }

    private List<String> findKeysForPage(String patternKey, int pageNum, int pageSize) {
        ScanOptions options = ScanOptions.scanOptions().match(patternKey).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) msdpRedisTemplate.getKeySerializer();
        Cursor cursor = (Cursor) msdpRedisTemplate.executeWithStickyConnection(redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize));
        List<String> result = new ArrayList<>();
        int tmpIndex = 0;
        int startIndex = (pageNum - 1) * pageSize;
        int end = pageNum * pageSize;

        assert cursor != null;
        while (cursor.hasNext()) {
            if (tmpIndex >= startIndex && tmpIndex < end) {
                result.add(cursor.next().toString());
                tmpIndex++;
                continue;
            }
            if (tmpIndex >= end) {
                break;
            }
            tmpIndex++;
            cursor.next();
        }
        return result;
    }
}
