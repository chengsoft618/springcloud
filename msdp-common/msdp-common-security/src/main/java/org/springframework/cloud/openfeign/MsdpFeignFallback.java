package org.springframework.cloud.openfeign;

import cn.hutool.core.util.StrUtil;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author mao.chi
 * <p>
 * fallback 代理处理
 */
@Slf4j
@AllArgsConstructor
public class MsdpFeignFallback<T> implements MethodInterceptor {
    private final Class<T> targetType;
    private final String targetName;
    private final Throwable cause;

    @Nullable
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Class<?> returnType = method.getReturnType();
        if (R.class != returnType) {
            return null;
        }
        FeignException exception = (FeignException) cause;

        byte[] content = exception.content();

        String str = StrUtil.str(content, StandardCharsets.UTF_8);

        log.error("MsdpFeignFallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, str);

        return new R(REnum.CODE.FEIGN_FAILED, str);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MsdpFeignFallback<?> that = (MsdpFeignFallback<?>) o;
        return targetType.equals(that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType);
    }
}
