package com.iyysoft.msdp.common.core.util.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SMSUtils {


    //发送短信，uid，pwd，参数值请向企信通申请， tel：发送的手机号， content:发送的内容
    public static Integer sendDfcLinKai(String mobile, String content) throws IOException {
        String smsGetway = "http://mb345.com/WS/Send.aspx";
        HttpPost httpPost = new HttpPost(smsGetway);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("CorpID", "1111"));
        formparams.add(new BasicNameValuePair("Pwd", "667788"));
        formparams.add(new BasicNameValuePair("Cell", "01"));
        formparams.add(new BasicNameValuePair("SendTime", ""));
        formparams.add(new BasicNameValuePair("Mobile", mobile));
        formparams.add(new BasicNameValuePair("Content", content));
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "GBK");
        httpPost.setEntity(uefEntity);

        //httpPost.setHeader("Content-Type", "charset=GBK");
        //DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse res = httpclient.execute(httpPost);
        if (null != res && res.getEntity() != null) {
            String restext = EntityUtils.toString(res.getEntity(), "UTF-8");
            if (restext.equals("0")) {
                return 0;

            } else {
                return -2;
            }
        } else {
            return -1;
        }
    }


    //发送短信，uid，pwd，参数值请向企信通申请， tel：发送的手机号， content:发送的内容
    public static Integer sendDfcZTKJ(String mobile, String content) throws IOException {
        String url = "http://www.ztsms.cn/sendNSms.do";
        String username = "dfc";//内容
        String password = "Dfc667788";//密码
        String productid = "111111";//产品id
        String xh = "";//没有
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String tkey = format.format(date);
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String param = "url=" + url + "&username=" + username + "&password=" + MD5Util.getMD5String(MD5Util.getMD5String(password) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile + "&content=" + content + "&productid=" + productid + "&xh" + xh;
        String ret = HttpRequest.sendPost(url, param);//sendPost or sendGet  即get和post方式
        if (ret.indexOf("1,") == 0) {
            return 0;
        } else if (ret.equals("2")) {
            return -1;
        } else {
            return -2;
        }
    }

    //发送短信，uid，pwd，参数值请向企信通申请， tel：发送的手机号， content:发送的内容
    public static Integer sendMovek(String mobile, String content) throws IOException {
        String url = "http://client.movek.net:8888/sms.aspx?action=send";
        String userId = "1023";//内容
        String account = "SDK-A1023-1023";//密码
        String password = "152678";//产品id
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String param = "url=" + url + "&userid=" + userId + "&account=" + account + "&password=" + password + "&json=1&mobile=" + mobile + "&content=" + content;
        String ret = HttpRequest.sendPost(url, param);//sendPost or sendGet  即get和post方式
        JSONObject back = JSON.parseObject(ret);
        String code = back.getString("code");
        if (code.equals("Success")) {
            return 0;
        } else {
            return -1;
        }
    }
}
