package com.iyysoft.msdp.dp.sys.utils.alipay;

import lombok.Data;

import java.util.Map;

@Data
public class AliLoginInfo {
    private static String appId;
    private static String pid;
    private static String privateKey;
    private boolean rsa2 = true;

    public AliLoginInfo(String appId, String pid, String privateKey) {
        this.appId = appId;
        this.pid = pid;
        this.privateKey = privateKey;
    }


    public String getAliLoginInfo() {


        //apiname=com.alipay.account.auth&app_id="+this.appId+"&app_name=mc&auth_type=AUTHACCOUNT&biz_type=openservice&method=alipay.open.auth.sdk.code.get&pid="+this.pid+"&product_id=APP_FAST_LOGIN&scope=kuaijie&sign_type=RSA2&target_id=dfcshop&sign=

        Map<String, String> params = OrderInfoUtil2_0.buildAuthInfoMap(this.pid, this.appId, "iyysoft", rsa2);

        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        String orderString = orderParam + "&" + sign;

        return orderString;
    }


}