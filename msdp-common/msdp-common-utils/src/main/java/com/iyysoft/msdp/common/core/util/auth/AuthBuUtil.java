package com.iyysoft.msdp.common.core.util.auth;

import com.alibaba.fastjson.JSONObject;
import com.iyysoft.msdp.common.core.util.http.HttpBuUtil;
import com.iyysoft.msdp.common.core.util.http.HttpClientUtils;
import com.iyysoft.msdp.common.core.util.io.FileUtils;
import org.jolokia.util.Base64Util;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p class="detail">
 * 功能:认证管理-百度
 * </p>
 *
 * @author cm
 * @ClassName Auth util.
 * @Version V1.0.
 * @date 2019.05.14 10:55:20
 */
public class AuthBuUtil {

    private final static String appId = "16249472";
    private final static String api_key = "87Wyspf1MsyOAgG51N0Nhusv";
    private final static String secret_Key = "uiXpUirSHdREoVQIKCwCEiVxhkDXiG9M";

    private final static String token = "https://aip.baidubce.com/oauth/2.0/token";
    private final static String uri_id = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
    private final static String uri_face_verify = "https://aip.baidubce.com/rest/2.0/face/v3/faceverify";

    /**
     * 认证管理-认证对比
     *
     * @param key :
     * @return auth id err
     * @author cm
     * @date 2019.05.14 10:55:20
     */
    public static String getAuthIdErr(String key, String language) {
        Map map = new HashMap();
        Map en_us = new HashMap();
        if (null != language.toLowerCase() && language.equals("en_us")) {
            en_us.put("222201", "network not available");
            en_us.put("222202", "pic not has face");
            en_us.put("222203", "image check fail");
            en_us.put("222204", "image_url_download_fail");
            en_us.put("222205", "network not availablel");
            return en_us.get(key).toString();
        } else {
            map.put("normal", "识别正常");
            map.put("reversed_side", "身份证正反面颠倒");
            map.put("non_idcard", "上传的图片中不包含身份证");
            map.put("blurred", "身份证模糊");
            map.put("other_type_card", "其他类型证照");
            map.put("over_exposure", "身份证关键字段反光或过曝");
            map.put("over_dark", "身份证欠曝（亮度过低）");
            map.put("unknown", "未知状态");
            map.put("222201", "服务端请求失败");
            map.put("222202", "图片中没有人脸;检查图片质量");
            map.put("222203", "无法解析人脸");
            map.put("222204", "从图片的url下载图片失败");
            map.put("222205", "服务端请求失败");
            return map.get(key).toString();
        }
    }

    /**
     * @return
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImgFilePath(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * <p class="detail">
     * 功能:	获取token
     * </p>
     *
     * @return string
     * @author cm
     * @date 2019.05.14 19:24:18
     */
    public static String getBuToken() {
        return HttpClientUtils.get(token + "?grant_type=client_credentials&client_id=" + api_key + "&client_secret=" + secret_Key);
    }

    /**
     * 认证管理-身份证识别
     * https://ai.baidu.com/docs#/OCR-API/7e4792c7
     *
     * @param map :
     * @return
     * @author cm
     * @date 2019.05.14 10:55:20
     */
    public static String getAuthId(Map map) {
        if (null == map) {
            map = new HashMap();
        }
        byte[] imgData = FileUtils.toByteArray(map.get("filePath").toString());
        String imgStr = Base64Util.encode(imgData);
        String image = "";
        String imgS = "";
        try {
            image = URLEncoder.encode("image", "UTF-8");
            imgS = URLEncoder.encode(imgStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String params = "id_card_side=front&" + image + "=" + imgS;
        String result = "";
        try {
            result = HttpBuUtil.post(uri_id, map.get("access_token").toString(), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
        //return HttpClientUtils.post(uri_id  , map);
    }

    /**
     * 认证管理-活体检测
     * http://ai.baidu.com/docs#/Face-Liveness-V3/top
     *
     * @param map :
     * @return
     * @author cm
     * @date 2019.05.14 10:55:20
     */
    public static String getAuthFaceVerify(Map map) {
        if (null == map) {
            map = new HashMap<>();
        }
        List list = new ArrayList();
        byte[] imgData = FileUtils.toByteArray(map.get("filePath").toString());
        String encodeImg = Base64Util.encode(imgData);
        String access_token = map.get("access_token").toString();
        map.remove("access_token");
        map.remove("filePath");
        map.put("image", encodeImg);
        list.add(map);
        String params = JSONObject.toJSONString(list);
        String result = "";
        try {
            result = HttpBuUtil.post(uri_face_verify, access_token, "application/json", params);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * 用于测试
     * 获取身份证认证信息
     * 百度实现
     *
     * @return
     */
    private static String testAuthId() {
        Map map = new HashMap();
        JSONObject retJson = JSONObject.parseObject(AuthBuUtil.getBuToken());
        if (null == retJson) {
            retJson.get("error").toString();
        }

        map.put("id_card_side", "front");
        map.put("filePath", "F:\\msdp.16618.cn/id01.jpg");
        map.put("access_token", retJson.get("access_token").toString());

        //识别身份证
        retJson = JSONObject.parseObject(AuthBuUtil.getAuthId(map));
        String image_status = retJson.get("image_status").toString();
        if (!image_status.equals("normal")) {
            //return AuthBuUtil.getAuthIdErr(image_status);
            return null;
        }
        return retJson.toJSONString();
    }

    /**
     * 用于测试
     * 活体检测
     * 百度实现
     *
     * @return
     */
    private static String testAuthFaceVerify() {
        Map map = new HashMap();
        //获取TOKEN
        JSONObject retJson = JSONObject.parseObject(AuthBuUtil.getBuToken());
        if (null == retJson) {
            retJson.get("error").toString();
        }

        //map.put("id_card", "front");
        map.put("image_type", "BASE64");//BASE64	URL		FACE_TOKEN
        //map.put("face_field", "face_liveness,thresholds,quality,gender,age,face_type");
        map.put("filePath", "F:\\msdp.16618.cn\\id02.jpg");
        map.put("access_token", retJson.get("access_token").toString());
        return AuthBuUtil.getAuthFaceVerify(map);
    }


    public static void main1(String[] args) {
        //识别身份证
		/*
		String words_result = JSONObject.parseObject(testAuthId()).getString("words_result");
		String address = JSONObject.parseObject(words_result).getJSONObject("住址").getString("words");
		String id = JSONObject.parseObject(words_result).getJSONObject("公民身份号码").getString("words");
		String bir = JSONObject.parseObject(words_result).getJSONObject("出生").getString("words");
		String username = JSONObject.parseObject(words_result).getJSONObject("姓名").getString("words");
		String sex = JSONObject.parseObject(words_result).getJSONObject("性别").getString("words");
		String nat = JSONObject.parseObject(words_result).getJSONObject("民族").getString("words");
		*/
        JSONObject authFaceVerify = JSONObject.parseObject(testAuthFaceVerify());

        String error_code = authFaceVerify.get("error_code").toString();
        //活体验证
        if (authFaceVerify.containsKey("error_code") && "0".equals(error_code)) {
            String face_liveness = authFaceVerify.getJSONObject("result").get("face_liveness").toString();
            List face_list = authFaceVerify.getJSONObject("result").getJSONArray("face_list");
            String face_token1 = JSONObject.parseObject(face_list.get(0).toString()).getString("face_token");

            BigDecimal face_liveness_bg = new BigDecimal(face_liveness);
            BigDecimal liveness = new BigDecimal(0.393241);
            if (face_liveness_bg.compareTo(liveness) > -1) {
                System.out.println("人脸识别通过.");
            }
            //String face_token =  authFaceVerify.getJSONObject(face_list).getJSONObject("face_list").get("face_token").toString();
            //String face_token = JSONObject.parseObject(face_list).getJSONObject("face_list").getString("face_token");
            System.out.println(face_liveness);
        } else {
            System.out.println(authFaceVerify.get("error_msg").toString());
        }


    }
}
