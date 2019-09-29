package com.iyysoft.msdp.common.core.util.http;


import javax.servlet.http.HttpServletRequest;

/**
 * @author cm
 * @created with: msdp-parent
 * @package com.jcwj.ms.security.common.utils.http
 * @description: http 请求信息获取
 * @date 2018/7/6 14:16
 * @modified By:
 */
public class HttpRequestUtils {

    /**
     * 访问者ip
     *
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    /**
     * 完整请求url
     *
     * @param request
     * @return
     */
    public static String getRequestURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    /**
     * 请求方式(get,post,delete...)
     *
     * @param request
     * @return
     */
    public static String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    /**
     * 服务器的ip地址
     *
     * @param request
     * @return
     */
    public static String getLocalAddr(HttpServletRequest request) {
        return request.getLocalAddr();
    }

//    public static String getData(HttpServletRequest request) throws IOException{
//        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
//        String body = IOUtils.readAll(reader);
//        return body;
//    }
}
