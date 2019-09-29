package com.iyysoft.msdp.common.core.util.auth;

import com.iyysoft.msdp.common.core.util.http.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p class="detail">
 * 功能:认证管理
 * </p>
 *
 * @author cm
 * @ClassName Auth util.
 * @Version V1.0.
 * @date 2019.05.14 10:55:20
 */
public class AuthUtil {

    private final static String apiid = "223913179d6941f8bd0f4517cc80f509";
    private final static String apisecret = "f98e23ebd4b34a84a473a68b0ea08166";
    private final static String uri = "https://cloudapi.linkface.cn/ocr/idcard";
    private final static String uri_verification = "https://cloudapi.linkface.cn/identity/selfie_idnumber_verification";


    /**
     * 认证管理-身份证识别
     *
     * @param map :
     * @return
     * @author cm
     * @date 2019.05.14 10:55:20
     */
    public static String reqAuth(Map map) {
        if (null == map) {
            map = new HashMap();
        }
        map.put("api_id", apiid);
        map.put("api_secret", apisecret);
        return HttpClientUtils.post(uri, map);
    }

    /**
     * 认证管理-认证对比
     *
     * @param map :
     * @return
     * @author cm
     * @date 2019.05.14 10:55:20
     */
    public static String reqAuthVerification(Map map) {
        if (null == map) {
            map = new HashMap();
        }
        map.put("api_id", apiid);
        map.put("api_secret", apisecret);
        return HttpClientUtils.post(uri_verification, map);
    }

    /**
     * 认证管理-认证对比
     *
     * @param key :
     * @return
     * @author cm
     * @date 2019.05.14 10:55:20
     */
    public static String getAuthErr(String key) {
        Map map = new HashMap();
        map.put("PHOTO_SERVICE_ERROR", "4003");
        map.put("ENCODING_ERROR", "4004");
        map.put("DOWNLOAD_TIMEOUT", "4005");
        map.put("DOWNLOAD_ERROR", "4006");
        map.put("IMAGE_ID_NOT_EXIST", "4007");
        map.put("NO_FACE_DETECTED", "4008");
        map.put("CORRUPT_IMAGE", "4009");
        map.put("INVALID_IMAGE_FORMAT_OR_SIZE", "4010");
        map.put("INVALID_ARGUMENT", "4011");
        map.put("UNAUTHORIZED", "4012");
        map.put("KEY_EXPIRED", "4013");
        map.put("RATE_LIMIT_EXCEEDED", "4014");
        map.put("NO_PERMISSION", "4015");
        map.put("OUT_OF_QUOTA", "4016");
        map.put("NOT_FOUND", "4017");
        map.put("INTERNAL_ERROR", "4018");
/*
		map.put("PHOTO_SERVICE_ERROR", "数据源服务服务出错");
		map.put("ENCODING_ERROR", "参数非UTF-8编码");
		map.put("DOWNLOAD_TIMEOUT", "网络地址图片获取超时");
		map.put("DOWNLOAD_ERROR", "网络地址图片获取失败");
		map.put("IMAGE_ID_NOT_EXIST", "图片不存在");
		map.put("NO_FACE_DETECTED", "上传的图片未检测出人脸");
		map.put("CORRUPT_IMAGE", "文件不是图片文件或已经损坏");
		map.put("INVALID_IMAGE_FORMAT_OR_SIZE", "图片大小或格式不符合要求");
		map.put("INVALID_ARGUMENT", "请求参数错误");
		map.put("UNAUTHORIZED", "账号或密钥错误");
		map.put("KEY_EXPIRED", "账号过期");
		map.put("RATE_LIMIT_EXCEEDED", "调用频率超出限额");
		map.put("NO_PERMISSION", "无调用权限");
		map.put("OUT_OF_QUOTA", "调用次数超出限额");
		map.put("NOT_FOUND", "请求路径错误");
		map.put("INTERNAL_ERROR", "服务器内部错误");
		*/

        return map.get(key).toString();
    }


}
