package com.iyysoft.msdp.common.core.util.http;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * http 请求返回状态。
 *
 * @author chimao
 * @type class
 * @date 10:33 2018-07-10
 */
public final class HttpsStatus {

    private static Logger log = LoggerFactory.getLogger(HttpsStatus.class);


    /**
     * 获取编码
     *
     * @param request
     * @return
     */
    public HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (null == statusCode) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * 获取编码及信息
     *
     * @param request
     * @return
     */
    public Map getHttpStatus(HttpServletRequest request, Exception e) {
        Map map = Maps.newHashMap();

        HttpStatus httpStatus = this.getStatus(request);
        Integer httpCode = -1;
        String httpMsg = "您已经进入太空了，请尽快联系管理员。";

        if (httpStatus == HttpStatus.BAD_REQUEST) {
            httpCode = 400;
            httpMsg = "参数校验失败。详细信息：" + e;
        } else if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            httpCode = 500;
            httpMsg = "系统错误。详细信息：" + e;
        } else if (httpStatus == HttpStatus.NOT_FOUND) {
            httpCode = 404;
            httpMsg = "服务或者页面不存在：" + e;
        }
        log.info("=============GlobalExceptionHandler.allExceptionHandler==================");
        System.out.println("getLocalizedMessage：" + e.getLocalizedMessage());
        System.out.println("getCause ：" + e.getCause());
        System.out.println("getSuppressed = " + e.getSuppressed());
        System.out.println("getMessage：" + e.getMessage());
        System.out.println("getStackTrace： " + e.getStackTrace());
        map.put("httpCode", httpCode);
        map.put("httpMsg", httpMsg);
        return map;
    }
}
