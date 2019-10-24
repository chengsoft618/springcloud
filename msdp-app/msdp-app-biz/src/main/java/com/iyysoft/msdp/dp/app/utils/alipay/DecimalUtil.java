package com.iyysoft.msdp.dp.app.utils.alipay;

import java.math.BigDecimal;

public class DecimalUtil {
    /**
     *将BigDecimal转化为金额字符串
     * @param num BigDecimal类型变量
     * @return 金额格式字符串
     */
    public static String getAmountStr(BigDecimal num){
        if(num==null)return "0.00";
        else {
            num=num.setScale(2,BigDecimal.ROUND_HALF_UP);
            return num.toString();
        }
    }

    public static boolean isEmpty(BigDecimal num) {
        if(num == null)return true;
        else return false;
    }

    public static boolean isNotEmpty(BigDecimal num) {
        return !isEmpty(num);
    }

    public static boolean isZero(BigDecimal num) {
        if(num == null)return true;
        else {
            if(num.compareTo(BigDecimal.ZERO)==0)return true;
            return false;
        }
    }

    public static boolean isNotZero(BigDecimal num) {
        return !isZero(num);
    }
}
