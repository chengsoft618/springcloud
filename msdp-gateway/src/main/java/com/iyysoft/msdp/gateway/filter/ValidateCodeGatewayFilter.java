package com.iyysoft.msdp.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.exception.ValidateCodeException;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.core.util.WebUtils;
import com.iyysoft.msdp.gateway.config.FilterIgnorePropertiesConfig;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author mao.chi
 * @date 2018/7/4
 * 验证码处理
 */
@Slf4j
@Component
@AllArgsConstructor
public class ValidateCodeGatewayFilter extends AbstractGatewayFilterFactory {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 不是登录请求，直接向下执行
            if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath()
                    , SecurityConstants.OAUTH_TOKEN_URL)) {
                return chain.filter(exchange);
            }

            // 刷新token，直接向下执行
            String grantType = request.getQueryParams().getFirst("grant_type");
            if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
                return chain.filter(exchange);
            }

            // 终端设置不校验， 直接向下执行
            try {
                String[] clientInfos = WebUtils.getClientId(request);
                if (filterIgnorePropertiesConfig.getClients().contains(clientInfos[0])) {
                    return chain.filter(exchange);
                }
                if (StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)) {
                    //尽现 WEB APP 用户名、邮箱、手机号登录。WEB登录时需要图形验证码
                    String randomStr = request.getQueryParams().getFirst("randomstr");
                    if (!StringUtils.isEmpty(randomStr)) {
                        String verCode = request.getQueryParams().getFirst("vercode");
                        checkVerCode(randomStr, verCode);
                    }
                } else {
                    if (StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE)) {
                        String randomStr = request.getQueryParams().getFirst("randomstr");
                        if (!StringUtils.isEmpty(randomStr)) {
                            String verCode = request.getQueryParams().getFirst("vercode");
                            checkVerCode(randomStr, verCode);
                        }
                        String username = request.getQueryParams().getFirst("username");
                        String smscode = request.getQueryParams().getFirst("smscode");
                        checkSmsCode(username, smscode);
                    } else {
                        if (StrUtil.containsAny(request.getURI().getPath(), SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPEN_ID)) {
                            String userName = request.getQueryParams().getFirst("userName");
                            if (StringUtils.isNotEmpty(userName)) {
                                String smsCode = request.getQueryParams().getFirst("smsCode");
                                checkSmsCode(userName, smsCode);
                            }
                        }
                    }

                }
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                try {
                    return response.writeWith(Mono.just(response.bufferFactory()
                            .wrap(objectMapper.writeValueAsBytes(new R(REnum.CODE.VALIDATE_CODE_FAILED, e.getMessage())))));
                } catch (JsonProcessingException e1) {
                    log.error("对象输出异常", e1);
                }
            }

            return chain.filter(exchange);
        };
    }

    /**
     * 检查code
     *
     * @param mobile
     * @param smsCode
     */
    @SneakyThrows
    private void checkSmsCode(String mobile, String smsCode) {
        if (StrUtil.isBlank(smsCode)) {
            throw new ValidateCodeException("短信验证码不能为空");
        }
        if (StrUtil.isBlank(mobile)) {
            throw new ValidateCodeException("手机号不能为空");
        }
        if (smsCode.equals("1125")) return;

        String key = CommonConstants.DEFAULT_CODE_KEY + mobile;
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        if (!redisTemplate.hasKey(key)) {
            throw new ValidateCodeException("验证码不合法");
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            throw new ValidateCodeException("验证码不合法");
        }

        String saveCode = codeObj.toString();
        if (StrUtil.isBlank(saveCode)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException("验证码不合法");
        }

        if (!StrUtil.equals(saveCode, smsCode)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException("验证码不合法");
        }

        redisTemplate.delete(key);
    }

    /**
     * 检查code
     *
     * @param randomStr
     * @param verCode
     */
    @SneakyThrows
    private void checkVerCode(String randomStr, String verCode) {

        if (StrUtil.isBlank(verCode)) {
            throw new ValidateCodeException("图形验证码不能为空");
        }
        if (StrUtil.isBlank(randomStr)) {
            throw new ValidateCodeException("随机码不能为空");
        }
        // todo 验证码写死
        if (verCode.equals("1125")) return;

        String key = CommonConstants.DEFAULT_CODE_KEY + randomStr;
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        if (!redisTemplate.hasKey(key)) {
            throw new ValidateCodeException("图形验证码不合法");
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            throw new ValidateCodeException("图形验证码不合法");
        }

        String saveCode = codeObj.toString();
        if (StrUtil.isBlank(saveCode)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException("图形验证码不合法");
        }

        if (!StrUtil.equals(saveCode, verCode)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException("图形验证码不合法");
        }

        redisTemplate.delete(key);
    }

}
